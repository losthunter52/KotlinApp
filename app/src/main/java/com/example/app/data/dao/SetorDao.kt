package com.example.app.data.dao

import androidx.room.*
import com.example.app.data.models.Setor
import kotlinx.coroutines.flow.Flow

@Dao
interface SetorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(setor: Setor)

    @Update
    suspend fun update(setor: Setor)

    @Delete
    suspend fun delete(setor: Setor)

    @Query("SELECT * FROM Setor")
    fun getSetors(): Flow<List<Setor>>
}