package aut.arch.ui.fragments.training.setup_picker_recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import aut.arch.databinding.ItemSetupPreviewBinding
import aut.arch.ui.interfaces.SetupPicker

class SetupPreviewAdapter(private val setupPicker: SetupPicker) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<SetupPreview> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemSetupPreviewBinding.inflate(layoutInflater, parent, false)
        return SetupPreviewViewHolder(binding, setupPicker)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(item: SetupPreview) {
        items.add(item)
    }

    fun getItems(): ArrayList<SetupPreview> {
        return items
    }

    fun deleteItem(model: SetupPreview) {
        items.remove(model)
        notifyDataSetChanged()
    }

    fun addPackModel(model: SetupPreview) {
        items.add(model)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        (holder as SetupPreviewViewHolder).bind(item)
    }

}
