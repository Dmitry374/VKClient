package com.dima.vkclient.data

import androidx.recyclerview.widget.DiffUtil
import com.dima.vkclient.data.domain.post.PostItem

class NewListAndDiffResult(val newList: List<PostItem>, val diffResult: DiffUtil.DiffResult)