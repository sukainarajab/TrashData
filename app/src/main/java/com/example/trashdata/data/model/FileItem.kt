package com.example.trashdata.data.model
data class FileItem(

    val name: String,

    val size: Long,

    val lastModified: Long,

    var daysUnused: Int = 0,

    var isForgotten: Boolean = false,

    var isLarge: Boolean = false

)

