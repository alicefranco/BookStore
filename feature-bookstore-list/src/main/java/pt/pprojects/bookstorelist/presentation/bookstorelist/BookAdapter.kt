package pt.pprojects.bookstorelist.presentation.bookstorelist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.pprojects.bookstorelist.databinding.ItemBookBinding
import pt.pprojects.bookstorelist.databinding.ItemLoadMoreBinding
import pt.pprojects.bookstorelist.presentation.model.BookItem
import pt.pprojects.bookstorelist.presentation.model.ListItem
import pt.pprojects.bookstorelist.presentation.setOptionalImage

class BookAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val LOADING_ITEM_VIEW = 1
    private val BOOK_ITEM_VIEW = 2

    private var listItems: List<ListItem> = listOf()
    private val loadingItem = ListItem(ListItem.LOADING_ITEM)

    private var bookItemClick: () -> Unit = {}
    private var loadMoreAction: () -> Unit = {}
    private var finished: Boolean = false

    fun addBooks(bookItems: List<BookItem>, finished: Boolean) {
        val newItems: ArrayList<ListItem> = ArrayList(bookItems)

        when (listItems.isEmpty()) {
            true -> {
                newItems.add(loadingItem)
                listItems = newItems
            }
            false -> {
                val currentItems: ArrayList<ListItem> = ArrayList(listItems)
                currentItems.remove(currentItems.last())

                currentItems.addAll(newItems)
                if (!finished)
                    currentItems.add(loadingItem)

                listItems = currentItems
            }
        }
    }

    fun addBookItemClick(itemClickAction: () -> Unit) {
        bookItemClick = itemClickAction
    }

    fun addLoadMoreAction(clickAction: () -> Unit) {
        loadMoreAction = clickAction
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun getItemViewType(position: Int): Int {
        var itemType = BOOK_ITEM_VIEW
        when (listItems[position].itemType) {
            ListItem.LOADING_ITEM ->
                itemType = LOADING_ITEM_VIEW
            ListItem.LIST_ITEM ->
                itemType = BOOK_ITEM_VIEW
        }
        return itemType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bindingLoadingItem = ItemLoadMoreBinding.inflate(inflater, parent, false)
        val bindingBookItem = ItemBookBinding.inflate(inflater, parent, false)

        var viewHolder: RecyclerView.ViewHolder = ViewHolderBookItem(bindingBookItem)

        if (viewType == LOADING_ITEM_VIEW) {
            viewHolder = ViewHolderLoadMoreItem(bindingLoadingItem)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            LOADING_ITEM_VIEW -> {
                (holder as ViewHolderLoadMoreItem).bind(loadMoreAction)
            }
            BOOK_ITEM_VIEW -> {
                (holder as ViewHolderBookItem).bind(context, listItems[position] as BookItem, bookItemClick)
            }
        }
    }

    class ViewHolderBookItem(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, book: BookItem, itemClick: () -> Unit) {

            binding.ivBook.setOptionalImage(book.image, context)

            binding.layoutItemBook.setOnClickListener { itemClick() }
        }
    }

    class ViewHolderLoadMoreItem(binding: ItemLoadMoreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadMoreAction: () -> Unit) {
            loadMoreAction()
        }
    }
}
