package com.slatinin.simplepincode

class ExistPinCodeResult(
    val pinCode: String,
    private val maxCodeLength: Int
) : PinCodeResult {

    override fun isNewPinCode(): Boolean {
        return false
    }

    override fun pinCode(): String {
        return pinCode
    }

    override fun enterPinAgain(): Boolean {
        return false
    }

    override fun pinsNotEqual(): Boolean {
        return false
    }

    override fun continueEnter(): Boolean {
        return pinCode.length < maxCodeLength
    }

}