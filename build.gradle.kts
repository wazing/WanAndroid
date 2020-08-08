// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
//    ext.kotlin_version = "1.3.72"
//    ext.compilesdk_version = 30
//    ext.build_tools_version = "30.0.0"
//    ext.minsdk_version = 26
//    ext.targetsdk_version = 30

    repositories {
        google()
        jcenter()
    }
    dependencies {
//        classpath "com.android.tools.build:gradle:4.0.0"
//        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72"
        classpath("com.android.tools.build:gradle:4.0.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
//        maven { url "https://jitpack.io" }
        maven("https://jitpack.io")
    }
}

//task clean (type: Delete) {
//    delete rootProject . buildDir
//}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}