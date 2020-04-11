#### AndroidUtils

#### Setup

1. Setting up app level build.gradle

1.1 Add this under **android {}** just after buildToolsVersion

```groovy
dataBinding {
        enabled = true
}
```

1.2 Inside **defaultConfig {}**

```groovy
multiDexEnabled true
```

2. Setting up Dependency

```groovy
dependencies {
    implementation todo:jitpackurl
}
```

3. Setting up project level build.gradle

-add jitpack.io repository

4. Creating a new app that extends BaseApplication()

4.1 Creating the kotlin app
```kotlin
class MyApp : BaseApplication() {
    override fun afterOnCreate() {
        //TODO: do initialization stuff
    }
}
```

4.2 Dont forget to register the app in manifest.xml like,
```xml
 <application
        android:name=".MyApp" 
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
</application>
```


#### Classes

#### Usage

