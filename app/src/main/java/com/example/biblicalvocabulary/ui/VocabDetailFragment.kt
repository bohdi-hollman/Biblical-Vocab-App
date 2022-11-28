package com.example.biblicalvocabulary.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.biblicalvocabulary.BaseApplication
import com.example.biblicalvocabulary.R
import com.example.biblicalvocabulary.databinding.FragmentVocabDetailBinding
import com.example.biblicalvocabulary.model.Vocab
import com.example.biblicalvocabulary.ui.viewmodel.VocabViewModel
import com.example.biblicalvocabulary.ui.viewmodel.VocabViewModelFactory

/**
 * A fragment to display the details of a [Vocab] currently stored in the database.
 * The [AddVocabFragment] can be launched from this fragment to edit the [Vocab]
 */
class VocabDetailFragment : Fragment() {
    private val navigationArgs: VocabDetailFragmentArgs by navArgs()

    private val viewModel: VocabViewModel by activityViewModels {
        VocabViewModelFactory(
            (activity?.application as BaseApplication).database.vocabDao()
        )
    }

    private lateinit var vocab: Vocab

    private var _binding: FragmentVocabDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVocabDetailBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        viewModel.getVocab(id).observe(viewLifecycleOwner) {
            vocab = it
            bindVocab()
        }
    }

    private fun bindVocab() {
        binding.apply {
            name.text = vocab.name
            location.text = vocab.address
            notes.text = vocab.notes
            if (vocab.isMemorized) {
                memory.text = getString(R.string.memorized)
            } else {
                memory.text = getString(R.string.not_memorized)
            }
            editVocabFab.setOnClickListener {
                val action = VocabDetailFragmentDirections
                    .actionVocabDetailFragmentToAddVocabFragment(vocab.id)
                findNavController().navigate(action)
            }

            location.setOnClickListener {
                launchMap()
            }
        }
    }

    private fun launchMap() {
        val address = vocab.address.let {
            it.replace(", ", ",")
            it.replace(". ", " ")
            it.replace(" ", "+")
        }
        val gmmIntentUri = Uri.parse("geo:0,0?q=$address")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }
}