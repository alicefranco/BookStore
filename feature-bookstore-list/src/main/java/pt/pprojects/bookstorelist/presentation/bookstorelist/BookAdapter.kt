package pt.pprojects.bookstorelist.presentation.bookstorelist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.pprojects.bookstorelist.databinding.ItemBookBinding
import pt.pprojects.bookstorelist.domain.model.Book
import pt.pprojects.bookstorelist.presentation.model.BookItem
import pt.pprojects.bookstorelist.presentation.setOptionalImage

class BookAdapter(private val context: Context) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {
    private var listItems: List<BookItem> = listOf()

    private var bookItemClick: (book: Book) -> Unit = {}
    private var loadMoreAction: () -> Unit = {}
    private var loadMore = true
    private var showFavourite = false
    fun addBooks(bookItems: List<BookItem>, more: Boolean, isFavourite: Boolean) {
        loadMore = more
        listItems = bookItems
        showFavourite = isFavourite
    }

    fun addBookItemClick(itemClickAction: (book: Book) -> Unit) {
        bookItemClick = itemClickAction
    }

    fun addLoadMoreAction(clickAction: () -> Unit) {
        loadMoreAction = clickAction
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bindingBookItem = ItemBookBinding.inflate(inflater, parent, false)

        return ViewHolder(bindingBookItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, listItems[position], bookItemClick)

        if (position == (itemCount - 1) && loadMore && !showFavourite) {
            loadMoreAction()
        }
    }

    class ViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, book: BookItem, itemClick: (book: Book) -> Unit) {

            binding.ivBook.setOptionalImage(book.image, context)

            binding.layoutItemBook.setOnClickListener {
                itemClick(
                    Book(
                        authors = book.authors,
                        title = book.title,
                        description = book.description,
                        image = book.image,
                        buyLink = book.buyLink,
                        id = book.id
                    )
                )
            }
        }
    }
}
