package pt.pprojects.bookstorelist.datasource.remote.model

data class VolumeInfoResponse(
    val title: String,
    val authors: List<String>,
    val imageLinks: ImageResponse? = null
)
