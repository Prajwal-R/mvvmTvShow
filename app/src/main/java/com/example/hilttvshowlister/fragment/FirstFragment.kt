package com.example.hilttvshowlister.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hilttvshowlister.databinding.FirstFragmentBinding
import com.example.hilttvshowlister.utility.CallBackInterface
import com.example.hilttvshowlister.utility.CompositeDispose
import com.example.hilttvshowlister.viewmodel.FirstFragmentState
import com.example.hilttvshowlister.viewmodel.FirstFragmentViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class FirstFragment : Fragment() {

    private lateinit var binding: FirstFragmentBinding
    var callBackInterface: CallBackInterface? = null
    private var recyclerAdapter: FirstFragmentAdapter? = null
    private lateinit var viewModel: FirstFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FirstFragmentBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[FirstFragmentViewModel::class.java]
        viewModel.init(requireContext())
        setupObserver()
        setUpRecyclerView()
        initViewModel()
    }


    private fun setUpRecyclerView() {
        with(binding) {
            layoutRecyclerView.layoutManager = LinearLayoutManager(context)
            recyclerAdapter = FirstFragmentAdapter().apply {
                clickListener = ::onItemClick
            }
            layoutRecyclerView.adapter = recyclerAdapter
        }
    }

    private fun onItemClick(tvShowId: Int) {
        if (callBackInterface != null) {
            callBackInterface?.onCallBack(tvShowId)
        }
    }

    private fun initViewModel() {
        viewModel.getAllTvShowDetail()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            viewModel.getFragmentState().collect {
                handleViewState(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        CompositeDispose().dispose()
    }

    private fun handleViewState(state: FirstFragmentState) {
        when (state) {
            is FirstFragmentState.Loading -> Log.d("TAG", "showProgress: loading")
            is FirstFragmentState.Error -> Log.d("TAG", "showError: error")
            is FirstFragmentState.LoadItems -> {
                binding.progressbar.setVisibility(GONE)
                recyclerAdapter?.setTvShowInformationList(state.itemList)
                Log.d("TAG", "Data loading in to user screen")
            }

        }
    }
}
