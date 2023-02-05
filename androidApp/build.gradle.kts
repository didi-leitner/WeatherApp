plugins {
    id("com.android.application")
    //id("com.google.dagger.hilt.android") version "2.44"
    //kotlin("kapt")
    kotlin("android")
}


android {
    compileSdk = 33
    defaultConfig {
        applicationId = "app.antran.kmmhelloworld.framework.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    // Added for Jetpack Compose
    buildFeatures {
        compose = true
    }
    composeOptions {
        //update to 1.4.0 breaks the build
        kotlinCompilerExtensionVersion = "1.3.0"//""1.2.0-alpha01"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":shared"))

    implementation("androidx.appcompat:appcompat:1.6.0")
    implementation("androidx.core:core-ktx:1.9.0")


    //update to 1.8.0 will break the build
    implementation("com.google.android.material:material:1.8.0")

    // Added for Jetpack Compose
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.compose.material:material:1.3.1")
    implementation("androidx.compose.ui:ui-tooling:1.3.3")
    implementation("androidx.compose.material3:material3:1.0.1")
    implementation("androidx.compose.material3:material3-window-size-class:1.0.1")
    implementation("androidx.compose.material:material:1.3.1")

    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.0-alpha01")
    implementation("androidx.compose.runtime:runtime-tracing:1.0.0-alpha01")
    implementation("androidx.navigation:navigation-compose:2.5.3")

    implementation("io.coil-kt:coil-compose:2.2.2")

    val dateTimeVersion = "0.4.0"
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$dateTimeVersion")


    val koinVersion = "3.2.0"
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-android:$koinVersion")
    implementation("io.insert-koin:koin-androidx-compose:$koinVersion")
    //Hilt
    //implementation("com.google.dagger:hilt-android:2.44")
    //kapt("com.google.dagger:hilt-android-compiler:2.44")

    implementation("androidx.tracing:tracing-ktx:1.2.0-alpha01")




}

// Allow references to generated code
//kapt {
//    correctErrorTypes = true
//}