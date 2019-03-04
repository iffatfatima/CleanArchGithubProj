package com.example.mobileui.test

import android.app.Activity
import android.app.Application
import android.support.test.InstrumentationRegistry
import com.example.mobileui.injection.DaggerTestApplicationComponent
import com.example.mobileui.injection.TestApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class TestApplication: Application(), HasActivityInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Activity>
    private lateinit var appComponent: TestApplicationComponent

    override  fun activityInjector(): AndroidInjector<Activity> {
        return androidInjector
    }

    companion object {
        fun appComponent():TestApplicationComponent{
            return  (InstrumentationRegistry.getTargetContext().applicationContext as TestApplication).appComponent
        }
    }
    override fun onCreate(){
        super.onCreate()
        appComponent = DaggerTestApplicationComponent.builder()
            .application(this)
            .build()
        appComponent.inject(this)
    }

}