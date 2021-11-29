package aut.arch.ui.fragments.landing.statistics_preview_recyclerview.models

abstract class ChartPreview(
    val type: Int
) {
    abstract val title: String

    companion object {
        const val TYPE_PIE = 0
        const val TYPE_BAR = 1
        const val TYPE_RADAR = 2
    }
}