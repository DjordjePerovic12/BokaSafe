import java.io.FileInputStream
import java.util.Properties

plugins {
    `version-catalog`
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.hilt.android.plugin)
    id("kotlin-parcelize")
//    id("com.google.gms.google-services")
    id ("com.google.firebase.crashlytics")
}
val keystorePropertiesFile = rootProject.file("app/keystore.properties")
// Initialize a new Properties() object called keystoreProperties.
val keystoreProperties = Properties()
// Load your keystore.properties file into the keystoreProperties object.
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = "llc.bokadev.bokabayseatrafficapp"
    compileSdk = 35


    signingConfigs {
        create("bokadev") {
            keyAlias = keystoreProperties["KeyAlias"] as String
            keyPassword = keystoreProperties["KeyPassword"] as String
            storeFile = file(keystoreProperties["StorePath"]!!)
            storePassword = keystoreProperties["StorePassword"] as String
        }
    }

    defaultConfig {
        applicationId = "llc.bokadev.bokabayseatrafficapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        val mapsApiKey: String? = project.findProperty("MAPS_API_KEY") as String?
        manifestPlaceholders["mapsApiKey"] = mapsApiKey ?: ""
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("bokadev")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/gradle/incremental.annotation.processors"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.gms.play.services.location)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.coil.kt.compose)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)
    implementation(libs.moshi)
    ksp(libs.moshi.kotlin)
    implementation(libs.lottie)
    implementation(libs.core.splash.screen)
    implementation(libs.timber)


    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.hilt)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.okHttp)
    implementation(libs.bundles.lifecycle)

    //Google Maps
    implementation(libs.play.services.maps.v1810)

    //Compose Maps
    implementation(libs.maps.compose)
    implementation(libs.maps.widgets)

    // Google Locations
    implementation (libs.gms.play.services.location)

    implementation(libs.accompanist.permissions)

    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
}