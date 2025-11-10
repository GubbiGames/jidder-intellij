package com.jidder.lang;

import com.intellij.lang.Language;

public final class JidderLanguage extends Language {
    public static final JidderLanguage INSTANCE = new JidderLanguage();

    private JidderLanguage() {
        super("Jidder");
    }
}
