package pt.pprojects.bookstorelist.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(
    @PrimaryKey
    val id: String,
    val authors: List<String>,
    val title: String,
    val description: String,
    val image: String,
    val buyLink: String,
    val favourite: Boolean = false
)