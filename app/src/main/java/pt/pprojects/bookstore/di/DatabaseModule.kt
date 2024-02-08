package pt.pprojects.bookstore.di

import androidx.room.Room
import org.koin.dsl.module
import pt.pprojects.bookstorelist.datasource.local.BookDao
import pt.pprojects.bookstorelist.datasource.local.BookDatabase

const val RUNNING_DATABASE_NAME = "book-database-name"

val databaseModule = module {
    single<BookDatabase> {
        Room.databaseBuilder(
            get(),
            BookDatabase::class.java,
            RUNNING_DATABASE_NAME
        ).build()
    }

    single<BookDao> {
        val database = get<BookDatabase>()
        database.bookDao()
    }
}