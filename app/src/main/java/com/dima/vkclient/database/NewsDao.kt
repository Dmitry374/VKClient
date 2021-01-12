package com.dima.vkclient.database

import androidx.room.*
import com.dima.vkclient.database.entity.*
import com.dima.vkclient.database.query.CommentDbResult
import com.dima.vkclient.database.query.PostWithGroupAndAttachment
import com.dima.vkclient.database.query.UserProfileDbResult
import com.dima.vkclient.database.query.WallPostDbResult
import io.reactivex.Single

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(postEntity: PostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAttachment(attachmentEntity: AttachmentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhotoSize(photoSizeEntity: PhotoSizeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGroups(groups: List<GroupEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfiles(profiles: List<ProfileEntity>)

    @Transaction
    @Query("SELECT * FROM post ORDER BY date DESC")
    fun getPostsAndGroups(): Single<List<PostWithGroupAndAttachment>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePost(postEntity: PostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComments(comments: List<CommentEntity>)

    @Transaction
    @Query("SELECT * FROM comment WHERE post_id = :postId ORDER BY date DESC")
    fun getComments(postId: Int): Single<List<CommentDbResult>>

    @Transaction
    @Query("SELECT * FROM comment WHERE id = :commentId LIMIT 1")
    fun getComment(commentId: Int): Single<CommentDbResult>

    @Query("DELETE FROM comment WHERE post_id = :postId")
    fun deleteComments(postId: Int)

    @Query("UPDATE post SET count_comments = :commentCount WHERE id = :postId")
    fun updatePostCommentCount(postId: Int, commentCount: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserProfile(userProfile: UserProfileEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCareer(careerEntity: CareerEntity)

    @Transaction
    @Query("SELECT * FROM user_profile WHERE id = :userId LIMIT 1")
    fun getUserProfile(userId: Int): Single<UserProfileDbResult>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWallPost(wallPostEntity: WallPostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAttachmentWall(attachmentWallEntity: AttachmentWallEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhotoSizeWall(photoSizeWallEntity: PhotoSizeWallEntity)

    @Transaction
    @Query("SELECT * FROM wall_post ORDER BY date DESC")
    fun getWallPosts(): Single<List<WallPostDbResult>>

    @Transaction
    @Query("SELECT * FROM wall_post WHERE id = :postId ORDER BY date DESC LIMIT 1")
    fun getWallPost(postId: Int): Single<WallPostDbResult>

    @Query("DELETE FROM post")
    fun clearPosts()

    @Query("DELETE FROM wall_post")
    fun clearWallPosts()

    @Query("DELETE FROM attachment")
    fun clearAttachments()

    @Query("DELETE FROM attachment_wall")
    fun clearWallAttachments()

    @Query("DELETE FROM photo_size")
    fun clearPhotoSize()

    @Query("DELETE FROM photo_size_wall")
    fun clearPhotoSizeWall()

    @Query("DELETE FROM comment")
    fun clearComments()

    @Query("DELETE FROM groups")
    fun clearGroups()

    @Query("DELETE FROM profile")
    fun clearProfiles()

    @Query("DELETE FROM user_profile")
    fun clearUserProfile()

    @Query("DELETE FROM career")
    fun clearUserCareerInformation()
}