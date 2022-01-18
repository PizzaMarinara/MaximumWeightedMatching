buildscript {
    val gradlePluginVersion by extra { "7.0.4" }
    val kotlinVersion by extra { "1.6.10" }

    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://jitpack.io")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:$gradlePluginVersion")
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
