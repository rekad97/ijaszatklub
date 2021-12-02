package aut.arch.ui.fragments.setups.setups_recyclerview

import androidx.recyclerview.widget.RecyclerView
import aut.arch.databinding.ItemSetupPreviewBinding
import aut.arch.local_data.models.enums.BowType
import aut.arch.ui.interfaces.Navigator

class SetupsPreviewViewHolder(
    val binding: ItemSetupPreviewBinding,
    private val navigator: Navigator
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var item: SetupsPreview


    fun bind(item: SetupsPreview) {
        this.item = item
        when (item.type) {
            BowType.TRADITIONAL ->
                binding.statisticsPreviewIcon.setBackgroundResource(BowType.TRADITIONAL.src)
            BowType.RECURVE ->
                binding.statisticsPreviewIcon.setBackgroundResource(BowType.RECURVE.src)
            BowType.COMPOUND ->
                binding.statisticsPreviewIcon.setBackgroundResource(BowType.COMPOUND.src)
        }

        binding.statisticsPreviewName.text = item.name
        binding.executePendingBindings()

        initListeners()
    }

    private fun initListeners() {
        binding.statisticsPreviewIcon.setOnClickListener {
            navigator.navigateTo(item.setupId)
        }
    }

}
