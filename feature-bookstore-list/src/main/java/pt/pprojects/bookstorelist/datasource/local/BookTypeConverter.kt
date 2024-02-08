package pt.pprojects.bookstorelist.datasource.local

import androidx.room.TypeConverter

object BookTypeConverter {
    @TypeConverter
    fun fromString(value: String?): List<String> {
        val list: MutableList<String> = ArrayList()
        // Convert the comma-separated string to a list of strings
        if (value != null) {
            val items = value.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            for (item in items) {
                list.add(item)
            }
        }
        return list
    }

    @TypeConverter
    fun toString(list: List<String?>?): String {
        // Convert the list of strings to a comma-separated string
        val value = StringBuilder()
        if (list != null) {
            for (i in list.indices) {
                value.append(list[i])
                if (i < list.size - 1) {
                    value.append(",")
                }
            }
        }
        return value.toString()
    }
}