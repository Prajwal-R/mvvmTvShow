package com.example.hilttvshowlister.utility

import io.reactivex.rxjava3.disposables.CompositeDisposable

class CompositeDispose {
    val compositeDisposable = CompositeDisposable()
    fun dispose(){
        compositeDisposable.dispose()
    }
}