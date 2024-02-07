package pt.pprojects.bookstorelist.presentation.bookstorelist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.pprojects.bookstorelist.databinding.ItemLoadMoreBinding
import pt.pprojects.bookstorelist.databinding.ItemPokemonBinding
import pt.pprojects.bookstorelist.presentation.model.ListItem
import pt.pprojects.bookstorelist.presentation.model.PokemonItem

class PokemonsAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val LOADING_ITEM_VIEW = 1
    private val POKEMON_ITEM_VIEW = 2

    private var listItems: List<ListItem> = listOf()
    private val loadingItem = ListItem(ListItem.LOADING_ITEM)

    private var pokemonItemClick: (pokemonId: Int) -> Unit = {}
    private var loadMoreAction: () -> Unit = {}
    private var finished: Boolean = false

    fun addPokemons(pokemonItems: List<PokemonItem>, finished: Boolean) {
        val newItems: ArrayList<ListItem> = ArrayList(pokemonItems)

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

    fun addPokemonItemClick(itemClickAction: (pokemonId: Int) -> Unit) {
        pokemonItemClick = itemClickAction
    }

    fun addLoadMoreAction(clickAction: () -> Unit) {
        loadMoreAction = clickAction
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun getItemViewType(position: Int): Int {
        var itemType = POKEMON_ITEM_VIEW
        when (listItems[position].itemType) {
            ListItem.LOADING_ITEM ->
                itemType = LOADING_ITEM_VIEW
            ListItem.LIST_ITEM ->
                itemType = POKEMON_ITEM_VIEW
        }
        return itemType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bindingLoadingItem = ItemLoadMoreBinding.inflate(inflater, parent, false)
        val bindingPokemonItem = ItemPokemonBinding.inflate(inflater, parent, false)

        var viewHolder: RecyclerView.ViewHolder = ViewHolderPokemonItem(bindingPokemonItem)

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
            POKEMON_ITEM_VIEW -> {
                (holder as ViewHolderPokemonItem).bind(context, listItems[position] as PokemonItem, pokemonItemClick)
            }
        }
    }

    class ViewHolderPokemonItem(private val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, pokemon: PokemonItem, itemClick: (pokemonId: Int) -> Unit) {
            binding.tvPokemonNumber.text = pokemon.number
            binding.tvPokemonName.text = pokemon.name

            binding.ivPokemon.setImageResource(getImageResource(context, pokemon.image))
            binding.ivPokemon.setImageResource(getImageResource(context, pokemon.image))

            binding.layoutItemPokemon.setOnClickListener { itemClick(pokemon.number.toInt()) }
        }

        private fun getImageResource(context: Context, imageResourceName: String): Int {
            return context
                .getResources()
                .getIdentifier(
                    imageResourceName,
                    "drawable",
                    context.packageName
                )
        }
    }

    class ViewHolderLoadMoreItem(binding: ItemLoadMoreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadMoreAction: () -> Unit) {
            loadMoreAction()
        }
    }
}
