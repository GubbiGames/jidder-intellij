package no.gubbi.jidder.lang

import com.intellij.lang.Language
import com.intellij.psi.codeStyle.CommonCodeStyleSettings
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider

class JidderLanguageCodeStyleSettingsProvider : LanguageCodeStyleSettingsProvider() {
    override fun getLanguage(): Language = JidderLanguage.INSTANCE

    override fun customizeDefaults(
        commonSettings: CommonCodeStyleSettings,
        indentOptions: CommonCodeStyleSettings.IndentOptions
    ) {
        indentOptions.INDENT_SIZE = 2
        indentOptions.TAB_SIZE = 2
        indentOptions.USE_TAB_CHARACTER = false
        indentOptions.CONTINUATION_INDENT_SIZE = 2
    }

    /**
     * Should not be used in current plugin, so we provide a simple sample as backup.
     */
    override fun getCodeSample(settingsType: SettingsType): String {
        return """
            // Sample Jidder code
            Party: "Hello World"
        """.trimIndent()
    }
}
