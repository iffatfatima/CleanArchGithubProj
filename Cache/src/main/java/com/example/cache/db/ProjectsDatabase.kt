package com.example.cache.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.util.Log
import com.example.cache.dao.CachedProjectDao
import com.example.cache.dao.ConfigDao
import com.example.cache.model.CachedProject
import com.example.cache.model.Config
import javax.inject.Inject
@Database(entities = arrayOf(CachedProject::class, Config::class), version = 1, exportSchema = false)
abstract class ProjectsDatabase @Inject constructor(): RoomDatabase(){

    abstract fun cachedProjectsDao(): CachedProjectDao

    abstract fun configDao():ConfigDao

    companion object {
        private var INSTANCE: ProjectsDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): ProjectsDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            ProjectsDatabase::class.java, "projects.db"
                        ).build()
                    }
                    else{
                        Log.e("ProjectsDatabase", "getInstance() - Instance already created.")
                    }
                    return INSTANCE as ProjectsDatabase
                }
            }
            else{
                Log.e("ProjectsDatabase", "getInstance() - Instance exists.")
            }
            return INSTANCE as ProjectsDatabase
        }
    }
}