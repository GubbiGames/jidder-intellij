package com.jidder.lang;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.util.NlsSafe;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public final class JidderFileType extends LanguageFileType {
    public static final JidderFileType INSTANCE = new JidderFileType();

    private JidderFileType() {
        super(JidderLanguage.INSTANCE);
    }

    @Override
    public @NonNls @NotNull String getName() {
        return "Jidder";
    }

    @Override
    public @NlsContexts.Label @NotNull String getDescription() {
        return "Jidder script";
    }

    @Override
    public @NlsSafe @NotNull String getDefaultExtension() {
        return "jdr";
    }

    @Override
    public @Nullable Icon getIcon() {
        return null;
    }
}
