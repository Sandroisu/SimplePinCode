package com.slatinin.simplepincode

/**
 * Callbacks for pin code layout
 */
interface PinCodeDigitsCallback {
    fun onDigitEntered(digit: String)

    fun onBackspacePressed()

    fun onPinDropConfirmed()
}