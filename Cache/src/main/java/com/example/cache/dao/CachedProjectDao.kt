package com.example.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.cache.db.ProjectConstants
import com.example.cache.model.CachedProject
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
abstract class CachedProjectDao{

    @Query(ProjectConstants.QUERY_PROJECTS)
    abstract fun getProjects(): Single<List<CachedProject>>

    @Query(ProjectConstants.DELETE_PROJECTS)
    abstract fun deleteProjects()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertProjects(projects:List<CachedProject>)

    @Query(ProjectConstants.QUERY_BOOKMARKED_PROJECTS)
    abstract fun getBookmarkedProjects():Single<List<CachedProject>>

    @Query(ProjectConstants.QUERY_UPDATE_BOOKMARKED_PROJECTS)
    abstract fun setBookmarkStatus(isBookmarked:Boolean, projectId: String) : Int

}