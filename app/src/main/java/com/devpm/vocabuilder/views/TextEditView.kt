package com.devpm.vocabuilder.views

import android.content.Context
import android.graphics.Typeface
import android.renderscript.ScriptGroup.Input
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.devpm.vocabuilder.databinding.ViewTextEditBinding
import com.devpm.vocabuilder.R
import androidx.core.content.withStyledAttributes

class TextEditView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding: ViewTextEditBinding = ViewTextEditBinding.inflate(
        LayoutInflater.from(context), this, true
    )
    private var nullable: Boolean = false
    init {
        context.withStyledAttributes(attrs, R.styleable.TextEditView, defStyleAttr, 0) {
            setHint(getString(R.styleable.TextEditView_hint) ?: "")
            setLabel(getString(R.styleable.TextEditView_label) ?: "")
            setValue(getString(R.styleable.TextEditView_value) ?: "")
            setType(getInt(R.styleable.TextEditView_type, InputType.TYPE_CLASS_TEXT))
            nullable = getBoolean(R.styleable.TextEditView_nullable, false)
        }
    }

    fun clearError() {
        binding.textBox.error = null
    }
    fun clearValue() {
        binding.textBox.text?.clear()
    }
    fun getValue(): String? {
        val value = binding.textBox.text.toString()
        return if (nullable) value.ifBlank { null } else value
    }
    fun setError(message: String) {
        binding.textBox.error = message
    }
    fun setHint(resId: Int) {
        binding.textBox.setHint(resId)
    }
    fun setHint(text: String) {
        binding.textBox.hint = text
    }
    fun setLabel(resId: Int) {
        binding.label.setText(resId)
    }
    fun setLabel(text: String) {
        binding.label.text = text
    }
    fun setType(type: Int) {
        binding.textBox.inputType = type
        // Set typeface explicitly after changing input type
        binding.textBox.typeface = Typeface.create("sans-serif-condensed", Typeface.NORMAL)

        // Refresh transform method for passwords
        if (type and InputType.TYPE_TEXT_VARIATION_PASSWORD == InputType.TYPE_TEXT_VARIATION_PASSWORD ||
            type and InputType.TYPE_NUMBER_VARIATION_PASSWORD == InputType.TYPE_NUMBER_VARIATION_PASSWORD) {
            binding.textBox.transformationMethod = PasswordTransformationMethod.getInstance()
        } else {
            binding.textBox.transformationMethod = null
        }
    }
    fun setValue(text: String) {
        binding.textBox.setText(text)
    }
}
