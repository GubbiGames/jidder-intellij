package com.jidder.textmate

import com.intellij.openapi.application.PathManager
import org.jetbrains.plugins.textmate.api.TextMateBundleProvider
import org.jetbrains.plugins.textmate.api.TextMateBundleProvider.PluginBundle
import java.io.IOException
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path

class JidderTextMateBundleProvider : TextMateBundleProvider {
    override fun getBundles(): List<PluginBundle> {
        try {
            val bundleTempDir = Files.createTempDirectory(Path.of(PathManager.getTempPath()), "textmate-jidder")

            val classLoader = JidderTextMateBundleProvider::class.java.getClassLoader();

            for (fileToCopy in listOf(
                "package.json",
                "syntaxes/Jidder.tmLanguage.json"
            )) {
                val resource: URL = classLoader.getResource("textmate/jidder/$fileToCopy")!!

                resource.openStream().use { resourceStream ->
                    val target = bundleTempDir.resolve(fileToCopy)
                    Files.createDirectories(target.parent)
                    Files.copy(resourceStream, target)
                }
            }

            return listOf(PluginBundle("Jidder", bundleTempDir!!))
        } catch (e: IOException) {
            throw RuntimeException("Failed to load TextMate bundle", e)
        }
    }

}
