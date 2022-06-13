package com.slatinin.simplepincode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PinCodeViewModel(val isCreate: Boolean, val maxCodeLength: Int) : ViewModel() {
    var pinCode: String = ""
    var tempPin: String = ""

    private var pinCodeLiveData: MutableLiveData<com.slatinin.simplepincode.PinCodeResult> = MutableLiveData<com.slatinin.simplepincode.PinCodeResult>()
    private var clearDigitLiveData: MutableLiveData<com.slatinin.simplepincode.PinCodeResult> =
        MutableLiveData<com.slatinin.simplepincode.PinCodeResult>()

    fun refreshPinCode(digit: String): MutableLiveData<com.slatinin.simplepincode.PinCodeResult> {

        if (isCreate) {
            pinCode += digit
            if (pinCode.length > maxCodeLength) {
                pinCode = pinCode.substring(0, maxCodeLength)
            }
            if (pinCode.length != maxCodeLength) {
                pinCodeLiveData.setValue(
                    com.slatinin.simplepincode.NewPinCodeResult(
                        pinCode,
                        tempPin,
                        pinCode.length - 1,
                        maxCodeLength
                    )
                )
                return pinCodeLiveData
            }
            if (tempPin.isEmpty()) {
                tempPin = pinCode
                pinCode = ""
                pinCodeLiveData.setValue(
                    com.slatinin.simplepincode.NewPinCodeResult(
                        pinCode,
                        tempPin,
                        maxCodeLength,
                        maxCodeLength
                    )
                )
            } else {
                if (tempPin == pinCode) {
                    pinCodeLiveData.setValue(
                        com.slatinin.simplepincode.NewPinCodeResult(
                            pinCode,
                            tempPin,
                            maxCodeLength,
                            maxCodeLength
                        )
                    )
                } else {
                    pinCodeLiveData.setValue(
                        com.slatinin.simplepincode.NewPinCodeResult(
                            pinCode,
                            tempPin,
                            maxCodeLength,
                            maxCodeLength
                        )
                    )
                    tempPin = ""
                    pinCode = ""
                }
            }
        } else {
            if (pinCode.length < maxCodeLength) {
                pinCode += digit
            }
            pinCodeLiveData.setValue(
                com.slatinin.simplepincode.ExistPinCodeResult(
                    pinCode,
                    maxCodeLength
                )
            )
            if (pinCode.length == maxCodeLength) {
                pinCode = ""
            }
        }
        return pinCodeLiveData
    }

    fun clearOneDigit(): MutableLiveData<com.slatinin.simplepincode.PinCodeResult> {
        pinCode = if (pinCode.length > 1) {
            pinCode.substring(0, pinCode.length - 1)
        } else {
            ""
        }
        if (isCreate) {
            clearDigitLiveData.setValue(
                com.slatinin.simplepincode.NewPinCodeResult(
                    pinCode,
                    tempPin,
                    pinCode.length + 1,
                    maxCodeLength
                )
            )
        } else {
            clearDigitLiveData.setValue(
                com.slatinin.simplepincode.ExistPinCodeResult(
                    pinCode,
                    maxCodeLength
                )
            )
        }
        return clearDigitLiveData
    }
}