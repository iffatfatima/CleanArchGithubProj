package com.example.domain.interactor

import com.example.domain.executor.PostExecutionThread
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

abstract class ObservableUseCase<T, in Params> constructor(
    val postExecutionThread : PostExecutionThread
){
    private val disposables = CompositeDisposable()

    protected abstract fun buildUseCaseObservable(params: Params ?= null): Observable<T>

    open fun execute(observer: DisposableObserver<T>, params: Params?){
        val observable = buildUseCaseObservable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.scheduler)
        addDisposible(observable.subscribeWith(observer))
    }

    fun addDisposible(disposable: Disposable){
        disposables.add(disposable)
    }

    fun dispose(){
        disposables.dispose()
    }

}