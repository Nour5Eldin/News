package com.noureldin.news.ui.settings


import android.content.Context
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
import androidx.appcompat.widget.SwitchCompat
import com.noureldin.news.R
import com.noureldin.news.databinding.FragmentSettingsBinding
import com.noureldin.news.util.LocaleManager
import com.noureldin.news.util.getCurrentLanguage
import com.noureldin.news.util.recreateActivity

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var switchCompat: SwitchCompat
    private var nightMode: Boolean = false
    private var editor: SharedPreferences.Editor? = null
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
        switchNightMode()
        sharedPreferences = activity?.getSharedPreferences("MODE", Context.MODE_PRIVATE)
        // setCountrySettings()
//        val countries = resources.getStringArray(R.array.countries)
//        val adapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, countries)
//        binding.autoCompleteTVCountries.setAdapter(adapter)
//
//        binding.autoCompleteTVCountries.setOnItemClickListener { _, _, position, _ ->
//            val selectedCountryCode = countries[position].split(":")[1].trim()
//            changeCountry(selectedCountryCode)
//        }

        setCountrySettings()

    }



    private fun switchNightMode() {
        switchCompat = binding.switchMode
        sharedPreferences = activity?.getSharedPreferences("MODE", Context.MODE_PRIVATE)
        nightMode = sharedPreferences?.getBoolean("night", false)!!

        if (nightMode) {
            switchCompat.isChecked = true
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        switchCompat.setOnCheckedChangeListener { compoundButton, state ->
            if (nightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor = sharedPreferences?.edit()
                editor?.putBoolean("night", false)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor = sharedPreferences?.edit()
                editor?.putBoolean("night", true)
            }
            editor?.apply()
        }


    }


    override fun onResume() {
        super.onResume()
        setLanguageSettings()
        setCustomToolbarTitle(getString(R.string.menu_settings))

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
        editor.putString("countryCode", code)
        editor.apply()
    }


    private fun setCountrySettings() {
        val countries = resources.getStringArray(R.array.countries)
        val adapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, countries)
        binding.autoCompleteTVCountries.setAdapter(adapter)

        val defaultCountryPosition = countries.indexOf("United States: us")
        binding.autoCompleteTVCountries.setText(countries[defaultCountryPosition], false)

        // Initialize SharedPreferences with the default country
        val sharedPreferences = activity?.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val defaultCountryCode = countries[defaultCountryPosition].split(":")[1].trim()
        sharedPreferences?.edit()?.putString("countryCode", defaultCountryCode)?.apply()

        binding.autoCompleteTVCountries.setOnItemClickListener { _, _, position, _ ->
            val selectedCountryCode = countries[position].split(":")[1].trim()
            changeCountry(selectedCountryCode)
        }

    }


}