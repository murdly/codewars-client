package com.akarbowy.codewarsclient.helpers.utils


object TextUtils {

     fun alphaNumericOrEmpty(src: CharSequence, start: Int, end: Int): CharSequence {
        for (i in start until end) {
            if (!Character.isLetterOrDigit(src[i])) {
                return ""
            }
        }

        return src
    }
}