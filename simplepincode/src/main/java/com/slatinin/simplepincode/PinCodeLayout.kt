package com.slatinin.simplepincode

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.res.ResourcesCompat


@Suppress("MemberVisibilityCanBePrivate")
class PinCodeLayout : LinearLayoutCompat {
    val buttonOne: AppCompatButton
    var buttonTwo: AppCompatButton
    var buttonThree: AppCompatButton
    var buttonFour: AppCompatButton
    var buttonFive: AppCompatButton
    var buttonSix: AppCompatButton
    var buttonSeven: AppCompatButton
    var buttonEight: AppCompatButton
    var buttonNine: AppCompatButton
    var buttonReset: AppCompatButton
    var buttonZero: AppCompatButton
    var buttonBackspace: AppCompatButton
    var dotLayout: LinearLayoutCompat
    var firstDot: AppCompatImageView
    var secondDot: AppCompatImageView
    var thirdDot: AppCompatImageView
    var fourthDot: AppCompatImageView
    var fifthDot: AppCompatImageView
    var sixthDot: AppCompatImageView
    val parentLayout: ConstraintLayout

    val buttonsList: ArrayList<AppCompatButton> = ArrayList()
    val dotsList: ArrayList<AppCompatImageView> = ArrayList()

    private var maxDotCount = 4
    var currentDotsFilled = 0
    var emptyDotDrawable: Drawable? = null
    var filledDotDrawable: Drawable? = null

    var callback: PinCodeDigitsCallback? = null
    var backSpaceDrawable: Drawable? = null

    constructor(context: Context) : super(context) {
        View.inflate(context, R.layout.pin_code_digits, this)

        parentLayout = findViewById(R.id.pin_code_parent_layout)

        firstDot = findViewById(R.id.dot_line_first_dot)
        secondDot = findViewById(R.id.dot_line_second_dot)
        thirdDot = findViewById(R.id.dot_line_third_dot)
        fourthDot = findViewById(R.id.dot_line_fourth_dot)
        fifthDot = findViewById(R.id.dot_line_fifth_dot)
        sixthDot = findViewById(R.id.dot_line_sixth_dot)
        dotLayout = findViewById(R.id.dots_layout)

        buttonOne = findViewById(R.id.pin_digits_btn_one)
        buttonTwo = findViewById(R.id.pin_digits_btn_two)
        buttonThree = findViewById(R.id.pin_digits_btn_three)
        buttonFour = findViewById(R.id.pin_digits_btn_four)
        buttonFive = findViewById(R.id.pin_digits_btn_five)
        buttonSix = findViewById(R.id.pin_digits_btn_six)
        buttonSeven = findViewById(R.id.pin_digits_btn_seven)
        buttonEight = findViewById(R.id.pin_digits_btn_eight)
        buttonNine = findViewById(R.id.pin_digits_btn_nine)
        buttonReset = findViewById(R.id.pin_digits_btn_reset)
        buttonZero = findViewById(R.id.pin_digits_btn_zero)
        buttonBackspace = findViewById(R.id.pin_digits_btn_backspace)

        dotsList.add(firstDot)
        dotsList.add(secondDot)
        dotsList.add(thirdDot)
        dotsList.add(fourthDot)
        dotsList.add(fifthDot)
        dotsList.add(sixthDot)

        buttonsList.add(buttonOne)
        buttonsList.add(buttonTwo)
        buttonsList.add(buttonThree)
        buttonsList.add(buttonFour)
        buttonsList.add(buttonFive)
        buttonsList.add(buttonSix)
        buttonsList.add(buttonSeven)
        buttonsList.add(buttonEight)
        buttonsList.add(buttonNine)
        buttonsList.add(buttonReset)
        buttonsList.add(buttonZero)
        buttonsList.add(buttonBackspace)

        buttonOne.setOnClickListener {
            callback?.onDigitEntered(context.getString(R.string.one_digit))
        }
        buttonTwo.setOnClickListener {
            callback?.onDigitEntered(context.getString(R.string.two_digit))
        }
        buttonThree.setOnClickListener {
            callback?.onDigitEntered(context.getString(R.string.three_digit))
        }
        buttonFour.setOnClickListener {
            callback?.onDigitEntered(context.getString(R.string.four_digit))
        }
        buttonFive.setOnClickListener {
            callback?.onDigitEntered(context.getString(R.string.five_digit))
        }
        buttonSix.setOnClickListener {
            callback?.onDigitEntered(context.getString(R.string.six_digit))
        }
        buttonSeven.setOnClickListener {
            callback?.onDigitEntered(context.getString(R.string.seven_digit))
        }
        buttonEight.setOnClickListener {
            callback?.onDigitEntered(context.getString(R.string.eight_digit))
        }
        buttonNine.setOnClickListener {
            callback?.onDigitEntered(context.getString(R.string.nine_digit))
        }
        buttonReset.setOnClickListener {

            val adb = AlertDialog.Builder(context)
            adb.setPositiveButton(
                context.getString(R.string.clear)
            ) { _: DialogInterface?, _: Int -> callback?.onPinDropConfirmed() }
                .setNegativeButton(
                    context.getString(R.string.cancel)
                ) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
            val alert = adb.create()
            alert.setTitle(context.getString(R.string.attention))
            alert.setMessage(context.getString(R.string.reset_pin_code_warning))
            alert.setCancelable(true)
            alert.show()

        }
        buttonZero.setOnClickListener {
            callback?.onDigitEntered(context.getString(R.string.zero_digit))
        }

        buttonBackspace.setOnClickListener {
            callback?.onBackspacePressed()
        }
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        View.inflate(context, R.layout.pin_code_digits, this)

        parentLayout = findViewById(R.id.pin_code_parent_layout)

        firstDot = findViewById(R.id.dot_line_first_dot)
        secondDot = findViewById(R.id.dot_line_second_dot)
        thirdDot = findViewById(R.id.dot_line_third_dot)
        fourthDot = findViewById(R.id.dot_line_fourth_dot)
        fifthDot = findViewById(R.id.dot_line_fifth_dot)
        sixthDot = findViewById(R.id.dot_line_sixth_dot)

        dotLayout = findViewById(R.id.dots_layout)
        buttonOne = findViewById(R.id.pin_digits_btn_one)
        buttonTwo = findViewById(R.id.pin_digits_btn_two)
        buttonThree = findViewById(R.id.pin_digits_btn_three)
        buttonFour = findViewById(R.id.pin_digits_btn_four)
        buttonFive = findViewById(R.id.pin_digits_btn_five)
        buttonSix = findViewById(R.id.pin_digits_btn_six)
        buttonSeven = findViewById(R.id.pin_digits_btn_seven)
        buttonEight = findViewById(R.id.pin_digits_btn_eight)
        buttonNine = findViewById(R.id.pin_digits_btn_nine)
        buttonReset = findViewById(R.id.pin_digits_btn_reset)
        buttonZero = findViewById(R.id.pin_digits_btn_zero)
        buttonBackspace = findViewById(R.id.pin_digits_btn_backspace)

        dotsList.add(firstDot)
        dotsList.add(secondDot)
        dotsList.add(thirdDot)
        dotsList.add(fourthDot)
        dotsList.add(fifthDot)
        dotsList.add(sixthDot)

        buttonsList.add(buttonOne)
        buttonsList.add(buttonTwo)
        buttonsList.add(buttonThree)
        buttonsList.add(buttonFour)
        buttonsList.add(buttonFive)
        buttonsList.add(buttonSix)
        buttonsList.add(buttonSeven)
        buttonsList.add(buttonEight)
        buttonsList.add(buttonNine)
        buttonsList.add(buttonReset)
        buttonsList.add(buttonZero)
        buttonsList.add(buttonBackspace)

        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.PinCodeLayout, 0, 0
        )
        emptyDotDrawable =
            ResourcesCompat.getDrawable(resources, R.drawable.vector_pin_empty_dot, null)
        if (a.hasValue(R.styleable.PinCodeLayout_dotEmptyIcon)) {
            val customEmptyDot = a.getDrawable(R.styleable.PinCodeLayout_dotEmptyIcon)
            customEmptyDot?.let {
                emptyDotDrawable = it
                setEmptyDotIcon(it)
            }
        }
        filledDotDrawable =
            ResourcesCompat.getDrawable(resources, R.drawable.vector_pin_filled_dot, null)
        if (a.hasValue(R.styleable.PinCodeLayout_dotFilledIcon)) {
            val customFilledDot = a.getDrawable(R.styleable.PinCodeLayout_dotFilledIcon)
            customFilledDot?.let {
                filledDotDrawable = it
            }
        }


        val defaultButtonMargin = resources.getDimensionPixelSize(R.dimen.buttons_margin)
        val margin =
            a.getDimensionPixelSize(R.styleable.PinCodeLayout_buttonsMargin, defaultButtonMargin)
        val defaultButtonSize = resources.getDimensionPixelSize(R.dimen.buttons_size)
        val buttonsSize =
            a.getDimensionPixelSize(R.styleable.PinCodeLayout_buttonsSize, defaultButtonSize)
        val defaultButtonTextSize = resources.getDimensionPixelSize(R.dimen.buttons_text_size)
        val buttonTextSize =
            a.getDimensionPixelSize(
                R.styleable.PinCodeLayout_buttonsTextSize,
                defaultButtonTextSize
            )
        if (a.hasValue(R.styleable.PinCodeLayout_resetText)) {
            val text = a.getString(R.styleable.PinCodeLayout_resetText)
            text?.let {
                setResetText(it)
            }
        }
        backSpaceDrawable =
            ResourcesCompat.getDrawable(resources, R.drawable.vector_backspace, null)
        if (a.hasValue(R.styleable.PinCodeLayout_backspaceIcon)) {
            val customBackspaceDot = a.getDrawable(R.styleable.PinCodeLayout_backspaceIcon)
            customBackspaceDot?.let {
                backSpaceDrawable = it
                setBackspaceIcon(it, buttonsSize)
            }
        }
        if (a.hasValue(R.styleable.PinCodeLayout_buttonsBackground)) {
            val buttonsColor =
                a.getColor(R.styleable.PinCodeLayout_buttonsBackground, Color.TRANSPARENT)
            setButtonsBackground(buttonsColor, Color.GRAY)
        }

        if (a.hasValue(R.styleable.PinCodeLayout_buttonsTextColor)) {
            val buttonsTextColor =
                a.getColor(R.styleable.PinCodeLayout_buttonsTextColor, Color.BLACK)
            setButtonsTextColor(buttonsTextColor)
        }

        if (a.hasValue(R.styleable.PinCodeLayout_dotsSize)) {
            val defaultDotsSize = resources.getDimensionPixelSize(R.dimen.dots_size)
            val dotSize =
                a.getDimensionPixelSize(R.styleable.PinCodeLayout_dotsSize, defaultDotsSize)
            setDotsSize(dotSize)
        }

        if (a.hasValue(R.styleable.PinCodeLayout_dotsColor)) {
            val dotsColor =
                a.getColor(R.styleable.PinCodeLayout_dotsColor, Color.BLACK)
            setDotsColor(dotsColor)
        }

        if (a.hasValue(R.styleable.PinCodeLayout_rippleColor)) {
            val rippleColor =
                a.getColor(R.styleable.PinCodeLayout_rippleColor, Color.RED)
            setRippleColor(rippleColor)
        }

        a.recycle()

        setButtonsMargin(margin)
        setButtonsSize(buttonsSize)
        setButtonsTextSize(buttonTextSize)

        buttonOne.setOnClickListener {
            callback?.onDigitEntered(context.getString(R.string.one_digit))
        }
        buttonTwo.setOnClickListener {
            callback?.onDigitEntered(context.getString(R.string.two_digit))
        }
        buttonThree.setOnClickListener {
            callback?.onDigitEntered(context.getString(R.string.three_digit))
        }
        buttonFour.setOnClickListener {
            callback?.onDigitEntered(context.getString(R.string.four_digit))
        }
        buttonFive.setOnClickListener {
            callback?.onDigitEntered(context.getString(R.string.five_digit))
        }
        buttonSix.setOnClickListener {
            callback?.onDigitEntered(context.getString(R.string.six_digit))
        }
        buttonSeven.setOnClickListener {
            callback?.onDigitEntered(context.getString(R.string.seven_digit))
        }
        buttonEight.setOnClickListener {
            callback?.onDigitEntered(context.getString(R.string.eight_digit))
        }
        buttonNine.setOnClickListener {
            callback?.onDigitEntered(context.getString(R.string.nine_digit))
        }
        buttonReset.setOnClickListener {

            val adb = AlertDialog.Builder(context)
            adb.setPositiveButton(
                context.getString(R.string.clear)
            ) { _: DialogInterface?, _: Int -> callback?.onPinDropConfirmed() }
                .setNegativeButton(
                    context.getString(R.string.cancel)
                ) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
            val alert = adb.create()
            alert.setTitle(context.getString(R.string.attention))
            alert.setMessage(context.getString(R.string.reset_pin_code_warning))
            alert.setCancelable(true)
            alert.show()
        }
        buttonZero.setOnClickListener {
            callback?.onDigitEntered(context.getString(R.string.zero_digit))
        }
        buttonBackspace.setOnClickListener {
            callback?.onBackspacePressed()
        }

    }

    private fun setRippleColor(rippleColor: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }
        for (button in buttonsList) {

            button.foreground = RippleDrawable(
                ColorStateList.valueOf(rippleColor),
                null,
                ColorDrawable(Color.BLACK)
            )
        }
    }

    private fun setDotsColor(dotsColor: Int) {
        emptyDotDrawable?.let {
            val porterDuffColorFilter = PorterDuffColorFilter(
                dotsColor,
                PorterDuff.Mode.SRC_ATOP
            )

            it.colorFilter = porterDuffColorFilter
        }
        filledDotDrawable?.let {
            val porterDuffColorFilter = PorterDuffColorFilter(
                dotsColor,
                PorterDuff.Mode.SRC_ATOP
            )

            it.colorFilter = porterDuffColorFilter
        }

    }

    private fun setDotsSize(dotSize: Int) {
        for (dot in dotsList) {
            dot.layoutParams.width = dotSize
            dot.layoutParams.height = dotSize
        }
    }

    private fun setEmptyDotIcon(dotIcon: Drawable) {
        for (dot in dotsList) {
            dot.setImageDrawable(dotIcon)
        }

    }

    private fun setButtonsTextColor(buttonsTextColor: Int) {
        for (button in buttonsList) {
            if (button.id == R.id.pin_digits_btn_backspace) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    for (drawable in button.compoundDrawablesRelative) {
                        drawable?.let {
                            val porterDuffColorFilter = PorterDuffColorFilter(
                                buttonsTextColor,
                                PorterDuff.Mode.SRC_ATOP
                            )

                            it.colorFilter = porterDuffColorFilter
                        }
                    }
                } else {
                    for (drawable in button.compoundDrawables) {
                        drawable?.let {
                            val porterDuffColorFilter = PorterDuffColorFilter(
                                buttonsTextColor,
                                PorterDuff.Mode.SRC_ATOP
                            )

                            it.colorFilter = porterDuffColorFilter
                        }
                    }
                }
            }
            button.setTextColor(buttonsTextColor)
        }
    }

    private fun setButtonsBackground(buttonsColor: Int, pressedColor: Int) {
        for (button in buttonsList) {
            button.background = getPressedColorRippleDrawable(buttonsColor, pressedColor)
        }
    }


    fun setBackspaceIcon(drawable: Drawable, buttonSize: Int) {
        val drawableSize = drawable.intrinsicWidth
        if (buttonSize < drawableSize) {
            return
        }
        val padding = (buttonSize - drawableSize) / 2
        buttonBackspace.setPadding(
            padding,
            buttonBackspace.paddingTop,
            buttonBackspace.paddingRight,
            buttonBackspace.paddingBottom
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            buttonBackspace.setCompoundDrawablesRelativeWithIntrinsicBounds(
                drawable,
                null,
                null,
                null
            )
        } else {
            buttonBackspace.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
        }
    }


    fun setButtonsMargin(margin: Int) {
        for (button in buttonsList) {
            val newLayoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
            )

            val constraintSet = ConstraintSet()

            constraintSet.clone(parentLayout)

            constraintSet.setMargin(button.id, ConstraintSet.START, margin)
            constraintSet.setMargin(button.id, ConstraintSet.END, margin)
            constraintSet.setMargin(button.id, ConstraintSet.TOP, margin)
            constraintSet.setMargin(button.id, ConstraintSet.BOTTOM, margin)

            layoutParams = newLayoutParams
            constraintSet.applyTo(parentLayout)

            parentLayout.invalidate()
        }
    }

    fun setButtonsSize(size: Int) {
        for (button in buttonsList) {
            button.layoutParams.width = size
            button.layoutParams.height = size
        }
    }

    fun setButtonsTextSize(size: Int) {
        for (button in buttonsList) {
            if (button.id == R.id.pin_digits_btn_reset) {
                button.setTextSize(TypedValue.COMPLEX_UNIT_PX, size.toFloat() / 1.8f)
                continue
            }
            button.setTextSize(TypedValue.COMPLEX_UNIT_PX, size.toFloat())
        }
    }

    fun setDigitCallback(callback: PinCodeDigitsCallback) {
        this.callback = callback
    }

    fun hideReset() {
        buttonReset.visibility = View.GONE
    }

    fun setResetText(text: String) {
        buttonReset.text = text
    }

    fun clearDots() {
        assignEmptyDot(firstDot)
        assignEmptyDot(secondDot)
        assignEmptyDot(thirdDot)
        assignEmptyDot(fourthDot)
        assignEmptyDot(fifthDot)
        assignEmptyDot(sixthDot)
    }


    fun switchDot(length: Int) {
        when (length) {
            0 -> {
                clearDots()
                if (currentDotsFilled < length) {
                    animateDotFill(firstDot)
                } else {
                    assignEmptyDot(secondDot)
                }
            }
            1 -> {
                if (currentDotsFilled < length) {
                    animateDotFill(firstDot)
                } else {
                    assignEmptyDot(secondDot)
                }
            }
            2 -> {
                if (currentDotsFilled < length) {
                    animateDotFill(secondDot)
                } else {
                    assignEmptyDot(thirdDot)
                }
            }
            3 -> {
                if (currentDotsFilled < length) {
                    animateDotFill(thirdDot)
                } else {
                    assignEmptyDot(fourthDot)
                }
            }
            4 -> {
                if (currentDotsFilled < length) {
                    animateDotFill(fourthDot)
                } else {
                    assignEmptyDot(fifthDot)
                }
            }
            5 -> {
                if (currentDotsFilled < length) {
                    animateDotFill(fifthDot)
                } else {
                    assignEmptyDot(sixthDot)
                }
            }
            6 -> {
                if (currentDotsFilled < length) {
                    animateDotFill(sixthDot)
                }
            }
        }
        calculateCurrentDotsFilled(length)
    }


    @Suppress("DEPRECATION")
    fun wrongClear() {
        val pm: PackageManager = context.packageManager
        if (pm.checkPermission(
                Manifest.permission.VIBRATE,
                context.packageName
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager =
                    context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vibratorManager.defaultVibrator.vibrate(VibrationEffect.createOneShot(500, 10))
            } else {
                val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(
                        VibrationEffect.createOneShot(
                            500,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                } else {
                    v.vibrate(500)
                }
            }

        }
        animateDotsShake()
        clearDots()
    }

    fun animateDotFill(dot: AppCompatImageView) {
        assignFillDot(dot)
        val animatorSet = AnimatorSet()
        animatorSet.play(ObjectAnimator.ofFloat(dot, View.SCALE_X, 0f, 1.1f, 1f))
            .with(ObjectAnimator.ofFloat(dot, View.SCALE_Y, 0f, 1.1f, 1f))
        animatorSet.duration = 300
        animatorSet.interpolator = DecelerateInterpolator()
        animatorSet.start()
    }

    fun animateDotsShake() {
        val animatorSet = AnimatorSet()
        animatorSet.play(
            ObjectAnimator.ofFloat(
                dotLayout,
                View.TRANSLATION_X,
                0f,
                30f,
                -30f,
                25f,
                -25f,
                15f,
                -15f,
                6f,
                -6f,
                0f
            )
        )
        animatorSet.duration = 300
        animatorSet.interpolator = DecelerateInterpolator()
        animatorSet.start()
    }


    fun clearOneDot(length: Int) {
        when (length) {
            0 -> assignEmptyDot(firstDot)
            1 -> assignEmptyDot(secondDot)
            2 -> assignEmptyDot(thirdDot)
            3 -> assignEmptyDot(fourthDot)
            4 -> assignEmptyDot(fifthDot)
            5 -> assignEmptyDot(sixthDot)
        }
        activateDigits()
    }

    fun setMaxDots(maxDots: Int) {
        if (maxDots > 6 || maxDots < 3) {
            return
        }
        maxDotCount = maxDots
        when (maxDots) {
            3 -> {
                fourthDot.visibility = GONE
                fifthDot.visibility = GONE
                sixthDot.visibility = GONE
            }
            4 -> {
                fourthDot.visibility = VISIBLE
                fifthDot.visibility = GONE
                sixthDot.visibility = GONE
            }
            5 -> {
                fourthDot.visibility = VISIBLE
                fifthDot.visibility = VISIBLE
                sixthDot.visibility = GONE
            }
            6 -> {
                fourthDot.visibility = VISIBLE
                fifthDot.visibility = VISIBLE
                sixthDot.visibility = VISIBLE
            }
        }
    }

    fun fillDot(length: Int) {
        when (length) {
            1 -> animateDotFill(firstDot)
            2 -> animateDotFill(secondDot)
            3 -> animateDotFill(thirdDot)
            4 -> animateDotFill(fourthDot)
            5 -> animateDotFill(fifthDot)
            6 -> animateDotFill(sixthDot)
        }
    }

    fun fillDotsUntil(length: Int) {
        if (length <= 0) {
            return
        }
        for (i in 1..length) {
            fillDot(i)
        }
    }

    fun blockDigits() {
        buttonOne.isEnabled = false
        buttonTwo.isEnabled = false
        buttonThree.isEnabled = false
        buttonFour.isEnabled = false
        buttonFive.isEnabled = false
        buttonSix.isEnabled = false
        buttonSeven.isEnabled = false
        buttonEight.isEnabled = false
        buttonNine.isEnabled = false
        buttonZero.isEnabled = false
    }

    fun activateDigits() {
        buttonOne.isEnabled = true
        buttonTwo.isEnabled = true
        buttonThree.isEnabled = true
        buttonFour.isEnabled = true
        buttonFive.isEnabled = true
        buttonSix.isEnabled = true
        buttonSeven.isEnabled = true
        buttonEight.isEnabled = true
        buttonNine.isEnabled = true
        buttonZero.isEnabled = true
    }

    private fun assignEmptyDot(imageView: AppCompatImageView) {
        emptyDotDrawable?.let {
            imageView.setImageDrawable(it)
            return
        }
        imageView.setImageResource(R.drawable.vector_pin_empty_dot)
    }

    private fun assignFillDot(imageView: AppCompatImageView) {
        filledDotDrawable?.let {
            imageView.setImageDrawable(it)
            return
        }
        imageView.setImageResource(R.drawable.vector_pin_filled_dot)
    }

    private fun calculateCurrentDotsFilled(length: Int) {
        if (length == maxDotCount) {
            currentDotsFilled = 0
            return
        }
        currentDotsFilled = length
    }

    fun getPressedColorRippleDrawable(normalColor: Int, pressedColor: Int): Drawable {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RippleDrawable(
                getPressedColorSelector(normalColor, pressedColor)!!,
                getColorDrawableFromColor(normalColor),
                null
            )
        } else {
            return getStateListDrawable(normalColor, pressedColor)
        }
    }

    fun getStateListDrawable(
        normalColor: Int, pressedColor: Int
    ): StateListDrawable {
        val states = StateListDrawable()
        states.addState(
            intArrayOf(android.R.attr.state_pressed),
            ColorDrawable(pressedColor)
        )
        states.addState(
            intArrayOf(android.R.attr.state_focused),
            ColorDrawable(pressedColor)
        )
        states.addState(
            intArrayOf(android.R.attr.state_activated),
            ColorDrawable(pressedColor)
        )
        states.addState(
            intArrayOf(),
            ColorDrawable(normalColor)
        )
        return states
    }

    fun getPressedColorSelector(normalColor: Int, pressedColor: Int): ColorStateList? {
        return ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf(android.R.attr.state_focused),
                intArrayOf(android.R.attr.state_activated),
                intArrayOf()
            ), intArrayOf(
                pressedColor,
                pressedColor,
                pressedColor,
                normalColor
            )
        )
    }

    fun getColorDrawableFromColor(color: Int): ColorDrawable? {
        return ColorDrawable(color)
    }

}