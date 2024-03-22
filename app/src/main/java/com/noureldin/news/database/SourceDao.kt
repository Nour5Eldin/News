package com.noureldin.news.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.noureldin.news.api.sourcesModel.Source

@Dao
interface SourceDao {

    @Insert
    fun addSources(sources: List<Source>)

    @Query("DELETE FROM Source WHERE category = :category")
    fun deleteSources(category: String)
    @Query("SELECT * FROM Source WHERE category = :category")
    fun getSources(category: String): List<Source>
}