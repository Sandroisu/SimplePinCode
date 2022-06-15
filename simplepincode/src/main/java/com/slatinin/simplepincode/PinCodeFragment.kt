package com.slatinin.simplepincode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

/**
 * A ready-to-use pin code implementation. Subclass this, override abstract methods and you are ready to go.
 */
abstract class PinCodeFragment : Fragment() {

    protected var pinCodeLayout: PinCodeLayout? = null
    protected var progressBar: ProgressBar? = null
    protected var aboveCodeText: AppCompatTextView? = null

    private var viewModel: PinCodeViewModel? = null
    private var refreshPinObserver: Observer<PinCodeResult>? = null
    private var backspacePinObserver: Observer<PinCodeResult>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            PinCodeViewModelFactory(isCreateNewCode(), getMaxPinLength())
        ).get(PinCodeViewModel::class.java)

        refreshPinObserver = Observer<PinCodeResult> { result: PinCodeResult ->

            if (result.isNewPinCode()) {
                handleNewPinCodeClick(result)
            } else {
                pinCodeLayout?.fillDot(result.pinCode().length)
                if (result.pinCode() == getSavedPinCode()) {
                    pinCodeLayout?.blockDigits()
                    onCodeMatch(result as ExistPinCodeResult)
                } else {
                    if (!result.continueEnter()) {
                        pinCodeLayout?.wrongClear()
                    }
                }
            }
        }
        backspacePinObserver = Observer<PinCodeResult> { pinCodeResult: PinCodeResult ->
            pinCodeLayout?.clearOneDot(pinCodeResult.pinCode().length)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_pincode, container, false)
        pinCodeLayout = view.findViewById(R.id.fragment_code_digits)
        pinCodeLayout?.setMaxDots(getMaxPinLength())
        progressBar = view.findViewById(R.id.fragment_code_progress)
        aboveCodeText = view.findViewById(R.id.fragment_code_text)
        viewModel?.let { viewM ->
            if (viewM.isCreate) {
                pinCodeLayout?.hideReset()
            }
            pinCodeLayout?.setDigitCallback(object :
                PinCodeDigitsCallback {
                override fun onDigitEntered(digit: String) {
                    refreshPinObserver?.let {
                        viewM.refreshPinCode(digit).observe(viewLifecycleOwner, it)
                    }
                }

                override fun onBackspacePressed() {
                    backspacePinObserver?.let {
                        viewM.clearOneDigit().observe(viewLifecycleOwner, it)
                    }
                }

                override fun onPinDropConfirmed() {
                    codeResetConfirmed()
                }
            })
            pinCodeLayout?.fillDotsUntil(viewM.pinCode.length)
        }

        return view.rootView
    }

    private fun handleNewPinCodeClick(result: PinCodeResult) {
        if (result.enterPinAgain()) {
            pinCodeLayout?.clearDots()
        } else {
            pinCodeLayout?.fillDot(result.pinCode().length)
            if (result.continueEnter()) {
                return
            }
            if (result.pinsNotEqual()) {
                pinCodeLayout?.wrongClear()
                return
            }
            onNewCodeMatch(result as NewPinCodeResult)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        pinCodeLayout = null
        progressBar = null
        aboveCodeText = null
    }

    abstract fun isCreateNewCode(): Boolean

    abstract fun getSavedPinCode(): String

    abstract fun onCodeMatch(existPinCodeResult: ExistPinCodeResult)

    abstract fun onNewCodeMatch(newPinCodeResult: NewPinCodeResult)

    abstract fun codeResetConfirmed()

    abstract fun getMaxPinLength(): Int
}