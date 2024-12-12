package com.capstone.diacare.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.diacare.data.model.PredictionEntity
import com.capstone.diacare.databinding.ItemHistoryBinding

class AdapterHistory(
    private val data: List<PredictionEntity>,
    private val onDeleteItemClick: (PredictionEntity) -> Unit
) : RecyclerView.Adapter<AdapterHistory.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PredictionEntity) {
            binding.apply {
                val glide = Glide.with(binding.root.context)
                glide.clear(ivDetectionImage)
                glide.load(item.imageUri.toUri()).into(ivDetectionImage)
                tvDetectionDate.text = item.date
                tvDiabetesInfo.text = "${item.result} ${item.confidence}%"
                fabDelete.setOnClickListener {
                    onDeleteItemClick.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHistory.ViewHolder {
        val binding = ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterHistory.ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}