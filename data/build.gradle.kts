
plugins {
    // Application Specific Plugins
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.jetbrainsKotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.androidHilt)
}

dependencies {
    //Compile time dependencies
    kapt(Libraries.lifecycleCompiler)
    kapt(Libraries.hiltCompiler)

    implementation(Libraries.hilt)
    implementation(Libraries.appCompat)
    implementation(Libraries.ktxCore)
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.kotlinCoroutines)
    implementation(Libraries.kotlinCoroutinesAndroid)
    implementation(project(":domain"))

    implementation(Libraries.retrofit)
    implementation(Libraries.okHttpLoggingInterceptor)

    testImplementation(TestLibraries.junit4)
    testImplementation(TestLibraries.mockk)
    testImplementation(TestLibraries.kluent)
    testImplementation(TestLibraries.robolectric)
}

android {
    compileSdk = AndroidSdk.compile

    defaultConfig {
        minSdk = AndroidSdk.min
        targetSdk = AndroidSdk.target
    }

    sourceSets {
        map { it.java.srcDir("src/${it.name}/kotlin") }
        getByName("main") { java.srcDir("$buildDir/generated/source/kapt/main") }
    }
}
