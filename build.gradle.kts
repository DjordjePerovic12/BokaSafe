// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.kotlin.ksp) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.hilt.android.plugin) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(libs.hilt.android.gradle.plugin)
//        classpath("com.google.gms:google-services:4.4.1")
        classpath ("com.google.firebase:firebase-crashlytics-gradle:2.9.9")
        // Add the dependency for the Google services Gradle plugin
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}