package com.slatinin.pincode

import com.slatinin.simplepincode.ExistPinCodeResult
import com.slatinin.simplepincode.NewPinCodeResult
import com.slatinin.simplepincode.PinCodeFragment

class CodeFragment : PinCodeFragment(){
    override fun isCreateNewCode(): Boolean {
        return false
    }

    override fun getSavedPinCode(): String {
        return "222222"
    }

    override fun onCodeMatch(existPinCodeResult: ExistPinCodeResult) {
    }

    override fun onNewCodeMatch(newPinCodeResult: NewPinCodeResult) {
    }

    override fun codeResetConfirmed() {
    }

    override fun getMaxPinLength(): Int {
        return 6
    }
}