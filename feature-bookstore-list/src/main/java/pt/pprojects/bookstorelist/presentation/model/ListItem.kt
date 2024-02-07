package pt.pprojects.bookstorelist.presentation.model

open class ListItem(open val itemType: String) {
    companion object {
        const val LOADING_ITEM = "LOADING_ITEM"
        const val LIST_ITEM = "LIST_ITEM"
    }
}
