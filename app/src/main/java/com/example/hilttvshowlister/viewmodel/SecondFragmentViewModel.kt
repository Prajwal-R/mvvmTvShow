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

class SecondFragmentViewModel : ViewModel() {

    private lateinit var repository: RetrofitRepo
    private val secondFragmentStateVal =
        MutableStateFlow<SecondFragmentState>(SecondFragmentState.Loading)

    fun getFragmentState(): StateFlow<SecondFragmentState> = secondFragmentStateVal

    fun getTvShow(itemId: Int) {
        Log.d("TAG", "getTvShow: function called with $itemId ")
        val d = repository.getTvShow(itemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                try {
                    secondFragmentStateVal.value = SecondFragmentState.LoadDetail(it)
                    Log.d("getTvShow", "set the state with value ${it.image}")
                } catch (e: Exception) {
                    secondFragmentStateVal.value = SecondFragmentState.Error(e.message)
                }
            }, {
                secondFragmentStateVal.value = SecondFragmentState.Error(it.message)
            })
        CompositeDispose().compositeDisposable.add(d)
    }

    fun init(context: Context) {
        repository = RetrofitRepo(context)
    }
}

sealed class SecondFragmentState {
    object Loading : SecondFragmentState()
    data class LoadDetail(val tvShow: TvShowInformationData) : SecondFragmentState()
    data class Error(val message: String? = null, val apiType: Int? = null) : SecondFragmentState()
}