plugins {
    kotlin("jvm") version "1.3.71"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter", version = "5.6.1")
    testImplementation(group = "com.natpryce",      name = "hamkrest",      version = "1.4.0.0")
}

tasks {
    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}