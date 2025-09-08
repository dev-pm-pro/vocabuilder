package com.devpm.vocabuilder

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan

/**
 * Returns a SpannableStringBuilder where the first occurrence of [substring] within [fullText]
 * is styled with a bold typeface.
 *
 * If [substring] is not found within [fullText], the original text is returned without styling.
 *
 * @param fullText The full text in which to search and style a substring.
 * @param substring The substring to be styled in bold.
 * @return A SpannableStringBuilder with the [substring] styled bold if found.
 */
fun makeSubstringBold(fullText: String, substring: String): SpannableStringBuilder {
    val spannable = SpannableStringBuilder(fullText)
    val start = fullText.indexOf(substring)
    if (start >= 0) {
        val end = start + substring.length
        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return spannable
}
