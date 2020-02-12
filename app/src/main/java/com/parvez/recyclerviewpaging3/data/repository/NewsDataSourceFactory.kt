package com.parvez.recyclerviewpaging3.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.parvez.recyclerviewpaging3.data.api.response.NewsResponse

class NewsDataSourceFactory: DataSource.Factory<Int, NewsResponse.Articles>() {

    var newsDataSourceLiveData = MutableLiveData<NewsDataSource>()
    override fun create(): DataSource<Int, NewsResponse.Articles> {

        var newsDataSource = NewsDataSource()
        newsDataSourceLiveData.postValue(newsDataSource)
        return newsDataSource

    }
}