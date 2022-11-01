plugins {
    id(BuildPlugins.javaLibrary)
    id(BuildPlugins.kotlinJvm)
    id(BuildPlugins.kotlinAndroidExtensions)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.kotlinCoroutines)
    implementation(Libraries.kotlinCoroutinesAndroid)
    api(Libraries.gson)
}