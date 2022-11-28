package com.example.biblicalvocabulary.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Vocab entity to be stored in the vocab_database.
 */
@Entity(tableName = "vocab")
data class Vocab(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val address: String,
    @ColumnInfo(name = "memorized")
    val isMemorized: Boolean,
    val notes: String?
)