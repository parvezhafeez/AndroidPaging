package com.parvez.recyclerviewpaging3.data.api.response

/**
 * Api link:
 * https://newsapi.org/v2/everything?q=sports&apiKey=aa67d8d98c8e4ad1b4f16dbd5f3be348&page=1&pageSize=10
 */
data class NewsResponse(
    var status: String,
    var totalResults: Int,
    var articles: List<Articles>
) {
    data class Articles(
        var source: Source,
        var author: String,
        var title: String,
        var description: String,
        var url: String,
        var urlToImage: String,
        var publishedAt: String,
        var content: String
    ){
        data class Source(
            var id: String,
            var name: String
        )
    }
}