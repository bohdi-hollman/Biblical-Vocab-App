import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.biblicalvocabulary.BaseApplication
import com.example.biblicalvocabulary.R
import com.example.biblicalvocabulary.databinding.FragmentAddVocabBinding
import com.example.biblicalvocabulary.model.Vocab
import com.example.biblicalvocabulary.ui.viewmodel.VocabViewModel
import com.example.biblicalvocabulary.ui.viewmodel.VocabViewModelFactory

/**
 * A fragment to enter data for a new [Vocab] or edit data for an existing [Vocab].
 * [Vocab]s can be saved or deleted from this fragment.
 */
class AddVocabFragment : Fragment() {
    private val navigationArgs: AddVocabFragmentArgs by navArgs()

    private var _binding: FragmentAddVocabBinding? = null

    private lateinit var vocab: Vocab

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: VocabViewModel by activityViewModels {
        VocabViewModelFactory(
            (activity?.application as BaseApplication).database.vocabDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddVocabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        if (id > 0) {
            viewModel.getVocab(id).observe(viewLifecycleOwner) {
                vocab = it
                bindVocab(it)
            }

            binding.deleteBtn.visibility = View.VISIBLE
            binding.deleteBtn.setOnClickListener {
                deleteVocab(vocab)
            }
        } else {
            binding.saveBtn.setOnClickListener {
                addVocab()
            }
        }
    }

    private fun deleteVocab(vocab: Vocab) {
        viewModel.deleteVocab(vocab)
        findNavController().navigate(
            R.id.action_addVocabFragment_to_vocabListFragment
        )
    }

    private fun addVocab() {
        if (isValidEntry()) {
            viewModel.addVocab(
                binding.nameInput.text.toString(),
                binding.locationAddressInput.text.toString(),
                binding.isMemorizedCheckbox.isChecked,
                binding.notesInput.text.toString()
            )
            findNavController().navigate(
                R.id.action_addVocabFragment_to_vocabListFragment
            )
        }
    }

    private fun updateVocab() {
        if (isValidEntry()) {
            viewModel.updateVocab(
                id = navigationArgs.id,
                name = binding.nameInput.text.toString(),
                address = binding.locationAddressInput.text.toString(),
                isMemorized = binding.isMemorizedCheckbox.isChecked,
                notes = binding.notesInput.text.toString()
            )
            findNavController().navigate(
                R.id.action_addVocabFragment_to_vocabListFragment
            )
        }
    }

    private fun bindVocab(vocab: Vocab) {
        binding.apply {
            nameInput.setText(vocab.name, TextView.BufferType.SPANNABLE)
            locationAddressInput.setText(vocab.address, TextView.BufferType.SPANNABLE)
            isMemorizedCheckbox.isChecked = vocab.isMemorized
            notesInput.setText(vocab.notes, TextView.BufferType.SPANNABLE)
            saveBtn.setOnClickListener {
                updateVocab()
            }
        }
    }

    private fun isValidEntry() = viewModel.isValidEntry(
        binding.nameInput.text.toString(),
        binding.locationAddressInput.text.toString()
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}