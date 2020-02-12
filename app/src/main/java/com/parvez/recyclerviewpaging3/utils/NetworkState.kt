package com.parvez.recyclerviewpaging3.utils

class NetworkState(var status: Status, var msg: String) {

    companion object{
        var LOADING = NetworkState(Status.LOADING, "loading...")
        var SUCCESS = NetworkState(Status.SUCCESS, "loading...")

    }

}