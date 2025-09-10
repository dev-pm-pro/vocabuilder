package com.devpm.vocabuilder.views

import android.content.Context
import android.graphics.Typeface
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.devpm.vocabuilder.databinding.ViewTextEditableBinding
import com.devpm.vocabuilder.R
import androidx.core.content.withStyledAttributes

class TextEditableView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding: ViewTextEditableBinding = ViewTextEditableBinding.inflate(
        LayoutInflater.from(context), this, true
    )
    private var nullable: Boolean = false
    private var toggleable: Boolean = false
    private var isPwdOn = false

    init {
        context.withStyledAttributes(attrs, R.styleable.TextEditView, defStyleAttr, 0) {
            setHint(getString(R.styleable.TextEditView_hint) ?: "")
            setLabel(getString(R.styleable.TextEditView_label) ?: "")
            setValue(getString(R.styleable.TextEditView_value) ?: "")
            setType(getInt(R.styleable.TextEditView_type, InputType.TYPE_CLASS_TEXT))
            nullable = getBoolean(R.styleable.TextEditView_nullable, false)
            toggleable = getBoolean(R.styleable.TextEditView_toggleable, false)
        }
        if (toggleable) {
            binding.pwdVisToggle.visibility = View.VISIBLE
            binding.pwdVisToggle.setOnClickListener {
                togglePasswordVisibility()
            }
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
        val inputClass = type and InputType.TYPE_MASK_CLASS

        if ((inputClass == InputType.TYPE_CLASS_TEXT && (type and InputType.TYPE_TEXT_VARIATION_PASSWORD == InputType.TYPE_TEXT_VARIATION_PASSWORD))
            || (inputClass == InputType.TYPE_CLASS_NUMBER && (type and InputType.TYPE_NUMBER_VARIATION_PASSWORD == InputType.TYPE_NUMBER_VARIATION_PASSWORD))) {
            binding.textBox.transformationMethod = PasswordTransformationMethod.getInstance()
        } else {
            binding.textBox.transformationMethod = null
        }
    }
    fun setValue(text: String) {
        binding.textBox.setText(text)
    }

    private fun togglePasswordVisibility() {
        isPwdOn = !isPwdOn
        if (isPwdOn) {
            binding.textBox.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.pwdVisToggle.setImageResource(R.drawable.ic_eye_slash)
            binding.textBox.transformationMethod = null
        } else {
            binding.textBox.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.pwdVisToggle.setImageResource(R.drawable.ic_eye_solid)
            binding.textBox.transformationMethod = PasswordTransformationMethod.getInstance()
        }
        binding.textBox.typeface = Typeface.create("sans-serif-condensed", Typeface.NORMAL)
        binding.textBox.setSelection(binding.textBox.text?.length ?: 0)
    }
}
