package pt.pprojects.bookstorelist.presentation.bookdetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import pt.pprojects.bookstorelist.R
import pt.pprojects.bookstorelist.databinding.ActivityBookDetailsBinding
import pt.pprojects.bookstorelist.domain.model.Book
import pt.pprojects.bookstorelist.presentation.bookstorelist.BookListActivity
import pt.pprojects.bookstorelist.presentation.gone
import pt.pprojects.bookstorelist.presentation.setOptionalImage
import pt.pprojects.bookstorelist.presentation.visible


class BookDetailsActivity : AppCompatActivity() {
    private val detailsViewModel: BookDetailsViewModel by viewModel()
    private lateinit var binding: ActivityBookDetailsBinding
    private lateinit var book: Book
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(BookListActivity.BOOK_ID)
        val title = intent.getStringExtra(BookListActivity.BOOK_TITLE)
        val description = intent.getStringExtra(BookListActivity.BOOK_DESCRIPTION)
        val author = intent.getStringExtra(BookListActivity.BOOK_AUTHOR)
        val image = intent.getStringExtra(BookListActivity.BOOK_IMAGE)
        val buyLink = intent.getStringExtra(BookListActivity.BOOK_BUY_LINK)

        book = Book(
            id = id ?: "",
            title = title ?: "",
            description = description ?: "",
            authors = listOf(author ?: ""),
            buyLink = buyLink ?: "",
            image = image ?: ""
        )

        setBookDetails()

        binding.ivClose.setOnClickListener {
            closePokemonDetailsScreen()
        }
    }

    private fun setBookDetails() {
        binding.ivBook.setOptionalImage(book.image, this)

        binding.tvTitle.text = book.title
        binding.tvAuthor.text = book.authors.first()
        binding.tvDescription.text = book.description

        binding.pbDetails.gone()
        binding.layoutDetails.visible()

        book.buyLink?.let {
            if (it.isNotEmpty()) {
                binding.btBuy.visible()
                binding.btBuy.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(book.buyLink))
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
                    startActivity(intent);
                }
            }
        }

        detailsViewModel.isFavourite(book.id)
        detailsViewModel.isFavourite.observe(this) {
            if (it) {
                binding.ivFavorite.setImageResource(R.drawable.ic_heart_fill)
                binding.ivFavorite.setOnClickListener {
                    detailsViewModel.removeFavourite(book)
                }
            } else {
                binding.ivFavorite.setImageResource(R.drawable.ic_heart)
                binding.ivFavorite.setOnClickListener {
                    detailsViewModel.markAsFavorite(book)
                }
            }
        }
    }

    private fun closePokemonDetailsScreen() {
        finish()
    }
}