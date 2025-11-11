package com.jidder.lang

import com.intellij.openapi.fileTypes.PlainTextSyntaxHighlighterFactory
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class JidderSyntaxHighlighterFactory : SyntaxHighlighterFactory() {
    override fun getSyntaxHighlighter(project: Project?, virtualFile: VirtualFile?): SyntaxHighlighter {
        return createTextMateHighlighter(project, virtualFile)
            ?: PlainTextSyntaxHighlighterFactory().getSyntaxHighlighter(project, virtualFile)
    }

    companion object {
        private const val SCOPE_NAME = "source.jidder"
        private const val SERVICE_CLASS = "org.jetbrains.plugins.textmate.TextMateService"

        private fun createTextMateHighlighter(
            project: Project?,
            virtualFile: VirtualFile?,
        ): SyntaxHighlighter? {
            return try {
                val serviceClass = Class.forName(SERVICE_CLASS)
                val service = serviceClass.getMethod("getInstance").invoke(null)
                for (method in serviceClass.methods) {
                    if (!SyntaxHighlighter::class.java.isAssignableFrom(method.returnType)) continue
                    val name = method.name
                    if (name != "getSyntaxHighlighter" && name != "createHighlighter") continue
                    val arguments = buildArguments(method.parameterTypes, project, virtualFile) ?: continue
                    val result = method.invoke(service, *arguments)
                    if (result is SyntaxHighlighter) {
                        return result
                    }
                }
                null
            } catch (_: ReflectiveOperationException) {
                null
            }
        }

        private fun buildArguments(
            parameterTypes: Array<Class<*>>,
            project: Project?,
            virtualFile: VirtualFile?,
        ): Array<Any?>? {
            val arguments = arrayOfNulls<Any?>(parameterTypes.size)
            for ((index, type) in parameterTypes.withIndex()) {
                arguments[index] = when {
                    type == String::class.java -> SCOPE_NAME
                    Project::class.java.isAssignableFrom(type) -> project
                    VirtualFile::class.java.isAssignableFrom(type) -> virtualFile
                    else -> return null
                }
            }
            return arguments
        }
    }
}
