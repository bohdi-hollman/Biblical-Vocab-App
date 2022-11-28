package com.example.biblicalvocabulary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.biblicalvocabulary.BaseApplication
import com.example.biblicalvocabulary.R
import com.example.biblicalvocabulary.databinding.FragmentVocabListBinding
import com.example.biblicalvocabulary.ui.adapter.VocabListAdapter
import com.example.biblicalvocabulary.ui.viewmodel.VocabViewModel
import com.example.biblicalvocabulary.ui.viewmodel.VocabViewModelFactory

/**
 * A fragment to view the list of [Vocab]s stored in the database.
 * Clicking on a [Vocab] list item launches the [VocabDetailFragment].
 * Clicking the [FloatingActionButton] launched the [AddVocabFragment]
 */
class VocabListFragment : Fragment() {
    private val viewModel: VocabViewModel by activityViewModels {
        VocabViewModelFactory(
            (activity?.application as BaseApplication).database.vocabDao()
        )
    }

    private var _binding: FragmentVocabListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVocabListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = VocabListAdapter { vocab ->
            val action = VocabListFragmentDirections
                .actionVocabListFragmentToVocabDetailFragment(vocab.id)
            findNavController().navigate(action)
        }

        viewModel.vocab.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.apply {
            recyclerView.adapter = adapter
            addVocabFab.setOnClickListener {
                findNavController().navigate(
                    R.id.action_vocabListFragment_to_addVocabFragment
                )
            }
        }
    }
}