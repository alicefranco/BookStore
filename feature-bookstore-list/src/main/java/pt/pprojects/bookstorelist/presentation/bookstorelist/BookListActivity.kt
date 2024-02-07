package pt.pprojects.bookstorelist.presentation.bookstorelist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import pt.pprojects.domain.Result
import pt.pprojects.bookstorelist.R
import pt.pprojects.bookstorelist.databinding.ActivityBooklistBinding
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

    private val bookItemClick: () -> Unit = {
        openBookDetailsScreen()
    }

    private val loadMoreAction: () -> Unit = {
        bookListViewModel.getBooks(false)
    }

    private fun openBookDetailsScreen() {
        val intent = Intent(this, BookDetailsActivity::class.java)
//        intent.putExtra(POKEMON_ID)
        startActivity(intent)
    }

    companion object {
        const val POKEMON_ID = "POKEMON_ID"
    }
}