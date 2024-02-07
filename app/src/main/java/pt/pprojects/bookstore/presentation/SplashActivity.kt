package pt.pprojects.bookstore.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import pt.pprojects.bookstorelist.presentation.bookstorelist.BookListActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({
            startActivity(Intent(this, BookListActivity::class.java))
            finish()
        },
            DELAY_SIMULATION
        )
    }

    private companion object {
        private const val DELAY_SIMULATION = 1000L
    }
}
