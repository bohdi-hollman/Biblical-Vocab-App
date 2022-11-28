package com.example.biblicalvocabulary

import android.app.Application
import com.example.biblicalvocabulary.data.BiblicalVocabularyDatabase

/**
 * An application class that inherits from [Application], allows for the creation of a singleton
 * instance of the [ForageDatabase]
 */
class BaseApplication : Application() {
    val database: BiblicalVocabularyDatabase by lazy {
        BiblicalVocabularyDatabase.getDatabase(this)
    }
}