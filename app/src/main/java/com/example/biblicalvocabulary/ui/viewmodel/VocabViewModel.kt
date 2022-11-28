package com.example.biblicalvocabulary.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.biblicalvocabulary.data.VocabDao
import com.example.biblicalvocabulary.model.Vocab
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Shared [ViewModel] to provide data to the [ForageableListFragment], [ForageableDetailFragment],
 * and [AddForageableFragment] and allow for interaction the the [ForageableDao]
 */

class VocabViewModel(
    private val vocabDao: VocabDao
) : ViewModel() {
    val vocab = vocabDao.getVocab().asLiveData()

    fun getVocab(id: Long) = vocabDao.getVocab(id).asLiveData()

    fun addVocab(
        name: String,
        address: String,
        isMemorized: Boolean,
        notes: String
    ) {
        val vocab = Vocab(
            name = name,
            address = address,
            isMemorized = isMemorized,
            notes = notes
        )

        viewModelScope.launch(Dispatchers.IO) {
            vocabDao.insert(vocab)
        }
    }

    fun updateVocab(
        id: Long,
        name: String,
        address: String,
        isMemorized: Boolean,
        notes: String
    ) {
        val vocab = Vocab(
            id = id,
            name = name,
            address = address,
            isMemorized = isMemorized,
            notes = notes
        )
        viewModelScope.launch(Dispatchers.IO) {
            vocabDao.update(vocab)
        }
    }

    fun deleteVocab(vocab: Vocab) {
        viewModelScope.launch(Dispatchers.IO) {
            vocabDao.delete(vocab)
        }
    }

    fun isValidEntry(name: String, address: String): Boolean {
        return name.isNotBlank() && address.isNotBlank()
    }
}

class VocabViewModelFactory(private val vocabDao: VocabDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VocabViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VocabViewModel(vocabDao) as T
        }
        throw IllegalArgumentException("Unexpected class: $modelClass")
    }
}