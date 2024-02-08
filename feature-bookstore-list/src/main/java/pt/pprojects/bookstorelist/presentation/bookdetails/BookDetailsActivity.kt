package pt.pprojects.bookstorelist.presentation.bookdetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pt.pprojects.bookstorelist.databinding.ActivityBookDetailsBinding
import pt.pprojects.bookstorelist.presentation.bookstorelist.BookListActivity
import pt.pprojects.bookstorelist.presentation.gone
import pt.pprojects.bookstorelist.presentation.setOptionalImage
import pt.pprojects.bookstorelist.presentation.visible


class BookDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookDetailsBinding
    private var title: String? = null
    private var description: String? = null
    private var author: String? = null
    private var image: String? = null
    private var buyLink: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = intent.getStringExtra(BookListActivity.BOOK_TITLE)
        description = intent.getStringExtra(BookListActivity.BOOK_DESCRIPTION)
        author = intent.getStringExtra(BookListActivity.BOOK_AUTHOR)
        image = intent.getStringExtra(BookListActivity.BOOK_IMAGE)
        buyLink = intent.getStringExtra(BookListActivity.BOOK_BUY_LINK)

        setBookDetails()

        binding.ivClose.setOnClickListener {
            closePokemonDetailsScreen()
        }
    }

    private fun setBookDetails() {
        binding.ivBook.setOptionalImage(image, this)

        binding.tvTitle.text = title
        binding.tvAuthor.text = author
        binding.tvDescription.text = description

        binding.pbDetails.gone()
        binding.layoutDetails.visible()

        buyLink?.let {
            if (it.isNotEmpty()) {
                binding.btBuy.visible()
                binding.btBuy.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(buyLink))
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
                    startActivity(intent);
                }
            }
        }
    }

    private fun closePokemonDetailsScreen() {
        finish()
    }
}