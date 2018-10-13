package edu.poms.tsukanov.quiz.recyclerview

import java.util.ArrayList
import java.util.HashMap


object DashboardContent {


    val ITEMS: MutableList<DashboardItem> = ArrayList()

    val ITEM_MAP: MutableMap<String, DashboardItem> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createDummyItem(i))
        }
    }

    private fun addItem(item: DashboardItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun createDummyItem(position: Int): DashboardItem {
        return DashboardItem(position.toString(), "Item " + position, makeDetails(position))
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0..position - 1) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }


    data class DashboardItem(val id: String, val content: String, val details: String) {
        override fun toString(): String = content
    }
}
