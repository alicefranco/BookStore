package pt.pprojects.bookstorelist.datasource.remote.model

data class BookResponse(
    val id: String,
    val volumeInfo: VolumeInfoResponse? = null,
    val saleInfo: SaleInfoResponse? = null
)