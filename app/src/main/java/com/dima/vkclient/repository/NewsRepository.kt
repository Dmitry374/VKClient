package com.dima.vkclient.repository

import com.dima.vkclient.common.DataMapper
import com.dima.vkclient.data.PostsItemAndProfileUiModel
import com.dima.vkclient.data.domain.post.PostItem
import com.dima.vkclient.data.net.comment.CommentData
import com.dima.vkclient.data.net.comment.CommentItemNet
import com.dima.vkclient.data.net.comment.CreateCommentResponse
import com.dima.vkclient.data.net.post.*
import com.dima.vkclient.data.net.profile.*
import com.dima.vkclient.data.net.wall.WallData
import com.dima.vkclient.data.net.wall.WallItemNet
import com.dima.vkclient.database.NewsDao
import com.dima.vkclient.network.ApiService
import com.dima.vkclient.ui.detail.adapter.PostAndCommentUiModel
import com.dima.vkclient.ui.profile.adapter.ProfileAndPostsUiModel
import io.reactivex.Single

class NewsRepository(
    private val apiService: ApiService,
    private val newsDao: NewsDao,
    private val dataMapper: DataMapper
) {

    /**
     * Web
     */

    fun loadNews(filters: String): Single<PostData> {
        return apiService.getNews(filters)
    }

    fun addLike(postItem: PostItem): Single<LikeResponse> {
        return apiService.addLike(
            type = postItem.post.type,
            ownerId = postItem.post.sourceId,
            itemId = postItem.post.id
        )
    }

    fun deleteLike(postItem: PostItem): Single<LikeResponse> {
        return apiService.deleteLike(
            type = postItem.post.type,
            ownerId = postItem.post.sourceId,
            itemId = postItem.post.id
        )
    }

    fun hidePost(postItem: PostItem, type: String): Single<HidePostResponse> {
        return apiService.hidePost(
            type = type,
            ownerId = postItem.post.sourceId,
            itemId = postItem.post.id
        )
    }

    fun loadPostComments(
        ownerId: Int,
        postId: Int,
        count: Int,
        needLikes: Int,
        offset: Int,
        extended: Int,
        threadItemsCount: Int
    ): Single<CommentData> {
        return apiService.getPostComments(
            ownerId = ownerId,
            postId = postId,
            count = count,
            needLikes = needLikes,
            offset = offset,
            extended = extended,
            threadItemsCount = threadItemsCount
        )
    }

    fun createComment(ownerId: Int, postId: Int, message: String): Single<CreateCommentResponse> {
        return apiService.createComment(
            ownerId = ownerId,
            postId = postId,
            message = message
        )
    }

    fun getComment(ownerId: Int, commentId: Int, extended: Int): Single<CommentData> {
        return apiService.getComment(
            ownerId = ownerId,
            commentId = commentId,
            extended = extended
        )
    }

    fun getUserData(fields: String): Single<ProfileData> {
        return apiService.getUserProfile(fields)
    }

    fun getCountriesById(countryIds: String): Single<CountryData> {
        return apiService.getCountriesById(countryIds)
    }

    fun getCitiesById(cityIds: String): Single<CitiesData> {
        return apiService.getCitiesById(cityIds)
    }

    fun getWallNews(): Single<WallData> {
        return apiService.getWallNews()
    }

    fun createPost(ownerId: Int, message: String): Single<CreatePostData> {
        return apiService.createPost(
            ownerId = ownerId,
            message = message
        )
    }

    fun getPostById(posts: String): Single<WallData> {
        return apiService.getPostById(posts)
    }

    /**
     * DB
     */

    fun insertPost(post: Item) {
        newsDao.insertPost(dataMapper.itemToPostEntity(post))
    }

    fun insertAttachment(post: Item, attachment: Attachment) {
        newsDao.insertAttachment(dataMapper.attachmentToAttachmentEntity(attachment, post.postId))
    }

    fun insertPhotoSize(attachment: Attachment, photoSize: SizeXX) {
        if (attachment.photo != null) {
            newsDao.insertPhotoSize(
                dataMapper.sizePhotoToPhotoSizeEntity(
                    photoSize,
                    attachment.photo.id
                )
            )
        }
    }

    fun insertGroups(groups: List<Group>) {
        newsDao.insertGroups(dataMapper.listGroupsToGroupsEntityList(groups))
    }

    fun insertProfile(profiles: List<Profile>) {
        newsDao.insertProfiles(dataMapper.listProfilesToProfilesEntityList(profiles))
    }

    fun updatePostCommentCount(postId: Int, commentCount: Int) {
        newsDao.updatePostCommentCount(postId, commentCount)
    }

    fun getPostsLocal(): Single<List<PostItem>> {
        return newsDao.getPostsAndGroups()
            .map { postItems ->
                dataMapper.postsWithGroupAndAttachmentToPostItems(postItems) as ArrayList
            }
    }

    fun updatePostLocal(postItem: PostItem) {
        newsDao.updatePost(dataMapper.postDomainToPostEntity(postItem.post))
    }

    fun deletePostComments(postId: Int) {
        newsDao.deleteComments(postId)
    }

    fun insertComments(comments: List<CommentItemNet>) {
        newsDao.insertComments(dataMapper.listCommentsToCommentsEntity(comments))
    }

    fun getCommentsLocal(postId: Int): Single<List<PostAndCommentUiModel.CommentUiModel>> {
        return newsDao.getComments(postId)
            .map { commentDbResult ->
                val comments = dataMapper.commentsFromDbToCommentsItems(commentDbResult)
                dataMapper.commentsItemsToCommentUiModels(comments)
            }
    }

    fun getCommentLocal(commentId: Int): Single<PostAndCommentUiModel.CommentUiModel> {
        return newsDao.getComment(commentId)
            .map { commentDbResult ->
                val comment = dataMapper.commentFromDbToCommentItem(commentDbResult)
                dataMapper.commentItemsToCommentUiModel(comment)
            }
    }

    fun insertUserProfile(userProfile: ProfileResponse) {
        newsDao.insertUserProfile(dataMapper.profileResponseToUserProfileEntity(userProfile))
    }

    fun insertCareer(career: Career, userProfileId: Int, cityName: String, countryName: String) {
        newsDao.insertCareer(
            dataMapper.careerNetToCareerEntity(
                career = career,
                userProfileId = userProfileId,
                cityName = cityName,
                countryName = countryName
            )
        )
    }

    fun getUserProfile(userId: Int): Single<ProfileAndPostsUiModel.ProfileUiModel> {
        return newsDao.getUserProfile(userId)
            .map { userProfileDbResult ->
                val userProfileItem =
                    dataMapper.userProfileDbResultToUserProfileItem(userProfileDbResult)
                ProfileAndPostsUiModel.ProfileUiModel(userProfileItem)
            }
    }

    fun insertWallPost(wallItem: WallItemNet) {
        newsDao.insertWallPost(dataMapper.wallItemToWallPostEntity(wallItem))
    }

    fun insertWallAttachment(post: WallItemNet, attachment: Attachment) {
        newsDao.insertAttachmentWall(
            dataMapper.attachmentToAttachmentWallEntity(
                attachment,
                post.id
            )
        )
    }

    fun insertPhotoSizeWall(attachment: Attachment, photoSize: SizeXX) {
        if (attachment.photo != null) {
            newsDao.insertPhotoSizeWall(
                dataMapper.sizePhotoToPhotoSizeWallEntity(
                    photoSize,
                    attachment.photo.id
                )
            )
        }
    }

    fun getWallPosts(): Single<List<ProfileAndPostsUiModel>> {
        return newsDao.getWallPosts()
            .map { wallPostDbResult ->
                val postWallItems = dataMapper.wallPostsDbResultToPostWallItems(wallPostDbResult)
                val postsItems = dataMapper.postWallItemsToPostItems(postWallItems)

                dataMapper.postItemsToProfileAndPostsUiModels(postsItems)
            }
    }

    fun getWallPost(postId: Int): Single<PostsItemAndProfileUiModel> {
        return newsDao.getWallPost(postId)
            .map { wallPostDbResult ->
                val postWallItem = dataMapper.wallPostDbResultToPostItem(wallPostDbResult)
                val postsItem = dataMapper.postWallItemToPostItem(postWallItem)
                val postsItemAndProfileUiModel =
                    dataMapper.postItemToProfileAndPostsUiModel(postsItem)

                PostsItemAndProfileUiModel(postsItem, postsItemAndProfileUiModel)
            }
    }

    fun clearAllData() {
        newsDao.clearPosts()
        newsDao.clearWallPosts()
        newsDao.clearAttachments()
        newsDao.clearWallAttachments()
        newsDao.clearPhotoSize()
        newsDao.clearPhotoSizeWall()
        newsDao.clearComments()
        newsDao.clearGroups()
        newsDao.clearProfiles()
        newsDao.clearUserProfile()
        newsDao.clearUserCareerInformation()
    }
}