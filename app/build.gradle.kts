import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

val keystoreProperties = Properties()
val keystorePropertiesFile = rootProject.file("keystore.properties")

if (keystorePropertiesFile.exists()) {
    keystorePropertiesFile.inputStream().use(keystoreProperties::load)
}

fun propertyOrEnv(propertyName: String, envName: String): String? {
    val propertyValue = keystoreProperties.getProperty(propertyName)?.trim().orEmpty()
    if (propertyValue.isNotEmpty()) return propertyValue

    val envValue = providers.environmentVariable(envName).orNull?.trim().orEmpty()
    return envValue.ifEmpty { null }
}

val releaseStoreFilePath = propertyOrEnv("storeFile", "GOALPULSE_UPLOAD_STORE_FILE")
val releaseStorePassword = propertyOrEnv("storePassword", "GOALPULSE_UPLOAD_STORE_PASSWORD")
val releaseKeyAlias = propertyOrEnv("keyAlias", "GOALPULSE_UPLOAD_KEY_ALIAS")
val releaseKeyPassword = propertyOrEnv("keyPassword", "GOALPULSE_UPLOAD_KEY_PASSWORD")
val hasReleaseSigning = listOf(
    releaseStoreFilePath,
    releaseStorePassword,
    releaseKeyAlias,
    releaseKeyPassword
).all { !it.isNullOrBlank() }

android {
    namespace = "com.prosolution.goalpulse"
    compileSdk = 36

    signingConfigs {
        if (hasReleaseSigning) {
            create("release") {
                storeFile = file(releaseStoreFilePath!!)
                storePassword = releaseStorePassword
                keyAlias = releaseKeyAlias
                keyPassword = releaseKeyPassword
                enableV1Signing = true
                enableV2Signing = true
                enableV3Signing = true
                enableV4Signing = true
            }
        }
    }

    defaultConfig {
        applicationId = "com.prosolution.goalpulse"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    androidResources {
        localeFilters += listOf("en", "bn", "es", "hi")
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            if (hasReleaseSigning) {
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }

    bundle {
        language {
            enableSplit = true
        }
        density {
            enableSplit = true
        }
        abi {
            enableSplit = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}

val validateReleaseSigning by tasks.registering {
    group = "release"
    description = "Validates release signing configuration required for Play Store AAB builds."

    doLast {
        check(hasReleaseSigning) {
            """
            Missing release signing configuration.

            Create a keystore.properties file in the project root or export these environment variables:
            - GOALPULSE_UPLOAD_STORE_FILE
            - GOALPULSE_UPLOAD_STORE_PASSWORD
            - GOALPULSE_UPLOAD_KEY_ALIAS
            - GOALPULSE_UPLOAD_KEY_PASSWORD
            """.trimIndent()
        }

        val storeFile = file(releaseStoreFilePath!!)
        check(storeFile.exists()) {
            "Release keystore file not found at: ${storeFile.absolutePath}"
        }
    }
}

tasks.matching { it.name in setOf("bundleRelease", "assembleRelease", "packageReleaseBundle") }
    .configureEach {
        dependsOn(validateReleaseSigning)
    }

dependencies {
    implementation(project(":common"))
    implementation(project(":core"))
    implementation(project(":designsystem"))
    implementation(project(":feature_home"))
    implementation(project(":feature_live"))
    implementation(project(":feature_match"))
    implementation(project(":feature_standings"))
    implementation(project(":feature_team"))
    implementation(project(":feature_profile"))
    implementation(project(":feature_notifications"))
    implementation(project(":feature_predictions"))

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material.icons)


    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)

    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}