package com.devpm.vocabuilder.services

class Validity {
    companion object {
        fun checkFilled(s: String) = !s.isBlank()
    }
}
