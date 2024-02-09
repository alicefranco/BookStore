package pt.pprojects.bookstorelist.presentation.bookstorelist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import pt.pprojects.domain.Result
import pt.pprojects.bookstorelist.R
import pt.pprojects.bookstorelist.databinding.ActivityBooklistBinding
import pt.pprojects.bookstorelist.domain.model.Book
import pt.pprojects.bookstorelist.presentation.gone
import pt.pprojects.bookstorelist.presentation.bookdetails.BookDetailsActivity
import pt.pprojects.bookstorelist.presentation.model.BookItem
import pt.pprojects.bookstorelist.presentation.showDialog
import pt.pprojects.bookstorelist.presentation.visible

class BookListActivity : AppCompatActivity() {

    private val bookListViewModel: BookListViewModel by viewModel()
    private lateinit var bookAdapter: BookAdapter
    private val layoutManager = GridLayoutManager(this, 2)

    private lateinit var binding: ActivityBooklistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBooklistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecycler()

        bookListViewModel.books.observe(this) {
            handleResult(it)
        }

        getBooks()
    }

    private fun setupRecycler() {
        bookAdapter = BookAdapter(this)
        bookAdapter.addBookItemClick(bookItemClick)
        bookAdapter.addLoadMoreAction(loadMoreAction)

        binding.rvBooks.layoutManager = layoutManager
        binding.rvBooks.adapter = bookAdapter
    }

    private fun handleResult(result: Result<List<BookItem>>) {
        when (result) {
            is Result.Success -> {
                binding.pbListLoading.gone()
                binding.rvBooks.visible()
                bookAdapter.addBooks(result.data, bookListViewModel.loadedAll)
                bookAdapter.notifyDataSetChanged()
            }
            is Result.Loading -> {
            }
            is Result.Error -> {
                showErrorDialog(getString(R.string.error_title), result.cause.message)
            }
        }
    }

    private fun showErrorDialog(
        title: String,
        message: String?
    ) {
        this.showDialog(
            title,
            message + getString(R.string.error_reload_message),
            positiveAction = {
                getBooks()
            },
            negativeAction = {
                finish()
            }
        )
    }

    private fun getBooks() {
        bookListViewModel.getBooks()
    }

    private val bookItemClick: (book: Book) -> Unit = { book ->
        openBookDetailsScreen(book)
    }

    private val loadMoreAction: () -> Unit = {
        bookListViewModel.getBooks(false)
    }

    private fun openBookDetailsScreen(book: Book) {
        val intent = Intent(this, BookDetailsActivity::class.java)
        intent.putExtra(BOOK_AUTHOR, book.authors.first())
        intent.putExtra(BOOK_TITLE, book.title)
        intent.putExtra(BOOK_ID, book.id)
        intent.putExtra(BOOK_DESCRIPTION, book.description)
        intent.putExtra(BOOK_BUY_LINK, book.buyLink)
        intent.putExtra(BOOK_IMAGE, book.image)
        startActivity(intent)
    }

    companion object {
        const val BOOK_ID = "BOOK_ID"
        const val BOOK_TITLE = "BOOK_TITLE"
        const val BOOK_AUTHOR = "BOOK_AUTHOR"
        const val BOOK_DESCRIPTION = "BOOK_DESCRIPTION"
        const val BOOK_BUY_LINK = "BOOK_BUY_LINK"
        const val BOOK_IMAGE = "BOOK_IMAGE"
    }
}