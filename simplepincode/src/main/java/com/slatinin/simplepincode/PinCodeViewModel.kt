package com.slatinin.simplepincode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PinCodeViewModel(val isCreate: Boolean, val maxCodeLength: Int) : ViewModel() {
    var pinCode: String
    var tempPin: String
    init {
        pinCode = ""
        tempPin = ""
    }


    private var pinCodeLiveData: MutableLiveData<PinCodeResult> = MutableLiveData<PinCodeResult>()
    private var clearDigitLiveData: MutableLiveData<PinCodeResult> =
        MutableLiveData<PinCodeResult>()

    fun refreshPinCode(digit: String): MutableLiveData<PinCodeResult> {

        if (isCreate) {
            pinCode += digit
            if (pinCode.length > maxCodeLength) {
                pinCode = pinCode.substring(0, maxCodeLength)
            }
            if (pinCode.length != maxCodeLength) {
                pinCodeLiveData.value = NewPinCodeResult(
                    pinCode,
                    tempPin,
                    pinCode.length - 1,
                    maxCodeLength
                )
                return pinCodeLiveData
            }
            if (tempPin.isEmpty()) {
                tempPin = pinCode
                pinCode = ""
                pinCodeLiveData.value = (
                    NewPinCodeResult(
                        pinCode,
                        tempPin,
                        maxCodeLength,
                        maxCodeLength
                    )
                )
            } else {
                if (tempPin == pinCode) {
                    pinCodeLiveData.value = (
                        NewPinCodeResult(
                            pinCode,
                            tempPin,
                            maxCodeLength,
                            maxCodeLength
                        )
                    )
                } else {
                    pinCodeLiveData.value = NewPinCodeResult(
                        pinCode,
                        tempPin,
                        maxCodeLength,
                        maxCodeLength
                    )
                    tempPin = ""
                    pinCode = ""
                }
            }
        } else {
            if (pinCode.length < maxCodeLength) {
                pinCode += digit
            }
            pinCodeLiveData.value = ExistPinCodeResult(
                pinCode,
                maxCodeLength
            )
            if (pinCode.length == maxCodeLength) {
                pinCode = ""
            }
        }
        return pinCodeLiveData
    }

    fun clearOneDigit(): MutableLiveData<PinCodeResult> {
        pinCode = if (pinCode.length > 1) {
            pinCode.substring(0, pinCode.length - 1)
        } else {
            ""
        }
        if (isCreate) {
            clearDigitLiveData.value = (
                NewPinCodeResult(
                    pinCode,
                    tempPin,
                    pinCode.length + 1,
                    maxCodeLength
                )
            )
        } else {
            clearDigitLiveData.value = (
                ExistPinCodeResult(
                    pinCode,
                    maxCodeLength
                )
            )
        }
        return clearDigitLiveData
    }
}