package com.dima.vkclient.ui.detail.adapter

interface PostRowListener {

    fun saveImageToGallery()

    fun shareImage(url: String?)

}