package com.slatinin.simplepincode

interface PinCodeResult {

    fun isNewPinCode(): Boolean

    fun pinCode(): String

    fun enterPinAgain(): Boolean

    fun pinsNotEqual(): Boolean

    fun continueEnter(): Boolean
}