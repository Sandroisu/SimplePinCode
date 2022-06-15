# Simple Pin Code


This library implements a screen for entering an access code (pin code).

You can use a ready-to-use implementation - for this you need to create a fragment that inherits PinCodeFragment and implement its abstract methods:
- `abstract fun isCreateNewCode(): Boolean` - to determine which mode you are in - creating a PIN code or entering an existing one
- `abstract fun getSavedPinCode(): String` - to return a saved pin code(you can return empty string if in create mode). Note that string length suppose to be same as result of `getMaxPinLength()`
- `abstract fun onCodeMatch(existPinCodeResult: ExistPinCodeResult)` - this function is called if the user has entered a code identical to the saved one(the one that `getSavedPinCode()` function returns) - `isCreateNewCode()` returns false.
- `abstract fun onNewCodeMatch(newPinCodeResult: NewPinCodeResult)` - this function is called if the user has entered an identical code again after the initial entry - creation mode, `isCreateNewCode()` returns true.
- `abstract fun codeResetConfirmed()` - this function is called if the user confirmed code reset.
- `abstract fun getMaxPinLength(): Int` - pin code length
- 
Another choice is to use a ready-made View - element - PinCodeLayout in which there are enough methods for customization (both through kotlin code and through xml):
```
app:dotFilledIcon
app:dotEmptyIcon 
app:dotsSize
app:dotsColor
app:buttonsMargin
app:buttonsSize
app:buttonsTextSize
app:resetText
app:backspaceIcon
app:buttonsBackgroundColor
app:buttonsTextColor
```      
