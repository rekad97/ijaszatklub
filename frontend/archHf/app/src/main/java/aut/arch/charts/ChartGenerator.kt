package aut.arch.charts

import android.graphics.Color
import aut.arch.local_data.models.Chart
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

object ChartGenerator {

    fun getPieChart(chart: Chart): PieChart {
        val pieChart = PieChart(chart.context)

        pieChart.setHoleColor(Color.TRANSPARENT)
        pieChart.setTransparentCircleColor(Color.TRANSPARENT)
        pieChart.description.text = ""

        val xEntries = ArrayList<PieEntry>()
        var labelId = 0
        chart.xs[0].forEach {
            xEntries.add(PieEntry(it.toFloat(), chart.labels[labelId++]))
        }
        val dataset = PieDataSet(xEntries, "pie")
        dataset.color = chart.colors[0].colorCode
        dataset.label = chart.dataLabels[0]
        dataset.sliceSpace = 8f
        dataset.valueLineWidth = 15f
        pieChart.data = PieData(dataset)

        return pieChart
    }

    fun getRadarChart(chart: Chart): RadarChart {
        val radarChart = RadarChart(chart.context)
        var colorIndex = 0

        radarChart.description.text = ""
        radarChart.webLineWidth = 3F
        radarChart.webAlpha = 255
        radarChart.webLineWidthInner = 2F
        val data = RadarData()

        for (i in chart.xs.indices) {
            val xEntries = ArrayList<RadarEntry>()
            chart.xs[i].forEach {
                xEntries.add(RadarEntry(it.toFloat()))
            }
            val dataset = RadarDataSet(xEntries, "radar")
            dataset.color = chart.colors[colorIndex++].colorCode
            dataset.lineWidth = 3F
            dataset.label = chart.dataLabels[i]
            data.addDataSet(dataset)
        }

        val formatter = IndexAxisValueFormatter(chart.labels)

        radarChart.xAxis.valueFormatter = formatter

        radarChart.data = data

        return radarChart
    }

    fun getBarChart(chart: Chart): BarChart {
        val barChart = BarChart(chart.context)
        var colorIndex = 0
        var yIndexWhenNull = 0
        barChart.description.text = ""

        val data = BarData()

        for (i in chart.xs.indices) {
            val xEntries = ArrayList<BarEntry>()
            var yIndex = 0
            chart.xs[i].forEach { localX ->
                if (chart.ys != null) {
                    xEntries.add(BarEntry(localX.toFloat(), chart.ys[i][yIndex++].toFloat()))
                } else {
                    xEntries.add(BarEntry(yIndexWhenNull++.toFloat(), localX.toFloat()))
                }
            }
            val dataset = BarDataSet(xEntries, "radar")
            dataset.color = chart.colors[colorIndex++].colorCode
            dataset.label = chart.dataLabels[i]
            data.addDataSet(dataset)
        }

        barChart.data = data
        barChart.setFitBars(true)

        return barChart
    }
}
