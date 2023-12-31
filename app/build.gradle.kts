plugins {
    id(Plugins.ANDROID_APPLICATION)
    kotlin(Plugins.Kotlin.ANDROID)
    kotlin(Plugins.Kotlin.KAPT)
    id(Plugins.HILT)
    id(Plugins.FIREBASE)
    id(Plugins.SAFE_ARGS)
}

android {
    compileSdk = Versions.COMPILE_SDK
    buildToolsVersion = Versions.BUILD_TOOLS

    signingConfigs {
        create("release") {
            storeFile = rootProject.file(project.property("releaseKeystoreFileName") as String)
            storePassword = project.property("releaseStorePassword") as String
            keyAlias = project.property("releaseKeyAlias") as String
            keyPassword = project.property("releaseKeyPassword") as String
        }
    }

    defaultConfig {
        applicationId = App.ID
        minSdk = Versions.MIN_SDK
        targetSdk = Versions.TARGET_SDK
        versionCode = App.Version.CODE
        versionName = App.Version.NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            signingConfig = signingConfigs.getByName("release")
        }

        buildTypes.forEach { type ->
            val appCenter = BuildConfigFields.APPCENTER_SECRET
            type.buildConfigField(appCenter.type, appCenter.title, appCenter.value)
        }
    }


    flavorDimensions(App.Dimension.DEFAULT)

    productFlavors {
        create(App.Flavor.DEV) {

        }

        create(App.Flavor.QA) {

        }

        create(App.Flavor.PRODUCTION) {

        }
    }

    buildFeatures {
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(defaultFileTree())

    // Core
    implementation(Libs.CORE_KTX)

    // UI
    implementation(Libs.APPCOMPAT)
    implementation(Libs.CONSTRAINT_LAYOUT)
    implementation(Libs.RECYCLERVIEW)

    // Jetpack
    implementation(Libs.ACTIVITY_KTX)
    implementation(Libs.FRAGMENT_KTX)
    // ViewModel
    implementation(Libs.LIFECYCLE_VIEWMODEL_KTX)
    // LiveData
    implementation(Libs.LIFECYCLE_LIVEDATA_KTX)
    // Navigation
    implementation(Libs.NAVIGATION_UI_KTX)
    implementation(Libs.NAVIGATION_FRAGMENT_KTX)
    // Hilt
    implementation(Libs.HILT)
    kapt(Libs.HILT_DAGGER_COMPILER)
    kapt(Libs.HILT_COMPILER)

    // Firebase
    implementation(Libs.FIREBASE_AUTH)

    implementation(Libs.FIREBASE_DATABASE)

    // Material
    implementation(Libs.MATERIAL)

    // Coroutines
    implementation(Libs.COROUTINES_CORE)
    implementation(Libs.COROUTINES_ANDROID)

    // Retrofit
    implementation(Libs.RETROFIT)
    implementation(Libs.RETROFIT_GSON)
    implementation(Libs.OKHTTP3)
    implementation(Libs.OKHTTP3_LOGGING_INTERCEPTOR)

    // Gson
    implementation(Libs.GSON)

    // AppCenter
    implementation(Libs.APPCENTER_ANALYTICS)
    implementation(Libs.APPCENTER_CRASHES)

    // Timber
    implementation(Libs.TIMBER)

    // Kotlin Reflect
    implementation(kotlin(Libs.REFLECT))

    // Alerter
    implementation(Libs.ALERTER)

    // Shimmer
    implementation(Libs.SHIMMER)
    
    // Glide
    implementation(Libs.GLIDE)
}
