package com.example.hilttvshowlister.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.hilttvshowlister.R
import com.example.hilttvshowlister.databinding.ItemTvShowDetails
import com.example.hilttvshowlister.model.TvShowInformationData
import com.example.hilttvshowlister.utility.CompositeDispose
import com.example.hilttvshowlister.viewmodel.SecondFragmentState
import com.example.hilttvshowlister.viewmodel.SecondFragmentViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class SecondFragment : Fragment() {
    private lateinit var binding: ItemTvShowDetails
    private lateinit var viewModel: SecondFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ItemTvShowDetails.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[SecondFragmentViewModel::class.java]
        viewModel.init(requireContext())
        setupObserver()
        initView()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            viewModel.getFragmentState().collect {
                handleViewState(it)
            }
        }
    }

    private fun initView() {
        viewModel.getTvShow(requireArguments().getInt("tvShowId"))
    }

    private fun handleViewState(state: SecondFragmentState) {
        when (state) {
            is SecondFragmentState.Loading -> Log.d("TAG", "showProgress: loading")
            is SecondFragmentState.Error -> Log.d("TAG", "showError: error")
            is SecondFragmentState.LoadDetail -> {
                Log.d("TAG", "Data to load in to user screen ${state.tvShow}")
                setItemData(state.tvShow)
            }

        }
    }

    private fun setItemData(itemData: TvShowInformationData) {
        with(binding) {
            tvTvShowTitle.text = itemData.title
            tvTvShowDescription.text = itemData.crew

            Glide.with(binding.root)
                .load(itemData.image)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(ivTvShowPoster)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        CompositeDispose().dispose()
    }

}