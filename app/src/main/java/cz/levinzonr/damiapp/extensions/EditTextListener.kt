package cz.levinzonr.damiapp.extensions

import android.text.Editable
import android.text.TextWatcher


class EditTextListener(private val lambda: (String) -> Unit) : TextWatcher {

    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        // do nothing
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
       lambda(p0.toString())
    }
}