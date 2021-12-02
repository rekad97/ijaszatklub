package aut.arch.ui.fragments.landing.statistics_preview_recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import aut.arch.databinding.ItemStatisticsPreviewBinding
import aut.arch.ui.fragments.landing.statistics_preview_recyclerview.models.BarChartPreview
import aut.arch.ui.fragments.landing.statistics_preview_recyclerview.models.ChartPreview
import aut.arch.ui.fragments.landing.statistics_preview_recyclerview.models.PieChartPreview
import aut.arch.ui.fragments.landing.statistics_preview_recyclerview.models.RadarChartPreview
import aut.arch.ui.fragments.landing.statistics_preview_recyclerview.viewholders.StatisticsPreviewBarChartViewHolder
import aut.arch.ui.fragments.landing.statistics_preview_recyclerview.viewholders.StatisticsPreviewPieChartViewHolder
import aut.arch.ui.fragments.landing.statistics_preview_recyclerview.viewholders.StatisticsPreviewRadarChartViewHolder

class StatisticsPreviewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<ChartPreview> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemStatisticsPreviewBinding.inflate(layoutInflater, parent, false)
        return when (viewType) {

            ChartPreview.TYPE_PIE -> {
                StatisticsPreviewPieChartViewHolder(binding, parent.context)
            }
            ChartPreview.TYPE_BAR -> {
                StatisticsPreviewBarChartViewHolder(binding, parent.context)
            }
            ChartPreview.TYPE_RADAR -> {
                StatisticsPreviewRadarChartViewHolder(binding, parent.context)
            }
            else -> {
                StatisticsPreviewPieChartViewHolder(binding, parent.context)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(item: ChartPreview) {
        items.add(item)
    }

    fun getItems(): ArrayList<ChartPreview> {
        return items
    }

    fun deleteItem(model: ChartPreview) {
        items.remove(model)
        notifyDataSetChanged()
    }

    fun addPackModel(model: ChartPreview) {
        items.add(model)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        if (holder is StatisticsPreviewPieChartViewHolder && item is PieChartPreview)
            holder.bind(item)

        if (holder is StatisticsPreviewRadarChartViewHolder && item is RadarChartPreview)
            holder.bind(item)

        if (holder is StatisticsPreviewBarChartViewHolder && item is BarChartPreview)
            holder.bind(item)
    }

}
