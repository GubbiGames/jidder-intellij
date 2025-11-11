package com.jidder.lang

import com.intellij.openapi.fileTypes.LanguageFileType

object JidderFileType : LanguageFileType(JidderLanguage.INSTANCE) {
    override fun getName(): String = "Jidder"

    override fun getDescription(): String = "Jidder script"

    override fun getDefaultExtension(): String = "jdr"

    override fun getIcon() = null
}
