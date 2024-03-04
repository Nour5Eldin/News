package com.noureldin.news.ui.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.noureldin.news.api.articlesModel.Article
import com.noureldin.news.databinding.ItemArticleBinding

class ArticlesAdapter(var articles: List<Article>? = null):
RecyclerView.Adapter<ArticlesAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: ItemArticleBinding):
    RecyclerView.ViewHolder(binding.root){
        fun bind(article: Article){
            binding.article = article
            binding.invalidateAll()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = articles?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles?.get(position)!!
        holder.bind(article)

        if (onArticleClick != null){
            holder.itemView.setOnClickListener {
                onArticleClick?.invoke(article)
            }
        }
    }

    fun updateArticles(articles: List<Article>) {
        this.articles = articles
        notifyDataSetChanged()
    }

    var onArticleClick: ((article: Article) -> Unit)? = null
}