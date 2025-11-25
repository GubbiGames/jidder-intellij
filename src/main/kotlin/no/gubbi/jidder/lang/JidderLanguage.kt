package no.gubbi.jidder.lang

import com.intellij.lang.Language

class JidderLanguage private constructor() : Language("Jidder") {
    companion object {
        val INSTANCE: JidderLanguage = JidderLanguage()
    }
}
