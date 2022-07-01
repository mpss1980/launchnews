package br.com.coupledev.launchnews.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import br.com.coupledev.launchnews.R
import br.com.coupledev.launchnews.core.State
import br.com.coupledev.launchnews.data.enums.SpaceFlightNewsCategory
import br.com.coupledev.launchnews.databinding.HomeFragmentBinding
import br.com.coupledev.launchnews.presentation.adapters.PostListAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private val binding: HomeFragmentBinding by lazy {
        HomeFragmentBinding.inflate(layoutInflater)
    }

    private lateinit var searchView: SearchView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        initBinding()
        initSnackbar()
        initRecyclerView()
        initOptionMenu()
        initSearchBar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initQueryHintObserver()
    }

    private fun initBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initQueryHintObserver() {
        viewModel.category.observe(viewLifecycleOwner) {
            searchView.queryHint = getString(R.string.search_in) + when(it) {
                SpaceFlightNewsCategory.ARTICLES -> getString(R.string.news)
                SpaceFlightNewsCategory.BLOGS -> getString(R.string.blogs)
                SpaceFlightNewsCategory.REPORTS -> getString(R.string.reports)
                else -> ""
            }
        }
    }

    private fun initSearchBar() {
        with(binding.homeToolbar) {
            val searchItem = menu.findItem(R.id.actionSearch)
            searchView = searchItem.actionView as SearchView

            searchView.isIconified = false

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    val searchString = searchView.query.toString()
                    viewModel.searchPostsTitleContains(searchString)
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { value ->
                        viewModel.searchPostsTitleContains(value)
                    }
                    return true
                }

            })
        }
    }

    private fun initOptionMenu() {
        with(binding.homeToolbar) {
            this.inflateMenu(R.menu.menu)
            menu.findItem(R.id.actionGetArticles).setOnMenuItemClickListener {
                viewModel.fetchLatest(SpaceFlightNewsCategory.ARTICLES)
                true
            }
            menu.findItem(R.id.actionGetBlogs).setOnMenuItemClickListener {
                viewModel.fetchLatest(SpaceFlightNewsCategory.BLOGS)
                true
            }
            menu.findItem(R.id.actionGetReports).setOnMenuItemClickListener {
                viewModel.fetchLatest(SpaceFlightNewsCategory.REPORTS)
                true
            }
        }
    }

    private fun initSnackbar() {
        viewModel.snackbar.observe(viewLifecycleOwner) {
            it?.let { errorMessage ->
                Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
                viewModel.onSnackBarShown()
            }
        }
    }

    private fun initRecyclerView() {
        val adapter = PostListAdapter()
        binding.rvHome.adapter = adapter

        viewModel.listPost.observe(viewLifecycleOwner) { state ->
            when (state) {
                State.Loading -> {
                    viewModel.showProgressBar()
                }
                is State.Error -> {
                    viewModel.hideProgressBar()
                }
                is State.Success -> {
                    viewModel.hideProgressBar()
                    adapter.submitList(state.result)
                }
            }
        }
    }
}