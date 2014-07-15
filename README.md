coverageDemo
============

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
 ```
 

- ./gradlew createDebugCoverageReport or ./gradlew connectedCheck
 
 
 

[1]:http://mvnrepository.com/artifact/org.jacoco/jacoco-maven-plugin
