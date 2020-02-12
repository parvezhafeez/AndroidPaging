package com.parvez.recyclerviewpaging3.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.parvez.recyclerviewpaging3.data.api.response.NewsResponse
import com.parvez.recyclerviewpaging3.data.repository.NewsDataSource
import com.parvez.recyclerviewpaging3.data.repository.NewsDataSourceFactory
import com.parvez.recyclerviewpaging3.utils.NetworkState

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    var initialLoading: LiveData<NetworkState>
    var networkState: LiveData<NetworkState>

    var newsDataSourceFactory = NewsDataSourceFactory()
    var pagedListLiveData : LiveData<PagedList<NewsResponse.Articles>>

    init {
        var config = PagedList.Config.Builder()
            .setPageSize(NewsDataSource.PAGE_SIZE)
            .build()
        pagedListLiveData = LivePagedListBuilder(newsDataSourceFactory, config).build()

        initialLoading = Transformations.switchMap(newsDataSourceFactory.newsDataSourceLiveData){
            it.initialLoading
        }

        networkState = Transformations.switchMap(newsDataSourceFactory.newsDataSourceLiveData){
            it.networkState
        }
    }

}
