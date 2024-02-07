plugins {
    id(PluginIds.JAVA_LIBRARY)
    id(PluginIds.KOTLIN_LIBRARY)
    id(PluginIds.KTLINT_ANDROID)
}

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation(Libraries.KOTLIN_STD_LIB)
}
