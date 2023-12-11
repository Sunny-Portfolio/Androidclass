plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
//    kotlin("kapt") version "1.9.21
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs")



}

android {
    namespace = "com.example.bytecrunch"
    compileSdk = 34

//    compileOptions {
//        sourceCompatibility (JavaVersion.VERSION_1_8)
//        targetCompatibility (JavaVersion.VERSION_1_8)
//    }

    defaultConfig {
        applicationId = "com.example.bytecrunch"
        minSdk = 28
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
//        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")




    // Dependencies Version
    var V_retrofit = "2.9.0"
    var V_nav = "2.7.5"
    var V_room = "2.6.1"
    var V_glide = "4.16.0"
    var V_lifecycle = "2.6.2"
    var V_coroutines = "1.7.3"


    // ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel:$V_lifecycle")

    // Room
    implementation ("androidx.room:room-runtime:$V_room")
    annotationProcessor ("androidx.room:room-compiler:$V_room")
    implementation ("androidx.room:room-guava:$V_room")
    implementation("androidx.room:room-rxjava3:$V_room")


    // View Model n Life Cycle (Koltin)
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$V_lifecycle")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:$V_lifecycle")

    // Room (Koltin)
    implementation ("androidx.room:room-ktx:$V_room")
    ksp ("androidx.room:room-compiler:$V_room")


    // Coroutines (Koltin)
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:$V_coroutines")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:$V_coroutines")





    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:$V_retrofit")
    implementation ("com.squareup.retrofit2:converter-gson:$V_retrofit")

    // Logging Interceptor - use for debug API request
    implementation ("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // jetPack Navigation
    implementation ("androidx.navigation:navigation-fragment:$V_nav")
    implementation ("androidx.navigation:navigation-ui:$V_nav")
    implementation ("androidx.navigation:navigation-dynamic-features-fragment:$V_nav")

    // Glide
    implementation ("com.github.bumptech.glide:glide:$V_glide")
//    annotationProcessor ("com.github.bumptech.glide:compiler:$V_glide")
    ksp ("com.github.bumptech.glide:compiler:$V_glide")

    // Livedate
    implementation ("androidx.lifecycle:lifecycle-livedata:$V_lifecycle")

    // Swipe Refresh layout from jet pack
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // Google's Gson
    implementation("com.google.code.gson:gson:2.10.1")

}