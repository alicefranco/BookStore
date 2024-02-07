package pt.pprojects.bookstore

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import pt.pprojects.bookstore.di.appModule
import pt.pprojects.bookstore.di.networkModule
import pt.pprojects.bookstorelist.data.di.bookListDataModule
import pt.pprojects.bookstorelist.datasource.di.pokeListDatasourceModule
import pt.pprojects.bookstorelist.domain.di.bookListDomainModule
import pt.pprojects.bookstorelist.presentation.di.bookListPresentationModule

class PokeApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initModules()
    }

    private fun initModules() {
        startKoin {
            androidContext(this@PokeApp)
            modules(
                listOf(
                    appModule,
                    networkModule,
                    bookListDataModule,
                    pokeListDatasourceModule,
                    bookListDomainModule,
                    bookListPresentationModule
                )
            )
        }
    }
}