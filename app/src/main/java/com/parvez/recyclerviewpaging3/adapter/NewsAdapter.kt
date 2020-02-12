package com.parvez.recyclerviewpaging3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.parvez.recyclerviewpaging3.R
import com.parvez.recyclerviewpaging3.data.api.response.NewsResponse
import com.parvez.recyclerviewpaging3.utils.NetworkState
import kotlinx.android.synthetic.main.item.view.*

class NewsAdapter : PagedListAdapter<NewsResponse.Articles, NewsAdapter.MyViewHolder>(diffUtil) {

    lateinit var myNetworkState: NetworkState

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        if (viewType == R.layout.item_loading) {
            var view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            return MyViewHolder(view)
        } else {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
            return MyViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if(getItemViewType(position)==R.layout.item){
            var model: NewsResponse.Articles? = getItem(position)
            holder.itemView.item_date.text = model?.publishedAt
            holder.itemView.item_desc.text = model?.description
            holder.itemView.item_title.text = model?.title
            Glide.with(holder.itemView.context).load(model?.urlToImage).into(holder.itemView.item_image)
        }else{
            //do nothing
        }

    }


    override fun getItemViewType(position: Int): Int {
        return if (myNetworkState == NetworkState.LOADING && position == itemCount - 1)
            R.layout.item_loading
        else
            R.layout.item
    }

    fun setNetworkState(it: NetworkState) {
        myNetworkState = it
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {
        var diffUtil = object : DiffUtil.ItemCallback<NewsResponse.Articles>() {

            override fun areItemsTheSame(
                oldItem: NewsResponse.Articles,
                newItem: NewsResponse.Articles
            ): Boolean {
                return oldItem.publishedAt == newItem.publishedAt
            }

            override fun areContentsTheSame(
                oldItem: NewsResponse.Articles,
                newItem: NewsResponse.Articles
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}