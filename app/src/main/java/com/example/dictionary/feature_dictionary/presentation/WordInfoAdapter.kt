package com.example.dictionary.feature_dictionary.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.databinding.ItemWordInfoBinding
import com.example.dictionary.feature_dictionary.domain.models.WordInfo

class WordInfoAdapter() : ListAdapter<WordInfo, WordInfoAdapter.WordInfoViewHolder>(WordInfoDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordInfoViewHolder {
        val binding = ItemWordInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordInfoViewHolder, position: Int) {
        val curItem = getItem(position)
        holder.bind(curItem)
    }

    inner class WordInfoViewHolder(private val binding: ItemWordInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(wordInfo: WordInfo) {
            binding.apply {
                tvWord.text = wordInfo.word
                wordInfo.phonetic?.let {tvPhonetic.text = it }
                wordInfo.origin?.let {tvOrigin.text = it }
                wordInfo.meanings?.forEach { meaning ->
                    tvPartOfSpeech.text = meaning.partOfSpeech
                    // todo далее надо пробегаться по всем и добавлять, а не только с индексом 0
                    tvDefinition.text = meaning.definitions[0].definition
                    meaning.definitions[0].example?.let { example ->
                        tvExample.text = "Example: $example"
                    }
                }
            }
        }
    }
}

class WordInfoDiff : DiffUtil.ItemCallback<WordInfo>() {
    override fun areItemsTheSame(oldItem: WordInfo, newItem: WordInfo): Boolean {
        return oldItem.word == newItem.word
    }

    override fun areContentsTheSame(oldItem: WordInfo, newItem: WordInfo): Boolean {
        return oldItem == newItem
    }
}