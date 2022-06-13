package com.slatinin.simplepincode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PinCodeViewModelFactory(private val isCreate: Boolean, private val maxCodeLength: Int) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (maxCodeLength > 6 || maxCodeLength < 3) {
            throw Exception("Max length of pin code must be between 3 and 6")
        }
        return com.slatinin.simplepincode.PinCodeViewModel(
            isCreate = isCreate,
            maxCodeLength = maxCodeLength
        ) as T
    }

}