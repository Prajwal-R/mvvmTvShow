package com.example.hilttvshowlister.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.hilttvshowlister.model.TvShowInformationData
import com.example.hilttvshowlister.repository.RetrofitRepo
import com.example.hilttvshowlister.utility.CompositeDispose
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FirstFragmentViewModel : ViewModel() {
    private lateinit var repository: RetrofitRepo
    private var firstFragmentStateVal =
        MutableStateFlow<FirstFragmentState>(FirstFragmentState.Loading)

    fun getFragmentState(): StateFlow<FirstFragmentState> = firstFragmentStateVal

    fun init(context: Context) {
        repository = RetrofitRepo(context)
    }

    fun getAllTvShowDetail() {
        val d = repository.getTvShowApiData()
            .map {
                val listTv = mutableListOf<TvShowInformationData>()
                it.forEach { item ->
                    listTv.add(
                        TvShowInformationData(
                            item.title,
                            item.image,
                            item.crew,
                            item.imDbRating
                        )
                    )
                }
                return@map listTv
            }
            .doOnNext {
                //insert to database
                repository.insertTvShow(it)
            }
            .flatMap {
                repository.getAllTvShow()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                try {
                    firstFragmentStateVal.value = FirstFragmentState.LoadItems(it)
                }
                catch(e: Exception){
                    firstFragmentStateVal.value = FirstFragmentState.Error(e.message)
                    //Log.d("TAG", "getAllTvShowDetail: ${e.message}")
                }
            }, {
                firstFragmentStateVal.value = FirstFragmentState.Error(it.message)
                //Log.d("TAG", "getAllTvShowDetail: error ${it.message} ")
            })
        CompositeDispose().compositeDisposable.add(d)
    }
}

sealed class FirstFragmentState {
    object Loading : FirstFragmentState()
    data class LoadItems(val itemList: List<TvShowInformationData>) : FirstFragmentState()
    data class Error(val message: String? = null, val apiType: Int? = null) : FirstFragmentState()
}