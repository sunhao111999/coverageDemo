coverageDemo
============
 - add file FinishListener, InstrumentedActivity, JacocoInstrumentation to your $TEST_PATH
 - inside your android tag. [Jacoco Version] [1]



 ```sh
    android {
        jacoco {
            version = '0.7.0.201403182114'
        }
    
        buildTypes {
            debug {
                testCoverageEnabled = true
            }
        }
    }
    
    def coverageSourceDirs = [ '../app/src' ]

    task jacocoTestReport(type: JacocoReport) {
        group = "Reporting"
        description = "Generate Jacoco coverage reports"
        classDirectories = fileTree(
                dir: '../app/build/intermediates/classes',
                excludes: ['**/R.class',
                           '**/R$*.class',
                           '**/*$ViewInjector*.*',
                           '**/BuildConfig.*',
                           '**/Manifest*.*'])
        additionalSourceDirs = files(coverageSourceDirs)
        sourceDirectories = files(coverageSourceDirs)
        executionData = files('../coverage.ec')
    
        reports {
            xml.enabled = true
            html.enabled = true }
    }
 ```
 
 - inside your $AndroidMainfest.xml

```
<application
    <activity android:label="InstrumentationActivity"
        android:name="com.example.test.InstrumentedActivity" />
</application>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<instrumentation
    android:handleProfiling="true"
    android:label="CoverageInstrumentation"
    android:name="com.example.test.JacocoInstrumentation"
    android:targetPackage="com.example.sunhao" />
```
 

- build your app use ./gradlew clean build and reinstall, mark sure the app isnt running
- start the app and testing

```
adb shell am instrument com.example.sunhao/com.example.test.JacocoInstrumentation
```

- use adb pull coverage data to project path from /data/data/com.example.sunhao/files/coverage.ec

- run ./gradlew jacocoTestReport get html coverage report
 
 
 

[1]:http://mvnrepository.com/artifact/org.jacoco/jacoco-maven-plugin
