package com.wazing.wanandroid.model.api

import com.wazing.wanandroid.model.Article
import com.wazing.wanandroid.model.BannerEntity
import com.wazing.wanandroid.model.Pagination
import com.wazing.wanandroid.model.User
import com.wazing.wanandroid.model.entity.*
import retrofit2.http.*

interface ApiService {

    @GET("/article/top/json")
    suspend fun getTopArticles(): DataResponse<List<Article>>

    @GET("/article/list/{page}/json")
    suspend fun getArticles(@Path("page") page: Int): DataResponse<Pagination<Article>>

    @GET("/banner/json")
    suspend fun getBanners(): DataResponse<List<BannerEntity>>

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun signIn(
        @Field("username") username: String,
        @Field("password") password: String
    ): DataResponse<User>

    @FormUrlEncoded
    @POST("/user/register")
    suspend fun signUp(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") rePassword: String = password
    ): DataResponse<User>

    @GET("/lg/coin/userinfo/json")
    suspend fun getCoin(): DataResponse<Coin>

    @GET("/lg/coin/list/{page}/json")
    suspend fun getCoinList(@Path("page") page: Int): DataResponse<Pagination<CoinInfo>>

    @GET("/lg/collect/list/{page}/json")
    suspend fun getCollects(@Path("page") page: Int): DataResponse<Pagination<Collect>>

    @POST("/lg/collect/{id}/json")
    suspend fun collect(@Path("id") id: Int)

    @POST("/lg/uncollect_originId/{id}/json")
    suspend fun unCollect(@Path("id") id: Int)

    @GET("/tree/json")
    suspend fun getSystemTrees(): DataResponse<List<SystemParent>>

    @GET("/navi/json")
    suspend fun getNavigateTrees(): DataResponse<List<NavigateParent>>

    @GET("/article/list/{page}/json")
    suspend fun getSystemArticle(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): DataResponse<Pagination<Article>>

}