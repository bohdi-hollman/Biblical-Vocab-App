package com.example.biblicalvocabulary.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vocab.model.Vocab
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for database interaction.
 */
@Dao
interface VocabDao {
    @Query("SELECT * FROM vocab")
    fun getVocab(): Flow<List<Vocab>>

    @Query("SELECT * FROM vocab WHERE id = :id")
    fun getVocab(id: Long): Flow<Vocab>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vocab: Vocab)

    @Update
    fun update(vocab: Vocab)

    @Delete
    fun delete(vocab: Vocab)
}