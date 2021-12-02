package aut.arch.ui.fragments.landing.statistics_preview_recyclerview.viewholders

import android.content.Context
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import aut.arch.databinding.ItemStatisticsPreviewBinding
import aut.arch.ui.fragments.landing.statistics_preview_recyclerview.models.PieChartPreview

class StatisticsPreviewPieChartViewHolder(
    val binding: ItemStatisticsPreviewBinding,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(item: PieChartPreview) {
        binding.statisticsPreviewTitle.text = item.title

        binding.statisticsPreviewChart.layoutParams.apply {
            item.chart.layoutParams = ViewGroup.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
            )
        }
        binding.statisticsPreviewChart.addView(item.chart)

        binding.executePendingBindings()
    }

}
