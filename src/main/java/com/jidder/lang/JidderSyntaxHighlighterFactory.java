package com.jidder.lang;

import com.intellij.openapi.fileTypes.PlainTextSyntaxHighlighterFactory;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class JidderSyntaxHighlighterFactory extends SyntaxHighlighterFactory {
    private static final String SCOPE_NAME = "source.jidder";
    private static final String SERVICE_CLASS = "org.jetbrains.plugins.textmate.TextMateService";

    @Override
    public @NotNull SyntaxHighlighter getSyntaxHighlighter(@Nullable Project project, @Nullable VirtualFile virtualFile) {
        SyntaxHighlighter highlighter = createTextMateHighlighter(project, virtualFile);
        if (highlighter != null) {
            return highlighter;
        }
        return new PlainTextSyntaxHighlighterFactory().getSyntaxHighlighter(project, virtualFile);
    }

    @Nullable
    private static SyntaxHighlighter createTextMateHighlighter(@Nullable Project project, @Nullable VirtualFile virtualFile) {
        try {
            Class<?> serviceClass = Class.forName(SERVICE_CLASS);
            Object service = serviceClass.getMethod("getInstance").invoke(null);
            for (Method method : serviceClass.getMethods()) {
                if (!SyntaxHighlighter.class.isAssignableFrom(method.getReturnType())) {
                    continue;
                }
                String name = method.getName();
                if (!"getSyntaxHighlighter".equals(name) && !"createHighlighter".equals(name)) {
                    continue;
                }
                Object[] arguments = buildArguments(method.getParameterTypes(), project, virtualFile);
                if (arguments == null) {
                    continue;
                }
                Object result = method.invoke(service, arguments);
                if (result instanceof SyntaxHighlighter) {
                    return (SyntaxHighlighter) result;
                }
            }
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
        }
        return null;
    }

    @Nullable
    private static Object[] buildArguments(Class<?>[] parameterTypes, @Nullable Project project, @Nullable VirtualFile virtualFile) {
        Object[] arguments = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> type = parameterTypes[i];
            if (String.class.equals(type)) {
                arguments[i] = SCOPE_NAME;
            } else if (Project.class.isAssignableFrom(type)) {
                arguments[i] = project;
            } else if (VirtualFile.class.isAssignableFrom(type)) {
                arguments[i] = virtualFile;
            } else {
                return null;
            }
        }
        return arguments;
    }
}
