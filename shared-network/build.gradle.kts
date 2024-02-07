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

    implementation(Libraries.RX_JAVA)
    implementation(Libraries.OKHTTP)
    implementation(Libraries.OKHTTP_LOGGER)

    implementation(Libraries.RETROFIT)
    implementation(Libraries.RETROFIT_CONVERTER_GSON)
    implementation(Libraries.RETROFIT_RX_JAVA_ADAPTER)
}
