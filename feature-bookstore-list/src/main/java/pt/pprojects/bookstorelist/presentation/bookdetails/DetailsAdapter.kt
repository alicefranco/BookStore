package pt.pprojects.bookstorelist.presentation.bookdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.pprojects.bookstorelist.databinding.ItemDetailBinding
import pt.pprojects.bookstorelist.presentation.model.DetailItem

class DetailsAdapter : RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {
    private var details: List<DetailItem> = listOf()

    fun addDetails(items: List<DetailItem>) {
        details = items
    }

    override fun getItemCount(): Int {
        return details.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDetailBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = details[position]
        holder.bind(item.description)
    }

    class ViewHolder(private val binding: ItemDetailBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(description: String) {
            binding.tvDetail.text = description
        }
    }
}
