package com.example.biblicalvocabulary.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.biblicalvocabulary.databinding.ListItemVocabBinding
import com.example.biblicalvocabulary.model.Vocab

/**
 * ListAdapter for the list of [Vocab]s retrieved from the database
 */
class VocabListAdapter(
    private val clickListener: (Vocab) -> Unit
) : ListAdapter<Vocab, VocabListAdapter.VocabViewHolder>(DiffCallback) {

    class VocabViewHolder(
        private var binding: ListItemVocabBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(vocab: Vocab) {
            binding.vocab = vocab
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Vocab>() {
        override fun areItemsTheSame(oldItem: Vocab, newItem: Vocab): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Vocab, newItem: Vocab): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VocabViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return VocabViewHolder(
            ListItemVocabBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VocabViewHolder, position: Int) {
        val vocab = getItem(position)
        holder.itemView.setOnClickListener{
            clickListener(vocab)
        }
        holder.bind(vocab)
    }
}