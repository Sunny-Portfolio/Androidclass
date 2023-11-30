plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.bytecrunch"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.bytecrunch"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
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
    var V_room = "2.6.0"
    var V_glide = "4.16.0"
    var V_lifecycle = "2.6.2"



    // ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel:$V_lifecycle")

    // Room
    implementation ("androidx.room:room-runtime:$V_room")
    implementation ("androidx.room:room-compiler:$V_room")

    // Kotlin Extensions and Coroutines support for Room
//    implementation ("androidx.room:room-ktx:2.2.5")

    // Coroutines ?
//    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")
//    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.5")

    // Coroutine Lifecycle Scopes ?
//    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
//    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:$V_retrofit")
    implementation ("com.squareup.retrofit2:converter-gson:$V_retrofit")
//    implementation ("com.squareup.okhttp3:logging-interceptor:4.5.0")

    // jetPack Navigation
    implementation ("androidx.navigation:navigation-fragment:$V_nav")
    implementation ("androidx.navigation:navigation-ui:$V_nav")
    implementation ("androidx.navigation:navigation-dynamic-features-fragment:$V_nav")

    // Glide
    implementation ("com.github.bumptech.glide:glide:$V_glide")
    annotationProcessor ("com.github.bumptech.glide:compiler:$V_glide")

    // Live

}