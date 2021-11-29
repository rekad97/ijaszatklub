package aut.arch.ui.fragments.setups.setups_recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import aut.arch.databinding.ItemSetupPreviewBinding
import aut.arch.ui.interfaces.Navigator

class SetupsPreviewAdapter(val navigator: Navigator) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<SetupsPreview> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemSetupPreviewBinding.inflate(layoutInflater, parent, false)
        return SetupsPreviewViewHolder(binding, navigator)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(item: SetupsPreview) {
        items.add(item)
    }

    fun getItems(): ArrayList<SetupsPreview> {
        return items
    }

    fun deleteItem(model: SetupsPreview) {
        items.remove(model)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        (holder as SetupsPreviewViewHolder).bind(item)
    }

    fun clear() {
        items.clear()
    }

}
