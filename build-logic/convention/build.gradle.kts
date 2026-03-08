import org.jetbrains.kotlin.gradle.dsl.JvmTarget
//**base foundation to share Gradle configuration

plugins {
    // let create and reference conventions plugins here, recognize module as build-logic build
    `kotlin-dsl`
}

//similar to package name
group = "com.example.convention.buildlogic"

dependencies {

    //only used at compile time
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
}

//jdk target - version of java that can be used here
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}