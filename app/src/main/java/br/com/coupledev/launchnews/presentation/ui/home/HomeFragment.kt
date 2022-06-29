package br.com.coupledev.launchnews.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        initBinding()
        initSnackbar()
        initRecyclerView()
        initOptionMenu()
        return binding.root
    }

    private fun initBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
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