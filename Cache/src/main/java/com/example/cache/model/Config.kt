package com.example.cache.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.example.cache.db.ConfigConstants
import com.example.cache.db.ProjectConstants

@Entity(tableName = ConfigConstants.TABLE_NAME)
class Config (
    @PrimaryKey(autoGenerate = true)
    val lastCacheTime: Long) {
}