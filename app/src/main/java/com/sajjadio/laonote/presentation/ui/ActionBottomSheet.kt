package com.sajjadio.laonote.presentation.ui

import com.sajjadio.laonote.presentation.ui.fragment.BottomSheetFragment
import com.sajjadio.laonote.presentation.ui.fragment.PickerColorFragment

class ActionBottomSheet {
    companion object {
        fun newInstanceOfNoteBottomSheet() = BottomSheetFragment()
        fun newInstanceOfPickerColor() = PickerColorFragment()
    }
}