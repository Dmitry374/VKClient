package com.dima.vkclient.network

import com.dima.vkclient.common.Constants
import com.dima.vkclient.data.net.comment.CommentData
import com.dima.vkclient.data.net.comment.CreateCommentResponse
import com.dima.vkclient.data.net.post.CreatePostData
import com.dima.vkclient.data.net.post.HidePostResponse
import com.dima.vkclient.data.net.post.LikeResponse
import com.dima.vkclient.data.net.post.PostData
import com.dima.vkclient.data.net.profile.CitiesData
import com.dima.vkclient.data.net.profile.CountryData
import com.dima.vkclient.data.net.profile.ProfileData
import com.dima.vkclient.data.net.wall.WallData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(Constants.NEWS_METHOD)
    fun getNews(
        @Query("filters") filters: String
    ): Single<PostData>

    @GET(Constants.LIKES_ADD)
    fun addLike(
        @Query("type") type: String,
        @Query("owner_id") ownerId: Int,
        @Query("item_id") itemId: Int
    ): Single<LikeResponse>

    @GET(Constants.LIKES_DELETE)
    fun deleteLike(
        @Query("type") type: String,
        @Query("owner_id") ownerId: Int,
        @Query("item_id") itemId: Int
    ): Single<LikeResponse>

    @GET(Constants.IGNORE_ITEM_IN_NEWS)
    fun hidePost(
        @Query("type") type: String,
        @Query("owner_id") ownerId: Int,
        @Query("item_id") itemId: Int
    ): Single<HidePostResponse>

    @GET(Constants.WALL_GET_COMMENTS)
    fun getPostComments(
        @Query("owner_id") ownerId: Int,
        @Query("post_id") postId: Int,
        @Query("count") count: Int,
        @Query("need_likes") needLikes: Int,
        @Query("offset") offset: Int,
        @Query("extended") extended: Int,
        @Query("thread_items_count") threadItemsCount: Int
    ): Single<CommentData>

    @GET(Constants.WALL_CREATE_COMMENT)
    fun createComment(
        @Query("owner_id") ownerId: Int,
        @Query("post_id") postId: Int,
        @Query("message") message: String
    ): Single<CreateCommentResponse>

    @GET(Constants.WALL_GET_COMMENT)
    fun getComment(
        @Query("owner_id") ownerId: Int,
        @Query("comment_id") commentId: Int,
        @Query("extended") extended: Int
    ): Single<CommentData>

    @GET(Constants.USERS_GET)
    fun getUserProfile(
        @Query("fields") fields: String
    ): Single<ProfileData>

    @GET(Constants.GET_COUNTRIES_BY_ID)
    fun getCountriesById(
        @Query("country_ids") countryIds: String
    ): Single<CountryData>

    @GET(Constants.GET_CITIES_BY_ID)
    fun getCitiesById(
        @Query("city_ids") cityIds: String
    ): Single<CitiesData>

    @GET(Constants.WALL_GET)
    fun getWallNews(
        @Query("extended") extended: Int = 1,
        @Query("count") count: Int = 100
    ): Single<WallData>

    @GET(Constants.WALL_POST)
    fun createPost(
        @Query("owner_id") ownerId: Int,
        @Query("message") message: String
    ): Single<CreatePostData>

    @GET(Constants.WALL_GET_BY_ID)
    fun getPostById(
        @Query("posts") posts: String,
        @Query("extended") extended: Int = 1
    ): Single<WallData>
}