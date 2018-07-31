package com.onesidedcab.user.utils

import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView

object ValidateHelper {

    fun validateEditText(editText: EditText?): Boolean {

        if (editText == null) return false
        if (editText.text.toString().trim { it <= ' ' }.length == 0) return false

        return true
    }

    fun validateText(editText: TextView?): Boolean {

        if (editText == null) return false
        if (editText.text.toString().trim { it <= ' ' }.length == 0) return false

        return true
    }


    fun validateEditTextMobile(editText: EditText?): Boolean {

        if (editText == null) return false
        if (editText.text.toString().trim { it <= ' ' }.length == 0) return false
        if (editText.text.toString().trim { it <= ' ' }.length < 10) return false

        return true
    }


    fun validateEmail(editText: EditText): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(editText.text.toString().trim { it <= ' ' }).matches()
    }

    fun validateMatchPassword(editText1: EditText, editText2: EditText): Boolean {

        val val1 = editText1.text.toString().trim { it <= ' ' }
        val val2 = editText2.text.toString().trim { it <= ' ' }

        if (!val1.equals(val2, ignoreCase = true)) {
            return false
        }
        return true
    }

    fun validateValueIsNotZero(editText: String?): Boolean {

        if (editText == null) return false
        if (editText.toString().trim { it <= ' ' }.length == 0) return false
        if (!Utils.isNumeric(editText.toString().trim { it <= ' ' })) return false
        val `val` = Integer.parseInt(editText)
        if (`val` <= 0) return false

        return true
    }


    fun validateRadioButtonIsChecked(radioGroup: RadioGroup): Boolean {


        if (radioGroup.checkedRadioButtonId != -1) {
            return true
            // hurray at-least on radio button is checked.
        } else {
            return false
            // pls select at-least one radio button.. since id is -1 means no button is check
        }

    }


    fun validateCheckboxChecked(checkBox: CheckBox): Boolean {

        //code to check if this checkbox is checked!

        if (checkBox.isChecked) {

            return true
        }
        return false

    }
}