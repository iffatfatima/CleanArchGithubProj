package com.example.cache.dao

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import com.example.cache.db.ProjectsDatabase
import org.junit.After
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@Config(manifest= Config.NONE)
@RunWith(RobolectricTestRunner::class)
class ConfigDaoTest{

    @Rule
    @JvmField var instanttaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
        RuntimeEnvironment.application.applicationContext,
        ProjectsDatabase::class.java)
        .allowMainThreadQueries()
        .build()

    @After
    fun closeDb(){
        database.close()
    }

    @Test
    fun saveConfigurationsSavesData(){
        val config = ConfigDataFactory.createConfig()
        database.configDao().insertConfig(config)

        val testObserver = database.configDao().getConfig().test()
        testObserver.assertValue(config)

    }


}