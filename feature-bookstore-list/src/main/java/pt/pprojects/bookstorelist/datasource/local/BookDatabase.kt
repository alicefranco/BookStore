package pt.pprojects.bookstorelist.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pt.pprojects.bookstorelist.domain.model.Book

@Database(entities = [Book::class], version = 1)
@TypeConverters(BookTypeConverter::class)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}