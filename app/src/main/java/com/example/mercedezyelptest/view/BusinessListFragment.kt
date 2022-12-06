package com.example.mercedezyelptest.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mercedezyelptest.databinding.BusinessFragmentLayoutBinding
import com.example.mercedezyelptest.model.UIState
import com.example.mercedezyelptest.model.remote.Business
import com.example.mercedezyelptest.view.adapter.BusinessAdapter
import com.example.mercedezyelptest.viewmodel.BusinessViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BusinessListFragment: Fragment() {

    companion object{
        private const val TAG = "BusinessListFragment"
        const val EXTRA_LAT = "EXTRA_LAT"
        const val EXTRA_LON = "EXTRA_LON"
        fun newInstance(lat: Double, lon: Double) =
            BusinessListFragment().apply {
                arguments = Bundle().apply {
                    putDouble(EXTRA_LAT, lat)
                    putDouble(EXTRA_LON, lon)
                }
            }
    }

    private val adapter: BusinessAdapter by lazy { BusinessAdapter(emptyList(), navigateToDetails) }
    private val viewModel: BusinessViewModel by viewModels()
    private val navigateToDetails: (Int)-> Unit = {position->
        requireActivity().navigateToDetails(position)
    }
    private lateinit var binding: BusinessFragmentLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = BusinessFragmentLayoutBinding.inflate(inflater, container, false)
        initViews()
        initObservables()
        arguments?.apply {
            val lat = getDouble(EXTRA_LAT)
            val lon = getDouble(EXTRA_LON)
            viewModel.getDataByLocation(lat, lon)
        }
        return binding.root
    }

    private fun initObservables() {
        viewModel.hotBusiness.observe(viewLifecycleOwner){
            when(it){
                is UIState.Success -> {updateAdapter(it.data)}
                is UIState.Failure -> {showError(it.errorMessage)}
                is UIState.Loading -> {showLoading(it.isLoading)}
            }
        }
    }

    private fun showLoading(loading: Boolean) {
        requireActivity().showLoading(loading)
    }

    private fun showError(errorMessage: String) {
        requireActivity().displayErrorMessage(errorMessage)
    }

    private fun updateAdapter(data: List<Business>) {
        Log.d(TAG, "updateAdapter: $data")
        adapter.updateDataSet(data)
    }

    private fun initViews() {
        binding.apply {
            businessList.layoutManager = LinearLayoutManager(context)
            businessList.adapter = adapter
        }
    }
}