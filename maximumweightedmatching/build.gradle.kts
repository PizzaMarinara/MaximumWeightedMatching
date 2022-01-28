val junitVersion by extra { "4.13.2" }
val junitAndroidxVersion by extra { "1.1.3" }
plugins {
    id("com.android.library")
    kotlin("android")
    id("maven-publish")
}

android {
    defaultConfig {
        compileSdk = 31
        minSdk = 23
        targetSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }
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

    // Core
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")

    // Tests
    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test.ext:junit:$junitAndroidxVersion")
}

publishing {
    publications {
        create<MavenPublication>("MaximumWeightedMatching") {
            groupId = "dev.efantini"
            artifactId = "maximumweightedmatching"
            version = "0.1.5"
            afterEvaluate { artifact(tasks.getByName("bundleReleaseAar")) }
        }
    }
}
