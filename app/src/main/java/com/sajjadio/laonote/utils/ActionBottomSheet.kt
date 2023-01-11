package com.sajjadio.laonote.utils

import com.sajjadio.laonote.presentation.ui.fragments.note.BottomSheetFragment
import com.sajjadio.laonote.presentation.ui.fragments.note.PickerColorFragment

class ActionBottomSheet {
    companion object {
        fun newInstanceOfNoteBottomSheet() = BottomSheetFragment()
        fun newInstanceOfPickerColor() = PickerColorFragment()
    }
}