package com.noureldin.news.ui.settings


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.noureldin.news.R
import com.noureldin.news.databinding.FragmentSettingsBinding
import com.noureldin.news.util.LocaleManager
import com.noureldin.news.util.getCurrentLanguage


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private var sharedPreferences: SharedPreferences? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentLanguageCode = getCurrentLanguage(requireContext())
        val language = if (currentLanguageCode == "en") "English" else "Arabic"
        setLanguageDropDownMenuState(language)
        onLanguageDropDownMenuClick()
        setCountrySettings()
        setModeDropDownMenuState()
        onModeDropDownMenuClick()
        activity?.setTitle(getString(R.string.menu_settings))
    }


    override fun onResume() {
        super.onResume()
        setLanguageSettings()
        setCustomToolbarTitle(getString(R.string.menu_settings))
        setModeSettings()


    }

    private fun setCustomToolbarTitle(title: String) {
        val activity = requireActivity()

        if (activity is AppCompatActivity) {
            val toolbarTitle = activity.findViewById<TextView>(R.id.toolbarTitle)
            toolbarTitle?.text = title

        }
    }

    private fun applyLanguageChange(languageCode: String) {
        LocaleManager.setLocale(requireContext(), languageCode)
        recreateActivity()
    }

    private fun setLanguageDropDownMenuState(selectedLanguage: String) {
        binding.autoCompleteTVLanguages.setText(selectedLanguage)
    }

    private fun setLanguageSettings() {
        val languages = resources.getStringArray(R.array.languages)
        val arrayAdapterLanguages =
            ArrayAdapter(requireContext(), R.layout.drop_down_item, languages)
        binding.autoCompleteTVLanguages.setAdapter(arrayAdapterLanguages)

    }

    private fun onLanguageDropDownMenuClick() {
        binding.autoCompleteTVLanguages.setOnItemClickListener { parent, view, position, id ->
            val selectedLanguage = parent.getItemAtPosition(position).toString()
            val languageCode = if (selectedLanguage == "English") "en" else "ar"
            setLanguageDropDownMenuState(selectedLanguage)
            applyLanguageChange(languageCode)

        }
    }

    private fun changeCountry(code: String) {
        val editor = requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE).edit()
        editor.putString("country_code", code)
        editor.apply()
    }

    private fun setCountrySettings() {
        val countries = resources.getStringArray(R.array.countries)
        val adapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, countries)
        binding.autoCompleteTVCountries.setAdapter(adapter)

        val code = sharedPreferences?.getString("country_code", "us") ?: "us"
        val currentCountry = countries.find {
            it.contains(code, true)
        }
        binding.autoCompleteTVCountries.setText(currentCountry, false)

        binding.autoCompleteTVCountries.setOnItemClickListener { _, _, position, _ ->
            val selectedCountryCode = countries[position].split(":")[1].trim()
            changeCountry(selectedCountryCode)
        }

    }

    private fun setModeSettings() {
        val modes = resources.getStringArray(R.array.modes)
        val arrayAdapterModes = ArrayAdapter(requireContext(), R.layout.drop_down_item, modes)
        binding.autoCompleteTVSwitchDark.setAdapter(arrayAdapterModes)
        arrayAdapterModes.notifyDataSetChanged()

    }

    private fun onModeDropDownMenuClick() {
        binding.autoCompleteTVSwitchDark.setOnItemClickListener { parent, view, position, id ->
            val selectedMode = parent.getItemAtPosition(position).toString()
            setModeDropDownMenuState()
            val isDark = (selectedMode == "Dark")
            changeMode(isDark)

        }
    }

    private fun setModeDropDownMenuState() {
        val currentNightMode =
            resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        if (currentNightMode == android.content.res.Configuration.UI_MODE_NIGHT_NO) {
            binding.autoCompleteTVSwitchDark.setText(getString(R.string.light))
            changeModeDropDownMenuIcon("Light")
        } else {
            binding.autoCompleteTVSwitchDark.setText(R.string.dark)
            changeModeDropDownMenuIcon("Dark")
        }

    }

    private fun changeModeDropDownMenuIcon(selectedMode: String) {
        if (selectedMode == "Light") {
            binding.SwitchDarkTil.setStartIconDrawable( R.drawable.ic_light_mode)
        } else {
            binding.SwitchDarkTil.setStartIconDrawable(R.drawable.ic_dark)
        }
        binding.SwitchDarkTil.refreshStartIconDrawableState()
    }

    private fun changeMode(isDark: Boolean) {

        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }


    fun Fragment.recreateActivity() {
        activity?.let {
            val intent = Intent(it, it::class.java)
            it.startActivity(intent)
            it.finish()
        }
    }


}