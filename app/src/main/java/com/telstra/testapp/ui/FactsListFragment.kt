package com.telstra.testapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.telstra.testapp.R
import com.telstra.testapp.data.Resource
import com.telstra.testapp.data.Status
import com.telstra.testapp.databinding.FactsListFragmentBinding
import com.telstra.testapp.service.FactsResponse
import com.telstra.testapp.ui.adapter.FactsRecyclerViewAdapter
import com.telstra.testapp.viewmodel.FactsViewModel
import com.telstra.testapp.viewmodel.FactsViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Fragment which displays the facts data
 */
class FactsListFragment : DaggerFragment() {

    lateinit var binding: FactsListFragmentBinding
    lateinit var factsViewModel: FactsViewModel
    private val factsAdapter = FactsRecyclerViewAdapter(arrayListOf())

    @Inject
    lateinit var viewModelFactory: FactsViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FactsListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        factsViewModel = ViewModelProviders.of(this, viewModelFactory).get(FactsViewModel::class.java)

        binding.viewModel = factsViewModel
        binding.newsListRv.layoutManager = LinearLayoutManager(activity)
        binding.newsListRv.adapter = factsAdapter
        binding.executePendingBindings()

        initFactsData()
    }

    private fun initFactsData() {
        factsViewModel.factsData.observe(viewLifecycleOwner, Observer { it?.let { showFactsData(it) } })
        factsViewModel.loadFactsData()
    }


    private fun showFactsData(factsDataResource: Resource<FactsResponse>) {
        factsDataResource.data?.title?.let { activity?.title = it }
        factsDataResource.data?.items?.let { factsAdapter.replaceData(it) }
        if (factsDataResource.status == Status.ERROR) showErrorToast()
    }

    private fun showErrorToast() {
        Toast.makeText(activity, getString(R.string.error_loading_data), Toast.LENGTH_SHORT).show()
    }
}
