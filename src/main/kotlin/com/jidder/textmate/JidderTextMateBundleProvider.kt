package com.jidder.textmate

import com.intellij.openapi.application.PluginPathManager
import org.jetbrains.plugins.textmate.api.TextMateBundleProvider

class JidderTextMateBundleProvider : TextMateBundleProvider {
    override fun getBundles() =
        PluginPathManager.getPluginResource(javaClass, "textmate/jidder")
            ?.let { listOf(TextMateBundleProvider.PluginBundle("jidder", it.toPath())) }
            ?: emptyList()
}
