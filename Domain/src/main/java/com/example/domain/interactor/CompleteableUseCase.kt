package com.example.domain.interactor

import com.example.domain.executor.PostExecutionThread
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers

abstract class CompletableUseCase<in Params> constructor(
    val postExecutionThread : PostExecutionThread
){
    private val disposables = CompositeDisposable()

    protected abstract fun buildUseCaseCompletable(params: Params ?= null): Completable

    open fun execute(observer: DisposableCompletableObserver, params: Params?){
        val completable = buildUseCaseCompletable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.scheduler)
        addDisposible(completable.subscribeWith(observer))
    }

    fun addDisposible(disposable: Disposable){
        disposables.add(disposable)
    }

    fun dispose(){
        disposables.dispose()
    }

}