package com.imax.edumeet.data.remote.models.group

import com.google.gson.annotations.SerializedName
import com.imax.edumeet.presentation.models.group.GroupItem

data class GroupResponse(
    @SerializedName("_id")
    val id: String,
    val name: String,
    @SerializedName("__v")
    val v: Int
)

fun GroupResponse.toGroupItem(): GroupItem {
    return GroupItem(this.id, this.name)
}
