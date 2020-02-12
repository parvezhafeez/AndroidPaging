package com.parvez.recyclerviewpaging3.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.parvez.recyclerviewpaging3.R
import com.parvez.recyclerviewpaging3.adapter.NewsAdapter
import com.parvez.recyclerviewpaging3.utils.NetworkState
import com.parvez.recyclerviewpaging3.viewModel.NewsViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var newsAdapter: NewsAdapter
    lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newsAdapter = NewsAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = newsAdapter

        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        newsViewModel.pagedListLiveData.observe(this, Observer {
            newsAdapter.submitList(it)
        })

        swipeRefreshLayout.setOnRefreshListener {
            //used for refresh page
            newsViewModel.newsDataSourceFactory.newsDataSourceLiveData.value?.invalidate()
            swipeRefreshLayout.isRefreshing = false

        }

        newsViewModel.initialLoading.observe(this, Observer {
            when(it){
                NetworkState.LOADING -> progressBar.visibility = View.VISIBLE
                NetworkState.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        })

        newsViewModel.networkState.observe(this, Observer {
            newsAdapter.setNetworkState(it)
        })

    }
}
