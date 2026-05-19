plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.prosolution.common"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    implementation("androidx.annotation:annotation:1.7.0")
}
