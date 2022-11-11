package com.sajjadio.laonote.utils

import com.sajjadio.laonote.presentation.ui.note.BottomSheetFragment
import com.sajjadio.laonote.presentation.ui.note.PickerColorFragment

class ActionBottomSheet {
    companion object {
        fun newInstanceOfNoteBottomSheet() = BottomSheetFragment()
        fun newInstanceOfPickerColor() = PickerColorFragment()
    }
}