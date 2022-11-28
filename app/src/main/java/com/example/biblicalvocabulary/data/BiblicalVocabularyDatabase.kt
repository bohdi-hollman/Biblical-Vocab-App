package com.example.biblicalvocabulary.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.biblicalvocabulary.model.Vocab

/**
 * Room database to persist data for the Forage app.
 * This database stores a [Forageable] entity
 */
@Database(entities = [Vocab::class], version = 1, exportSchema = false)
abstract class BiblicalVocabularyDatabase : RoomDatabase() {
    abstract fun vocabDao(): VocabDao

    companion object {
        @Volatile
        private var INSTANCE: BiblicalVocabularyDatabase? = null

        fun getDatabase(context: Context) = INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext,
                BiblicalVocabularyDatabase::class.java,
                "vocab_database"
            ).build().apply {
                INSTANCE = this
            }
        }
    }
}