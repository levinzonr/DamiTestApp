package cz.levinzonr.damiapp.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import cz.levinzonr.damiapp.R


fun EditText.onTextChange(onChange : (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            onChange(p0.toString())
        }
    })
}

fun String.validEmail() : Boolean {
    val regex = Regex("^[a-zA-Z0-9_.]+@[a-zA-Z.]+?.[a-zA-Z]{2,3}$")
    return regex.matches(this)
}

fun String.valid() : Boolean {
    return this.length > 4
}

fun TextView.letText(string: String?) {
    if (string == null || string.isEmpty())
        this.text = context.getString(R.string.field_not_available)
    else this.text = string
}