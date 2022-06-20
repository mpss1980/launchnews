package br.com.coupledev.launchnews.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.coupledev.launchnews.R
import br.com.coupledev.launchnews.databinding.HomeFragmentBinding
import br.com.coupledev.launchnews.presentation.adapters.PostListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private val binding: HomeFragmentBinding by lazy {
        HomeFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        initBinding()
        intiRecyclerView()
        return binding.root
    }

    private fun initBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun intiRecyclerView() {
        val adapter = PostListAdapter()
        binding.rvHome.adapter = adapter

        viewModel.listPost.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

}