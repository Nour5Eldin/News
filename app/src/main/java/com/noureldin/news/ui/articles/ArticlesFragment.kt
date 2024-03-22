package com.noureldin.news.ui.articles

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.noureldin.news.R
import com.noureldin.news.api.articlesModel.Article
import com.noureldin.news.api.sourcesModel.Source
import com.noureldin.news.databinding.FragmentArticlesBinding
import com.noureldin.news.ui.articleDetails.ArticleDetailsFragment
import com.noureldin.news.ui.main.MainActivity
import com.noureldin.news.util.ConnectivityChecker
import com.noureldin.news.util.Constants
import com.noureldin.news.util.OnTryAgainClickListener
import com.noureldin.news.util.TabPreferences
import com.noureldin.news.util.showAlertDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticlesFragment : Fragment() {
    private lateinit var binding: FragmentArticlesBinding
    private val adapter = ArticlesAdapter()
    private lateinit var viewModel: ArticlesViewModel
    private lateinit var tabPreferences: TabPreferences
    private var sharedPreferences: SharedPreferences? = null
    private var country: String = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ArticlesViewModel::class.java]

        sharedPreferences = activity?.getSharedPreferences("settings", Context.MODE_PRIVATE)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val connect = ConnectivityChecker(requireContext())
        if (connect.isNetworkAvailable(requireContext())) {
            Toast.makeText(requireContext(), "Internet Connection Successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Internet Connection Failed", Toast.LENGTH_SHORT).show()
        }
        binding = FragmentArticlesBinding.inflate(inflater, container, false)

        tabPreferences = TabPreferences(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        val category = arguments?.getString(Constants.CATEGORY).toString()
        country = sharedPreferences?.getString("country_code", "us") ?: "sssss"
        Log.d("tt", country)
        //viewModel.getSources(category,country )
        viewModel.getSources(category, country )
        initRecyclerView()
        initObservers()
        binding.articlesRv.setOnClickListener {
           // viewModel.getSources(category,country)
            viewModel.getSources(category,country)
        }
        handleClicks(category=category)
    }
    private fun handleClicks(category: String) {
        binding.errorView.retryButton.setOnClickListener {
            viewModel.getSources(category,country)
        }
    }
    private fun setSelectedTabFromSharedPrefrences() {
        val selectedTab = tabPreferences.getSelectedTab()
        binding.tabLayout.getTabAt(selectedTab)?.select()

    }

    private fun initObservers() {
        viewModel.errorLiveData.observe(viewLifecycleOwner) { viewError ->
            handleError(viewError.message) {
                viewError.onTryAgainClickListener
            }
        }
        viewModel.articlesList.observe(viewLifecycleOwner) { articles ->
            adapter.updateArticles(articles as List<Article>)
        }

        viewModel.sourcesList.observe(viewLifecycleOwner) { sources ->
            bindTabs(sources)
        }

    }

    private fun initRecyclerView() {

        binding.articlesRv.adapter = adapter
        adapter.onArticleClick = { article: Article ->
            val bundle = Bundle()
            bundle
                .putParcelable(Constants.ARTICLE, article)
            val fragment = ArticleDetailsFragment()
            fragment.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
                .addToBackStack("").commit()
        }
    }

    private fun handleError(message: String? = null, onClickListener: OnTryAgainClickListener) {
        showAlertDialog(message
            ?: "something went wrong",
            posActionName = "try again",
            posAction = { dialog, which ->
                dialog.dismiss()
                onClickListener.onTryAgainClick()
            },
            negActionName = "cancel",
            negAction = { dialog, which ->
                dialog.dismiss()
            })
    }

    private fun bindTabs(sources: List<Source?>?) {
        if (sources == null)
            return
        sources.forEach { source ->
            val tab = binding.tabLayout.newTab()
            tab.text = source?.name
            tab.tag = source?.id
            binding.tabLayout.addTab(tab)
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let {
                    tabPreferences.saveSelectedTab(it)
                }

                viewModel.getArticles(tab?.tag.toString())
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                viewModel.getArticles(tab?.tag.toString())
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                viewModel.getArticles(tab?.tag.toString())
            }
        })

        setSelectedTabFromSharedPrefrences()


    }


    override fun onResume() {
        super.onResume()
        setCustomToolbarTitle(arguments?.getString(Constants.CATEGORY).toString())
        enableBackArrowButton()
        setSelectedTabFromSharedPrefrences()
    }


    override fun onPause() {
        super.onPause()
        disableBackArrowButton()
    }


    private fun enableBackArrowButton() {
        val activity = requireActivity() as MainActivity
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)

        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun disableBackArrowButton() {
        val activity = requireActivity() as MainActivity
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        activity.supportActionBar?.setDisplayShowHomeEnabled(false)
    }


    private fun setCustomToolbarTitle(title: String) {
        val activity = requireActivity()

        if (activity is AppCompatActivity) {
            val toolbarTitle = activity.findViewById<TextView>(R.id.toolbarTitle)
            toolbarTitle?.text = title

        }
    }
}