package aut.arch.ui.fragments.training.setup_picker_recyclerview

import androidx.recyclerview.widget.RecyclerView
import aut.arch.databinding.ItemSetupPreviewBinding
import aut.arch.local_data.models.enums.BowType
import aut.arch.ui.interfaces.SetupPicker

class SetupPreviewViewHolder(
    val binding: ItemSetupPreviewBinding,
    val setupPicker: SetupPicker
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var item: SetupPreview


    fun bind(item: SetupPreview) {
        this.item = item
        binding.statisticsPreviewName.text = item.name
        when (item.type) {
            BowType.TRADITIONAL -> {
                binding.statisticsPreviewIcon.setBackgroundResource(BowType.TRADITIONAL.src)
            }
            BowType.RECURVE -> {
                binding.statisticsPreviewIcon.setBackgroundResource(BowType.RECURVE.src)
            }
            BowType.COMPOUND -> {
                binding.statisticsPreviewIcon.setBackgroundResource(BowType.COMPOUND.src)
            }
        }

        binding.executePendingBindings()

        initListeners()
    }

    private fun initListeners() {
        binding.statisticsPreviewIcon.setOnClickListener {
            setupPicker.pickSetup(binding, item)
        }
    }

}
