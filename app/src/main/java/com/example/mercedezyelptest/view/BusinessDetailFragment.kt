package com.example.mercedezyelptest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mercedezyelptest.databinding.BusinessFragmentLayoutBinding
import com.example.mercedezyelptest.model.UIState
import com.example.mercedezyelptest.model.remote.Business
import com.example.mercedezyelptest.view.adapter.BusinessDetailAdapter
import com.example.mercedezyelptest.viewmodel.BusinessViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BusinessDetailFragment : Fragment() {

    companion object {
        const val POSITION_EXTRA = "POSITION_EXTRA"
        fun newInstance(position: Int) =
            BusinessDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(POSITION_EXTRA, position)
                }
            }
    }

    private val viewModel: BusinessViewModel by viewModels()
    private lateinit var binding: BusinessFragmentLayoutBinding
    private val adapter: BusinessDetailAdapter by lazy { BusinessDetailAdapter(emptyList()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = BusinessFragmentLayoutBinding.inflate(inflater, container, false)
        initViews()
        initObservable()
        return binding.root
    }

    private fun initObservable() {
        viewModel.hotBusiness.observe(viewLifecycleOwner) {
            when (it) {
                is UIState.Success -> {
                    updateAdapter(it.data)
                }
                is UIState.Failure -> {
                    showError(it.errorMessage)
                }
                is UIState.Loading -> {
                    showLoading(it.isLoading)
                }
            }
        }
    }

    private fun initViews() {
        binding.apply {
            businessList.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            businessList.adapter = adapter
        }
    }

    private fun showLoading(loading: Boolean) {
        requireActivity().showLoading(loading)
    }

    private fun showError(errorMessage: String) {
        requireActivity().displayErrorMessage(errorMessage)
    }

    private fun updateAdapter(data: List<Business>) {
        adapter.updateDataSet(data)
        binding.businessList.layoutManager?.scrollToPosition(
            arguments?.getInt(POSITION_EXTRA) ?: 0
        )
    }
}