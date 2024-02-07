package pt.pprojects.bookstore.di

import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.dsl.module

val appModule = module {
    single { Schedulers.io() }
}