package com.parvez.recyclerviewpaging3.data.repository

import android.net.Network
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.parvez.recyclerviewpaging3.data.api.ApiClient
import com.parvez.recyclerviewpaging3.data.api.response.NewsResponse
import com.parvez.recyclerviewpaging3.utils.NetworkState
import com.parvez.recyclerviewpaging3.utils.Status
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsDataSource: PageKeyedDataSource<Int, NewsResponse.Articles>() {

    var initialLoading = MutableLiveData<NetworkState>()
    var networkState = MutableLiveData<NetworkState>()

    companion object{
        var FIRST_PAGE = 1
        var PAGE_SIZE = 10
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, NewsResponse.Articles>) {
        initialLoading.postValue(NetworkState.LOADING)
        networkState.postValue(NetworkState.LOADING)
        ApiClient.getApiClient().callNewsApi(FIRST_PAGE, PAGE_SIZE).enqueue(object : Callback<NewsResponse>{
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                initialLoading.postValue(NetworkState(Status.ERROR, t.toString()))
                networkState.postValue(NetworkState(Status.ERROR, t.toString()))
            }

            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                response.body()?.articles?.let { callback.onResult(it, null, FIRST_PAGE+1) }
                initialLoading.postValue(NetworkState.SUCCESS)
                networkState.postValue(NetworkState.SUCCESS)
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, NewsResponse.Articles>) {
        networkState.postValue(NetworkState.LOADING)
        ApiClient.getApiClient().callNewsApi(params.key, PAGE_SIZE).enqueue(object : Callback<NewsResponse>{
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                networkState.postValue(NetworkState(Status.ERROR, t.toString()))
            }

            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                response.body()?.articles?.let { callback.onResult(it, params.key+1) }
                networkState.postValue(NetworkState.SUCCESS)
            }

        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, NewsResponse.Articles>) {

    }
}