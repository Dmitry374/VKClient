package com.dima.vkclient.common

import com.dima.vkclient.data.domain.comment.CommentDomain
import com.dima.vkclient.data.domain.comment.CommentItem
import com.dima.vkclient.data.domain.comment.LikesCommentDomain
import com.dima.vkclient.data.domain.post.*
import com.dima.vkclient.data.domain.userprofile.CareerDomain
import com.dima.vkclient.data.domain.userprofile.LastSeenDomain
import com.dima.vkclient.data.domain.userprofile.UserProfileItem
import com.dima.vkclient.data.domain.wall.PostWallItem
import com.dima.vkclient.data.domain.wall.WallPostDomain
import com.dima.vkclient.data.domain.wall.WallRepostsDomain
import com.dima.vkclient.data.net.comment.CommentItemNet
import com.dima.vkclient.data.net.comment.CommentLikesNet
import com.dima.vkclient.data.net.post.*
import com.dima.vkclient.data.net.profile.*
import com.dima.vkclient.data.net.wall.WallItemNet
import com.dima.vkclient.data.net.wall.WallRepostsNet
import com.dima.vkclient.database.entity.*
import com.dima.vkclient.database.local.*
import com.dima.vkclient.database.query.*
import com.dima.vkclient.ui.detail.adapter.PostAndCommentUiModel
import com.dima.vkclient.ui.profile.adapter.ProfileAndPostsUiModel
import kotlin.math.abs

class DataMapper {

    fun itemToPostEntity(item: Item): PostEntity {
        return item.toPostEntity()
    }

    private fun Item.toPostEntity() = PostEntity(
        id = this.postId,
        comments = this.comments.toCommentsLocal(),
        date = this.date,
        isFavorite = this.isFavorite,
        likes = this.likes.toLikesLocal(),
        markedAsAds = this.markedAsAds,
        postType = this.postType,
        reposts = this.reposts.toRepostsLocal(),
        sourceId = this.sourceId,
        sourceIdAbs = abs(this.sourceId),
        text = this.text,
        type = this.type,
        views = this.views?.toViewsLocal()
    )

    private fun Comments.toCommentsLocal() = CommentsLocal(
        canPost = this.canPost,
        count = this.count,
        groupsCanPost = this.groupsCanPost
    )

    private fun Likes.toLikesLocal() = LikesLocal(
        canLike = this.canLike,
        canPublish = this.canPublish,
        count = this.count,
        userLikes = this.userLikes
    )

    private fun Reposts.toRepostsLocal() = RepostsLocal(
        count = this.count,
        userReposted = this.userReposted
    )

    private fun Views.toViewsLocal() = ViewsLocal(
        count = this.count
    )

    fun attachmentToAttachmentEntity(attachment: Attachment, postId: Int): AttachmentEntity {
        return attachment.toAttachmentEntity(postId)
    }

    private fun Attachment.toAttachmentEntity(postId: Int) =
        AttachmentEntity(
            id = this.getAttachmentId(),
            type = this.type,
            photo = this.photo?.toPhotoLocal(),
            postId = postId
        )

    private fun Attachment.getAttachmentId(): Int {
        return when (this.type) {
            Constants.ATTACHMENT_TYPE_ALBUM -> {
                this.album?.id ?: 0
            }
            Constants.ATTACHMENT_TYPE_AUDIO -> {
                this.audio?.id ?: 0
            }
            Constants.ATTACHMENT_TYPE_DOC -> {
                this.doc?.id ?: 0
            }
            Constants.ATTACHMENT_TYPE_LINK -> {
                this.link?.photo?.id ?: 0
            }
            Constants.ATTACHMENT_TYPE_MARKET -> {
                this.market?.id ?: 0
            }
            Constants.ATTACHMENT_TYPE_POOL -> {
                this.poll?.id ?: 0
            }
            Constants.ATTACHMENT_TYPE_PHOTO -> {
                this.photo?.id ?: 0
            }
            Constants.ATTACHMENT_TYPE_PODCAST -> {
                this.podcast?.id ?: 0
            }
            Constants.ATTACHMENT_TYPE_VIDEO -> {
                this.video?.id ?: 0
            }
            else -> throw IllegalArgumentException("Unsupported item type: $type")
        }
    }

    private fun PhotoXX.toPhotoLocal() =
        PhotoLocal(
            accessKey = this.accessKey,
            date = this.date,
            hasTags = this.hasTags,
            photoId = this.id,
            text = this.text,
            userId = this.userId
        )

    fun sizePhotoToPhotoSizeEntity(size: SizeXX, idAttachmentPhoto: Int): PhotoSizeEntity {
        return size.toPhotoSizeEntity(idAttachmentPhoto)
    }

    private fun SizeXX.toPhotoSizeEntity(idAttachmentPhoto: Int) =
        PhotoSizeEntity(
            idAttachmentPhoto = idAttachmentPhoto,
            height = this.height,
            type = this.type,
            url = this.url,
            width = this.width
        )

    /**
     * Group
     */

    fun listGroupsToGroupsEntityList(groupsList: List<Group>): List<GroupEntity> {
        return groupsList.toGroupEntityList()
    }

    private fun List<Group>.toGroupEntityList() = this.map { it.toGroupEntity() }

    private fun Group.toGroupEntity() = GroupEntity(
        id = this.id,
        isClosed = this.isClosed,
        name = this.name,
        photo100 = this.photo100,
        photo200 = this.photo200,
        photo50 = this.photo50,
        screenName = this.screenName,
        type = this.type
    )

    /**
     * Profile
     */

    fun listProfilesToProfilesEntityList(profilesList: List<Profile>): List<ProfileEntity> {
        return profilesList.toProfileEntityList()
    }

    private fun List<Profile>.toProfileEntityList() = this.map { it.toProfileEntity() }

    private fun Profile.toProfileEntity() = ProfileEntity(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        online = this.online,
        photo100 = this.photo100,
        photo50 = this.photo50,
        screenName = this.screenName,
        sex = this.sex,
        onlineInfo = this.onlineInfo.toOnlineInfoLocal()
    )

    private fun OnlineInfo.toOnlineInfoLocal() = OnlineInfoLocal(
        appId = this.appId,
        isMobile = this.isMobile,
        isOnline = this.isOnline,
        lastSeen = this.lastSeen,
        visible = this.visible
    )

    fun postsWithGroupAndAttachmentToPostItems(postsWithGroupAndAttachment: List<PostWithGroupAndAttachment>): List<PostItem> {
        return postsWithGroupAndAttachment.toPostsItemList()
    }

    private fun List<PostWithGroupAndAttachment>.toPostsItemList() = this.map { it.toPostItem() }

    private fun PostWithGroupAndAttachment.toPostItem() = PostItem(
        post = this.post.toPostDomain(),
        group = this.group?.toGroupDomain(),
        profile = this.profile?.toProfileDomain(),
        attachments = this.attachments?.toAttachmentWithPhotoDomainList()
    )

    private fun PostEntity.toPostDomain() = PostDomain(
        id = this.id,
        comments = this.comments.toCommentDomain(),
        date = this.date,
        isFavorite = this.isFavorite,
        likes = this.likes.toLikesDomain(),
        markedAsAds = this.markedAsAds,
        postType = this.postType,
        reposts = this.reposts.toRepostsDomain(),
        sourceId = this.sourceId,
        sourceIdAbs = this.sourceIdAbs,
        text = this.text,
        type = this.type,
        views = this.views?.toViewsDomain()
    )

    private fun CommentsLocal.toCommentDomain() = CommentsDomain(
        canPost = this.canPost,
        count = this.count,
        groupsCanPost = this.groupsCanPost
    )

    private fun LikesLocal.toLikesDomain() = LikesDomain(
        canLike = this.canLike,
        canPublish = this.canPublish,
        count = this.count,
        userLikes = this.userLikes
    )

    private fun RepostsLocal.toRepostsDomain() = RepostsDomain(
        count = this.count,
        userReposted = this.userReposted
    )

    private fun ViewsLocal.toViewsDomain() = ViewsDomain(
        count = this.count
    )

    private fun GroupEntity.toGroupDomain() = GroupDomain(
        id = this.id,
        isClosed = this.isClosed,
        name = this.name,
        photo100 = this.photo100,
        photo200 = this.photo200,
        photo50 = this.photo50,
        screenName = this.screenName,
        type = this.type
    )

    private fun List<AttachmentWithPhoto>.toAttachmentWithPhotoDomainList() =
        this.map { it.toAttachmentWithPhotoDomain() }

    private fun AttachmentWithPhoto.toAttachmentWithPhotoDomain() = AttachmentWithPhotoDomain(
        attachment = this.attachment.toAttachmentDomain(),
        photos = this.photos?.toPhotoSizeDomainList()
    )

    private fun AttachmentEntity.toAttachmentDomain() = AttachmentDomain(
        id = this.id,
        type = this.type,
        photo = this.photo?.toPhotoDomain(),
        postId = this.postId
    )

    private fun PhotoLocal.toPhotoDomain() = PhotoDomain(
        accessKey = this.accessKey,
        date = this.date,
        hasTags = this.hasTags,
        photoId = this.photoId,
        text = this.text,
        userId = this.userId
    )

    private fun List<PhotoSizeEntity>.toPhotoSizeDomainList() = this.map { it.toPhotoSizeDomain() }

    private fun PhotoSizeEntity.toPhotoSizeDomain() = PhotoSizeDomain(
        id = this.id,
        idAttachmentPhoto = this.idAttachmentPhoto,
        height = this.height,
        type = this.type,
        url = this.url,
        width = this.width
    )

    fun postDomainToPostEntity(postDomain: PostDomain): PostEntity {
        return postDomain.toPostEntity()
    }

    private fun PostDomain.toPostEntity() = PostEntity(
        id = this.id,
        comments = this.comments.toCommentsLocal(),
        date = this.date,
        isFavorite = this.isFavorite,
        likes = this.likes.toLikesLocal(),
        markedAsAds = this.markedAsAds,
        postType = this.postType,
        reposts = this.reposts.toRepostsLocal(),
        sourceId = this.sourceId,
        sourceIdAbs = this.sourceIdAbs,
        text = this.text,
        type = this.type,
        views = this.views?.toViewsLocal()
    )

    private fun CommentsDomain.toCommentsLocal() = CommentsLocal(
        canPost = this.canPost,
        count = this.count,
        groupsCanPost = this.groupsCanPost
    )

    private fun LikesDomain.toLikesLocal() = LikesLocal(
        canLike = this.canLike,
        canPublish = this.canPublish,
        count = this.count,
        userLikes = this.userLikes
    )

    private fun RepostsDomain.toRepostsLocal() = RepostsLocal(
        count = this.count,
        userReposted = this.userReposted
    )

    private fun ViewsDomain.toViewsLocal() = ViewsLocal(
        count = this.count
    )

    private fun GroupDomain.toGroupEntity() = GroupEntity(
        id = this.id,
        isClosed = this.isClosed,
        name = this.name,
        photo100 = this.photo100,
        photo200 = this.photo200,
        photo50 = this.photo50,
        screenName = this.screenName,
        type = this.type
    )

    private fun List<AttachmentWithPhotoDomain>.toAttachmentWithPhotoList() =
        this.map { it.toAttachmentWithPhoto() }

    private fun AttachmentWithPhotoDomain.toAttachmentWithPhoto() = AttachmentWithPhoto(
        attachment = this.attachment.toAttachmentEntity(),
        photos = this.photos?.toPhotoSizeEntityList()
    )

    private fun AttachmentDomain.toAttachmentEntity() = AttachmentEntity(
        id = this.id,
        type = this.type,
        photo = this.photo?.toPhotoLocal(),
        postId = this.postId
    )

    private fun PhotoDomain.toPhotoLocal() = PhotoLocal(
        accessKey = this.accessKey,
        date = this.date,
        hasTags = this.hasTags,
        photoId = this.photoId,
        text = this.text,
        userId = this.userId
    )

    private fun List<PhotoSizeDomain>.toPhotoSizeEntityList() = this.map { it.toPhotoSizeEntity() }

    private fun PhotoSizeDomain.toPhotoSizeEntity() = PhotoSizeEntity(
        id = this.id,
        idAttachmentPhoto = this.idAttachmentPhoto,
        height = this.height,
        type = this.type,
        url = this.url,
        width = this.width
    )

    /**
     * Comments
     */

    fun listCommentsToCommentsEntity(commentsList: List<CommentItemNet>): List<CommentEntity> {
        return commentsList.toCommentEntityList()
    }

    private fun List<CommentItemNet>.toCommentEntityList() = this.map { it.toCommentEntity() }

    private fun CommentItemNet.toCommentEntity() = CommentEntity(
        id = this.id,
        fromId = this.fromId,
        fromIdAbs = abs(this.fromId),
        postId = this.postId,
        ownerId = this.ownerId,
        date = this.date,
        text = this.text,
        likes = likes?.toCommentLikeLocal()
    )

    private fun CommentLikesNet.toCommentLikeLocal() = LikesLocalComment(
        canLike = this.canLike,
        canPublish = this.canPublish,
        count = this.count,
        userLikes = this.userLikes
    )

    /**
     * To CommentsItems
     */

    fun commentsFromDbToCommentsItems(commentsFromDb: List<CommentDbResult>): List<CommentItem> {
        return commentsFromDb.toCommentsItem()
    }

    private fun List<CommentDbResult>.toCommentsItem() = this.map { it.toCommentItem() }

    fun commentFromDbToCommentItem(commentFromDb: CommentDbResult): CommentItem {
        return commentFromDb.toCommentItem()
    }

    private fun CommentDbResult.toCommentItem() = CommentItem(
        comment = this.comment.toCommentDomain(),
        group = this.group?.toGroupDomain(),
        profile = this.profile?.toProfileDomain()
    )

    private fun CommentEntity.toCommentDomain() = CommentDomain(
        id = this.id,
        fromId = this.fromId,
        postId = this.postId,
        ownerId = this.ownerId,
        date = this.date,
        text = this.text,
        likes = this.likes?.toLikesCommentDomain()
    )

    private fun LikesLocalComment.toLikesCommentDomain() = LikesCommentDomain(
        canLike = this.canLike,
        canPublish = this.canPublish,
        count = this.count,
        userLikes = this.userLikes
    )

    /**
     * To ProfileDomain
     */

    private fun ProfileEntity.toProfileDomain() = ProfileDomain(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        online = this.online,
        photo100 = this.photo100,
        photo50 = this.photo50,
        screenName = this.screenName,
        sex = this.sex,
        onlineInfo = this.onlineInfo.toOnlineInfoDomain()
    )

    private fun OnlineInfoLocal.toOnlineInfoDomain() = OnlineInfoDomain(
        appId = this.appId,
        isMobile = this.isMobile,
        isOnline = this.isOnline,
        lastSeen = this.lastSeen,
        visible = this.visible
    )

    /**
     * CommentItem list to PostAndCommentUiModel.CommentUiModel list
     */

    fun commentsItemsToCommentUiModels(commentItems: List<CommentItem>): List<PostAndCommentUiModel.CommentUiModel> {
        return commentItems.toCommentUiModels()
    }

    private fun List<CommentItem>.toCommentUiModels() =
        this.map { PostAndCommentUiModel.CommentUiModel(it) }

    fun commentItemsToCommentUiModel(commentItem: CommentItem): PostAndCommentUiModel.CommentUiModel {
        return commentItem.toCommentUiModel()
    }

    private fun CommentItem.toCommentUiModel() = PostAndCommentUiModel.CommentUiModel(this)

    /**
     * ProfileResponse to UserProfileEntity
     */

    fun profileResponseToUserProfileEntity(userProfile: ProfileResponse): UserProfileEntity {
        return userProfile.toUserProfileEntity()
    }

    private fun ProfileResponse.toUserProfileEntity() = UserProfileEntity(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        bdate = this.bdate,
        about = this.about,
        city = this.city.toCityLocal(),
        country = this.country.toCountryLocal(),
        domain = this.domain,
        educationForm = this.educationForm,
        educationStatus = this.educationStatus,
        faculty = this.faculty,
        facultyName = this.facultyName,
        followersCount = this.followersCount,
        graduation = this.graduation,
        isClosed = this.isClosed,
        lastSeen = this.lastSeen.toLastSeenLocal(),
        online = this.online,
        onlineMobile = this.onlineMobile,
        photo50 = this.photo50,
        photo100 = this.photo100,
        sex = this.sex,
        university = this.university,
        universityName = this.universityName,
        canAccessClosed = this.canAccessClosed
    )

    private fun City.toCityLocal() = CityLocal(
        id = this.id,
        title = this.title
    )

    private fun Country.toCountryLocal() = CountryLocal(
        id = this.id,
        title = this.title
    )

    private fun LastSeen.toLastSeenLocal() = LastSeenLocal(
        platform = this.platform,
        time = this.time
    )

    /**
     * Career to CareerEntity
     */

    fun careerNetToCareerEntity(
        career: Career,
        userProfileId: Int,
        cityName: String,
        countryName: String
    ): CareerEntity {
        return career.toCareerEntity(userProfileId, cityName, countryName)
    }

    private fun Career.toCareerEntity(userProfileId: Int, cityName: String, countryName: String) =
        CareerEntity(
            company = this.company,
            userProfileId = userProfileId,
            cityId = this.cityId,
            cityName = cityName,
            countryId = this.countryId,
            countryName = countryName,
            position = this.position,
            from = this.from,
            until = this.until
        )

    /**
     * UserProfileDbResult to UserProfileItem
     */

    fun userProfileDbResultToUserProfileItem(userProfileDbResult: UserProfileDbResult): UserProfileItem {
        return userProfileDbResult.toUserProfileItem()
    }

    private fun UserProfileDbResult.toUserProfileItem() = UserProfileItem(
        id = this.profile.id,
        firstName = this.profile.firstName,
        lastName = this.profile.lastName,
        bdate = this.profile.bdate,
        about = this.profile.about,
        city = this.profile.city.title,
        country = this.profile.country.title,
        career = this.career?.toCareerDomainList(),
        domain = this.profile.domain,
        educationForm = this.profile.educationForm,
        educationStatus = this.profile.educationStatus,
        faculty = this.profile.faculty,
        facultyName = this.profile.facultyName,
        followersCount = this.profile.followersCount,
        graduation = this.profile.graduation,
        isClosed = this.profile.isClosed,
        lastSeen = this.profile.lastSeen.toLastSeenDomain(),
        online = this.profile.online,
        onlineMobile = this.profile.onlineMobile,
        photo50 = this.profile.photo50,
        photo100 = this.profile.photo100,
        sex = this.profile.sex,
        university = this.profile.university,
        universityName = this.profile.universityName,
        canAccessClosed = this.profile.canAccessClosed
    )

    private fun List<CareerEntity>.toCareerDomainList() = this.map { it.toCareerDomain() }

    private fun CareerEntity.toCareerDomain() = CareerDomain(
        company = this.company,
        userProfileId = this.userProfileId,
        cityId = this.cityId,
        cityName = this.cityName,
        countryId = this.countryId,
        countryName = this.countryName,
        position = this.position,
        from = this.from,
        until = this.until
    )

    private fun LastSeenLocal.toLastSeenDomain() = LastSeenDomain(
        platform = this.platform,
        time = this.time
    )

    /**
     * WallItem to WallPostEntity
     */

    fun wallItemToWallPostEntity(wallItem: WallItemNet): WallPostEntity {
        return wallItem.toWallPostEntity()
    }

    private fun WallItemNet.toWallPostEntity() = WallPostEntity(
        id = this.id,
        fromId = this.fromId,
        ownerId = this.ownerId,
        ownerIdAbs = abs(this.fromId),
        date = this.date,
        postType = this.postType,
        text = this.text,
        canEdit = this.canEdit,
        canDelete = this.canDelete,
        canPin = this.canPin,
        canArchive = this.canArchive,
        isArchived = this.isArchived,
        comments = this.comments.toCommentsLocal(),
        likes = this.likes.toLikesLocal(),
        reposts = this.reposts.toWallRepostsLocal(),
        isFavorite = this.isFavorite,
        shortTextRate = this.shortTextRate,
        views = this.views?.toViewsLocal()
    )

    private fun WallRepostsNet.toWallRepostsLocal() = WallRepostsLocal(
        count = this.count,
        mailCount = this.mailCount,
        userReposted = this.userReposted,
        wallCount = this.wallCount
    )

    /**
     * Attachment to AttachmentWallEntity
     */

    fun attachmentToAttachmentWallEntity(
        attachment: Attachment,
        postId: Int
    ): AttachmentWallEntity {
        return attachment.toAttachmentWallEntity(postId)
    }

    private fun Attachment.toAttachmentWallEntity(postId: Int) =
        AttachmentWallEntity(
            id = this.getAttachmentId(),
            type = this.type,
            photo = this.photo?.toPhotoLocal(),
            postId = postId
        )

    /**
     * SizePhoto to PhotoSizeWallEntity
     */

    fun sizePhotoToPhotoSizeWallEntity(size: SizeXX, idAttachmentPhoto: Int): PhotoSizeWallEntity {
        return size.toPhotoSizeWallEntity(idAttachmentPhoto)
    }

    private fun SizeXX.toPhotoSizeWallEntity(idAttachmentPhoto: Int) =
        PhotoSizeWallEntity(
            idAttachmentPhoto = idAttachmentPhoto,
            height = this.height,
            type = this.type,
            url = this.url,
            width = this.width
        )

    /**
     * WallPostsDbResult to PostWallItems
     */

    fun wallPostsDbResultToPostWallItems(wallPostsDbResult: List<WallPostDbResult>): List<PostWallItem> {
        return wallPostsDbResult.wallPostsToPostsItemList()
    }

    private fun List<WallPostDbResult>.wallPostsToPostsItemList() = this.map { it.toPostItem() }

    fun wallPostDbResultToPostItem(wallPostDbResult: WallPostDbResult): PostWallItem {
        return wallPostDbResult.toPostItem()
    }

    private fun WallPostDbResult.toPostItem() = PostWallItem(
        post = this.post.toWallPostDomain(),
        group = this.group?.toGroupDomain(),
        profile = this.profile?.toProfileDomain(),
        attachments = this.attachments?.toAttachmentWithPhotoDomainListWall()
    )

    private fun WallPostEntity.toWallPostDomain() = WallPostDomain(
        id = this.id,
        fromId = this.fromId,
        ownerId = this.ownerId,
        ownerIdAbs = this.ownerIdAbs,
        date = this.date,
        postType = this.postType,
        text = this.text,
        canEdit = this.canEdit,
        canDelete = this.canDelete,
        canPin = this.canPin,
        canArchive = this.canArchive,
        isArchived = this.isArchived,
        comments = this.comments.toCommentDomain(),
        likes = this.likes.toLikesDomain(),
        reposts = this.reposts.toWallRepostsDomain(),
        isFavorite = this.isFavorite,
        shortTextRate = this.shortTextRate,
        views = this.views?.toViewsDomain()
    )

    private fun WallRepostsLocal.toWallRepostsDomain() = WallRepostsDomain(
        count = this.count,
        mailCount = this.mailCount,
        userReposted = this.userReposted,
        wallCount = this.wallCount
    )

    private fun List<WallAttachmentWithPhoto>.toAttachmentWithPhotoDomainListWall() =
        this.map { it.toAttachmentWithPhotoDomain() }

    private fun WallAttachmentWithPhoto.toAttachmentWithPhotoDomain() = AttachmentWithPhotoDomain(
        attachment = this.attachment.toAttachmentDomain(),
        photos = this.photos?.toPhotoSizeDomainListWall()
    )

    private fun AttachmentWallEntity.toAttachmentDomain() = AttachmentDomain(
        id = this.id,
        type = this.type,
        photo = this.photo?.toPhotoDomain(),
        postId = this.postId
    )

    private fun List<PhotoSizeWallEntity>.toPhotoSizeDomainListWall() =
        this.map { it.toPhotoSizeDomain() }

    private fun PhotoSizeWallEntity.toPhotoSizeDomain() = PhotoSizeDomain(
        id = this.id,
        idAttachmentPhoto = this.idAttachmentPhoto,
        height = this.height,
        type = this.type,
        url = this.url,
        width = this.width
    )

    /**
     * PostWallItems to PostItems
     */

    fun postWallItemsToPostItems(postWallItems: List<PostWallItem>): List<PostItem> {
        return postWallItems.toPostItems()
    }

    private fun List<PostWallItem>.toPostItems() = this.map { it.toPostItem() }

    fun postWallItemToPostItem(postWallItem: PostWallItem): PostItem {
        return postWallItem.toPostItem()
    }

    private fun PostWallItem.toPostItem() = PostItem(
        post = this.post.toPostDomain(),
        group = this.group,
        profile = this.profile,
        attachments = this.attachments
    )

    private fun WallPostDomain.toPostDomain() = PostDomain(
        id = this.id,
        comments = this.comments,
        date = this.date,
        isFavorite = this.isFavorite,
        likes = this.likes,
        markedAsAds = 0,
        postType = this.postType,
        reposts = this.reposts.toRepostsDomain(),
        sourceId = this.ownerId,
        sourceIdAbs = this.ownerIdAbs,
        text = this.text,
        type = this.postType,
        views = this.views
    )

    private fun WallRepostsDomain.toRepostsDomain() = RepostsDomain(
        count = this.count,
        userReposted = this.userReposted
    )

    /**
     * PostItems to ProfileAndPostsUiModels
     */

    fun postItemsToProfileAndPostsUiModels(postItems: List<PostItem>): List<ProfileAndPostsUiModel> {
        return postItems.toProfileAndPostsUiModels()
    }

    private fun List<PostItem>.toProfileAndPostsUiModels() = this.map { postItem ->
        if (postItem.attachments.isNullOrEmpty() || postItem.attachments.count { it.attachment.type == Constants.ATTACHMENT_TYPE_PHOTO } == 0) {
            postItem.toProfileAndPostWithTextUiModel()
        } else {
            postItem.toProfileAndPostWithTextAndImageUiModel()
        }
    }

    private fun PostItem.toProfileAndPostWithTextUiModel() =
        ProfileAndPostsUiModel.PostWithTextUiModel(this)

    private fun PostItem.toProfileAndPostWithTextAndImageUiModel() =
        ProfileAndPostsUiModel.PostWithTextAndImageUiModel(this)

    fun postItemToProfileAndPostsUiModel(postItem: PostItem): ProfileAndPostsUiModel {
        return if (postItem.attachments.isNullOrEmpty() || postItem.attachments.count { it.attachment.type == Constants.ATTACHMENT_TYPE_PHOTO } == 0) {
            postItem.toProfileAndPostWithTextUiModel()
        } else {
            postItem.toProfileAndPostWithTextAndImageUiModel()
        }
    }
}