package com.capstone.diacare.presentation.news

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.diacare.adapter.AdapterNews
import com.capstone.diacare.data.model.ArticlesItem
import com.capstone.diacare.databinding.ActivityNewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private val vm: NewsViewModel by viewModels()
    private lateinit var dialog: ProgressDialog
    private lateinit var adapter: AdapterNews
    private val news = arrayListOf<ArticlesItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog = ProgressDialog.show(this, "", "Loading")
        adapter = AdapterNews(news) { url ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK // Ensures it works even if called from a non-activity context
            startActivity(intent)
        }

        binding.newsRecyclerView.adapter = adapter
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.ivBack.setOnClickListener {
            finish()
        }

        vm.loadingState.observe(this) {
            if (it) {
                dialog.show()
            } else {
                dialog.dismiss()
            }

        }
        vm.newsResult.observe(this) {
            news.clear()
            news.addAll(it.data?.articles ?: emptyList())
            adapter.notifyDataSetChanged()
        }
    }
}