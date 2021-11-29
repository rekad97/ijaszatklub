package aut.arch.ui.fragments.teams.teams_recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import aut.arch.databinding.ItemTeamPreviewBinding
import aut.arch.ui.interfaces.Navigator

class TeamsPreviewAdapter( val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<TeamPreview> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemTeamPreviewBinding.inflate(layoutInflater, parent, false)
        return TeamsPreviewViewHolder(binding, context)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(item: TeamPreview) {
        items.add(item)
    }

    fun getItems(): ArrayList<TeamPreview> {
        return items
    }

    fun deleteItem(model: TeamPreview) {
        items.remove(model)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        (holder as TeamsPreviewViewHolder).bind(item)
    }

    fun addAll(items: List<TeamPreview>) {
        this.items.addAll(items)
    }

    fun clear() {
        items.clear()
    }

}
