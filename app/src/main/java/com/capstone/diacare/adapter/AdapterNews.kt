package com.capstone.diacare.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.diacare.data.model.ArticlesItem
import com.capstone.diacare.databinding.ItemNewsBinding
import com.capstone.diacare.utils.getYearFromISODate

class AdapterNews(
    private val data: List<ArticlesItem>,
    private val onOpenLinkClick: (url: String) -> Unit
) : RecyclerView.Adapter<AdapterNews.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: ArticlesItem) {
            binding.apply {
                val glide = Glide.with(binding.root.context)
                glide.clear(ivNewsImage)
                glide.load(item.urlToImage).into(ivNewsImage)
                tvPublishAt.text = "Published at ${getYearFromISODate(item.publishedAt ?: "")}"
                tvNewsTitle.text = item.title
                tvNewsPublisher.text = item.author
                tvNewsDescription.text = item.description
                fabOpenBrowser.setOnClickListener {
                    onOpenLinkClick.invoke(item.url ?: "")
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: AdapterNews.ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}