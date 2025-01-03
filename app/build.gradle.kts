plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinAndroidKsp)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.byansanur.campuslist"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.byansanur.campuslist"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

val mockitoAgent = configurations.create("mockitoAgent")

tasks.withType<Test> {
    jvmArgs("-javaagent:${mockitoAgent.singleFile}") // Use singleFile for JVM agent path
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

    // test
    testImplementation(libs.junit)
    testImplementation(libs.mockito.test)
    mockitoAgent(libs.mockito.test) { isTransitive = false }

    androidTestImplementation(libs.arch.core.testing)
    testImplementation(libs.arch.core.testing)

    testImplementation(libs.hilt.testing)
    androidTestImplementation(libs.hilt.testing)
    kspAndroidTest(libs.hilt.testing.compiler)
    kspTest(libs.hilt.testing.compiler)

    androidTestImplementation(libs.room.test)
    testImplementation(libs.room.test)

    testImplementation(libs.kotlin.coroutines.test)

    testImplementation(libs.ktor.test.mock)
    testImplementation(libs.ktor.logging)
    testImplementation(libs.ktor.serialization.ktx)
    testImplementation(libs.ktor.content.negotiation)

    testImplementation(libs.io.mockk)
    testImplementation(libs.io.mockk.agent)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(libs.ktor.android)
    implementation(libs.ktor.core)
    implementation(libs.ktor.serialization)
    implementation(libs.ktor.logging)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.ktor.serialization.ktx)

    implementation(libs.kotlin.coroutines)
    implementation(libs.kotlin.coroutines.android)

    implementation(libs.google.gson)

    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    implementation(libs.android.navigation)
    implementation(libs.hilt.navigation)

    implementation(libs.compose.runtime)
    implementation(libs.compose.runtime.livedata)

    implementation(libs.kotlin.serial)
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}