plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.16.0"
}

group = "com.jidder"
version = "0.1.0"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

intellij {
    version.set("2023.3")
    type.set("IC")
    plugins.set(listOf("org.intellij.plugins.textmate"))
}

tasks {
    patchPluginXml {
        sinceBuild.set("233")
        untilBuild.set("")
    }
    buildSearchableOptions {
        enabled = false
    }
}
