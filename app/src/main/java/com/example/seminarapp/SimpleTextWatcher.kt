package com.example.seminarapp

import android.text.Editable
import android.text.TextWatcher

/**
 * Helper abstract class agar tidak perlu override semua method TextWatcher
 * yang tidak dibutuhkan.
 */
abstract class SimpleTextWatcher : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(s: Editable?) {}
}
