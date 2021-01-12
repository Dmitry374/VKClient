package com.dima.vkclient.common

object Constants {
    const val BASE_URL = "https://api.vk.com/method/"
    const val NEWS_METHOD = "newsfeed.get"
    const val LIKES_ADD = "likes.add"
    const val LIKES_DELETE = "likes.delete"
    const val IGNORE_ITEM_IN_NEWS = "newsfeed.ignoreItem"
    const val WALL_GET_COMMENTS = "wall.getComments"
    const val WALL_CREATE_COMMENT = "wall.createComment"
    const val WALL_GET_COMMENT = "wall.getComment"
    const val USERS_GET = "users.get"
    const val GET_COUNTRIES_BY_ID = "database.getCountriesById"
    const val GET_CITIES_BY_ID = "database.getCitiesById"
    const val WALL_GET = "wall.get"
    const val WALL_POST = "wall.post"
    const val WALL_GET_BY_ID = "wall.getById"

    const val WALL_TYPE = "wall"

    const val FILTER_POST = "post"

    const val USER_PROFILE_FIELDS =
        "sex,photo_50,photo_100,online,domain,about,bdate,city,country,career,education,followers_count,last_seen"

    const val API_VERSION = "5.126"

    const val NOT_LIKED = 0
    const val IS_LIKED = 1

    const val CAN_CREATE_COMMENT = 1

    const val DATABASE_NAME = "news_database.db"

    const val ATTACHMENT_TYPE_ALBUM = "album"
    const val ATTACHMENT_TYPE_AUDIO = "audio"
    const val ATTACHMENT_TYPE_DOC = "doc"
    const val ATTACHMENT_TYPE_LINK = "link"
    const val ATTACHMENT_TYPE_MARKET = "market"
    const val ATTACHMENT_TYPE_POOL = "poll"
    const val ATTACHMENT_TYPE_PHOTO = "photo"
    const val ATTACHMENT_TYPE_PODCAST = "podcast"
    const val ATTACHMENT_TYPE_VIDEO = "video"

    const val COMMUNITY_AVATAR_SIZE = 50
    const val PROFILE_AVATAR_SIZE = 70

    const val IMAGE_EXTENSION = "jpg"
    const val IMAGE_MIME_TYPE = "image/jpeg"
    const val IMAGE_QUALITY = 100

    const val USER_ID = "user_id"

    const val AFTER_PROFILE_INDEX = 1
    const val AFTER_POST_INDEX = 1
}