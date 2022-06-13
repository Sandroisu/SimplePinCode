package com.slatinin.simplepincode

class NewPinCodeResult(
    val pinCode: String,
    val tempPinCode: String,
    val previous: Int,
    private val maxCodeLength: Int
) :
    PinCodeResult {

    override fun isNewPinCode(): Boolean {
        return true
    }

    override fun pinCode(): String {
        return pinCode
    }

    override fun enterPinAgain(): Boolean {
        return pinCode.isEmpty() && tempPinCode.isNotEmpty()
    }

    override fun pinsNotEqual(): Boolean {
        return pinCode.length == maxCodeLength && tempPinCode.length == maxCodeLength && tempPinCode != pinCode
    }

    override fun continueEnter(): Boolean {
        return pinCode.length < maxCodeLength
    }
}