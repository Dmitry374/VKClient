package com.dima.vkclient.domain

import com.dima.vkclient.common.Constants
import com.dima.vkclient.common.SharedPreferencesHelper
import com.dima.vkclient.data.NewsAndFavouriteLists
import com.dima.vkclient.data.PostsItemAndProfileUiModel
import com.dima.vkclient.data.domain.post.PostDomain
import com.dima.vkclient.data.domain.post.PostItem
import com.dima.vkclient.data.net.post.*
import com.dima.vkclient.data.net.profile.Career
import com.dima.vkclient.data.net.profile.CitiesData
import com.dima.vkclient.data.net.profile.CountryData
import com.dima.vkclient.data.net.profile.ProfileResponse
import com.dima.vkclient.data.net.wall.WallItemNet
import com.dima.vkclient.data.net.wall.WallResponseNet
import com.dima.vkclient.repository.NewsRepository
import com.dima.vkclient.ui.detail.adapter.PostAndCommentUiModel
import com.dima.vkclient.ui.profile.adapter.ProfileAndPostsUiModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NewsInteractor(
    private val newsRepository: NewsRepository,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) {

    /**
     * Web
     */

    fun loadNews(filters: String): Single<Unit> {
        return newsRepository.loadNews(filters)
            .subscribeOn(Schedulers.io())
            .map { postData ->
                val response = postData.response

                val newsPosts = getPosts(response) as ArrayList

                newsPosts.forEach { post ->
                    newsRepository.insertPost(post)

                    post.attachments?.forEach { attachment ->
                        newsRepository.insertAttachment(post, attachment)

                        attachment.photo?.sizes?.forEach { photoSize ->
                            newsRepository.insertPhotoSize(attachment, photoSize)
                        }
                    }
                }

                val groups = getGroups(response) as ArrayList

                val profiles = getProfiles(response) as ArrayList

                newsRepository.insertGroups(groups)
                newsRepository.insertProfile(profiles)
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getPosts(response: Response?): List<Item> {
        return response?.items ?: arrayListOf()
    }

    private fun getGroups(response: Response?): List<Group> {
        return response?.groups ?: arrayListOf()
    }

    private fun getProfiles(response: Response?): List<Profile> {
        return response?.profiles ?: arrayListOf()
    }

    private fun getPosts(response: WallResponseNet?): List<WallItemNet> {
        return response?.items ?: arrayListOf()
    }

    private fun getGroups(response: WallResponseNet?): List<Group> {
        return response?.groups ?: arrayListOf()
    }

    private fun getProfiles(response: WallResponseNet?): List<Profile> {
        return response?.profiles ?: arrayListOf()
    }

    fun addLike(postItem: PostItem): Single<LikeResponse> {
        return newsRepository.addLike(postItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteLike(postItem: PostItem): Single<LikeResponse> {
        return newsRepository.deleteLike(postItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun updatePostInDb(postItem: PostItem): Observable<Unit> {
        return Observable.fromCallable {
            newsRepository.updatePostLocal(postItem)
        }
            .subscribeOn(Schedulers.io())
    }

    fun hidePost(postItem: PostItem, type: String): Single<HidePostResponse> {
        return newsRepository.hidePost(postItem, type)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun loadPostComments(postItem: PostItem): Single<Unit> {
        return newsRepository.loadPostComments(
            ownerId = postItem.post.sourceId,
            postId = postItem.post.id,
            count = postItem.post.comments.count,
            needLikes = 1,
            offset = 0,
            extended = 1,
            threadItemsCount = 0
        )
            .subscribeOn(Schedulers.io())
            .map { commentData ->
                val response = commentData.response

                val comments = response.items
                val groups = response.groups
                val profiles = response.profiles

                newsRepository.deletePostComments(postItem.post.id)

                newsRepository.insertComments(comments)
                newsRepository.insertGroups(groups)
                newsRepository.insertProfile(profiles)

                postItem.post.comments.count = response.count
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun createComment(
        postItem: PostItem,
        message: String
    ): Single<PostAndCommentUiModel.CommentUiModel> {
        var commentId = 0
        return newsRepository.createComment(
            ownerId = postItem.post.sourceId,
            postId = postItem.post.id,
            message = message
        )
            .subscribeOn(Schedulers.io())
            .flatMap { createCommentResponse ->
                commentId = createCommentResponse.response.commentId
                loadAndSaveNewComment(
                    post = postItem.post,
                    commentId = commentId
                )
            }
            .flatMap {
                postItem.post.comments.count = postItem.post.comments.count + 1
                getCommentFromDb(commentId)
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun loadAndSaveNewComment(post: PostDomain, commentId: Int): Single<Unit> {
        return newsRepository.getComment(
            ownerId = post.sourceId,
            commentId = commentId,
            extended = 1
        )
            .map { commentData ->

                val response = commentData.response

                val comments = response.items
                val groups = response.groups
                val profiles = response.profiles

                newsRepository.insertComments(comments)
                newsRepository.insertGroups(groups)
                newsRepository.insertProfile(profiles)

                newsRepository.updatePostCommentCount(post.id, post.comments.count + 1)
            }
    }

    fun loadUserProfileData(fields: String): Single<ProfileAndPostsUiModel.ProfileUiModel> {
        lateinit var profileResponse: ProfileResponse

        var careerList: List<Career> = listOf()

        var cityIdsString = ""
        var countriesIdsString = ""

        val cityNames = mutableListOf<String>()
        val countryNames = mutableListOf<String>()

        return newsRepository.getUserData(fields)
            .subscribeOn(Schedulers.io())
            .map { profileData ->
                profileResponse = profileData.response[0]

                if (profileResponse.id != sharedPreferencesHelper.getUserId()) {
                    newsRepository.clearAllData()
                }

                // save user id
                sharedPreferencesHelper.setUserId(profileResponse.id)

                newsRepository.insertUserProfile(profileResponse)

                careerList = profileResponse.career

                val cityIds: MutableList<Int> = mutableListOf()
                val countriesIds: MutableList<Int> = mutableListOf()

                profileResponse.career.forEach { career ->
                    cityIds.add(career.cityId)
                    countriesIds.add(career.countryId)
                }

                cityIdsString = cityIds.joinToString()
                countriesIdsString = countriesIds.joinToString()

            }
            .flatMap {
                getCitiesById(cityIdsString)
                    .map { citiesData ->
                        citiesData.response.forEach { cityResponse ->
                            cityNames.add(cityResponse.title)
                        }
                    }
            }
            .flatMap {
                getCountriesById(countriesIdsString)
                    .map { countryData ->
                        countryData.response.forEach { countryResponse ->
                            countryNames.add(countryResponse.title)
                        }

                    }
            }
            .map {
                for (i in careerList.indices) {
                    newsRepository.insertCareer(
                        career = careerList[i],
                        userProfileId = profileResponse.id,
                        cityName = cityNames[i],
                        countryName = countryNames[i]
                    )
                }
            }
            .flatMap {
                newsRepository.getUserProfile(profileResponse.id)
            }
            .onErrorResumeNext {
                newsRepository.getUserProfile(sharedPreferencesHelper.getUserId())
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getCitiesById(cityIds: String): Single<CitiesData> {
        return newsRepository.getCitiesById(cityIds)
    }

    private fun getCountriesById(countryIds: String): Single<CountryData> {
        return newsRepository.getCountriesById(countryIds)
    }

    fun getWallNews(): Single<List<ProfileAndPostsUiModel>> {
        return newsRepository.getWallNews()
            .subscribeOn(Schedulers.io())
            .map { wallData ->
                val response = wallData.response

                val newsPosts = getPosts(response) as ArrayList

                newsPosts.forEach { post ->
                    newsRepository.insertWallPost(post)

                    post.attachments?.forEach { attachment ->
                        newsRepository.insertWallAttachment(post, attachment)

                        attachment.photo?.sizes?.forEach { photoSize ->
                            newsRepository.insertPhotoSizeWall(attachment, photoSize)
                        }
                    }
                }

                val groups = getGroups(response) as ArrayList

                val profiles = getProfiles(response) as ArrayList

                newsRepository.insertGroups(groups)
                newsRepository.insertProfile(profiles)
            }
            .flatMap {
                newsRepository.getWallPosts()
            }
            .onErrorResumeNext {
                newsRepository.getWallPosts()
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun createPost(message: String): Single<PostsItemAndProfileUiModel> {
        var postId = 0
        return newsRepository.createPost(
            ownerId = sharedPreferencesHelper.getUserId(),
            message = message
        )
            .subscribeOn(Schedulers.io())
            .flatMap { postData ->
                postId = postData.response.postId
                newsRepository.getPostById("${sharedPreferencesHelper.getUserId()}_$postId")
            }
            .map { wallData ->
                val response = wallData.response

                val newsPosts = getPosts(response) as ArrayList

                newsPosts.forEach { post ->
                    newsRepository.insertWallPost(post)

                    post.attachments?.forEach { attachment ->
                        newsRepository.insertWallAttachment(post, attachment)

                        attachment.photo?.sizes?.forEach { photoSize ->
                            newsRepository.insertPhotoSizeWall(attachment, photoSize)
                        }
                    }
                }

                val groups = getGroups(response) as ArrayList

                val profiles = getProfiles(response) as ArrayList

                newsRepository.insertGroups(groups)
                newsRepository.insertProfile(profiles)
            }
            .flatMap {
                newsRepository.getWallPost(postId)
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * DB
     */

    fun getPostsLocal(): Single<NewsAndFavouriteLists> {
        return newsRepository.getPostsLocal()
            .subscribeOn(Schedulers.io())
            .map { postItems ->

                val favouriteNews =
                    postItems.filter { it.post.likes.userLikes == Constants.IS_LIKED } as ArrayList

                NewsAndFavouriteLists(postItems, favouriteNews)
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getCommentsLocal(postId: Int): Single<List<PostAndCommentUiModel.CommentUiModel>> {
        return newsRepository.getCommentsLocal(postId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getCommentFromDb(commentId: Int): Single<PostAndCommentUiModel.CommentUiModel> {
        return newsRepository.getCommentLocal(commentId)
    }
}