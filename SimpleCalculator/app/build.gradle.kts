plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.simplecalculator"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.simplecalculator"
        minSdk = 30
        targetSdk = 33
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
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(files("../libs/commons-lang3-3.13.0.jar"))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
//    implementation("org.apache.commons:commons-lang3:3.13.0")
    // https://mvnrepository.com/artifact/org.mozilla/rhino
    implementation("org.mozilla:rhino:1.7.14")
    implementation("org.mariuszgromada.math:MathParser.org-mXparser:5.2.1")
    implementation("net.objecthunter:exp4j:0.4.8")


}