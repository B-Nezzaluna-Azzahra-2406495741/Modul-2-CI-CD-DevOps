plugins {
    java
    jacoco
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.sonarqube") version "7.1.0.6387"
}

group = "id.ac.ui.cs.advprog"
version = "0.0.1-SNAPSHOT"
val seleniumJavaVersion = "4.14.1"
val seleniumJupiterVersion = "5.0.1"
val webdrivermanagerVersion = "5.6.3"
val junitJupiterVersion = "5.9.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.seleniumhq.selenium:selenium-java:$seleniumJavaVersion")
    testImplementation("io.github.bonigarcia:selenium-jupiter:$seleniumJupiterVersion")
    testImplementation("io.github.bonigarcia:webdrivermanager:$webdrivermanagerVersion")
    testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")
}

// Mengonfigurasi task pengujian standar agar tidak menjalankan Functional Test di CI [cite: 972]
tasks.test {
    useJUnitPlatform()
    filter {
        excludeTestsMatching("*FunctionalTest")
    }
    finalizedBy(tasks.jacocoTestReport) // Laporan dibuat otomatis setelah tes [cite: 974]
}

tasks.register<Test>("unitTest") {
    description = "Runs unit tests"
    group = "verification"
    useJUnitPlatform()
    filter {
        excludeTestsMatching("*FunctionalTest")
    }
}

tasks.register<Test>("functionalTest") {
    description = "Runs functional tests"
    group = "verification"
    useJUnitPlatform()
    filter {
        includeTestsMatching("*FunctionalTest")
    }
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // Memastikan data tes tersedia [cite: 978]
    reports {
        xml.required.set(true) // Wajib untuk SonarQube
        html.required.set(true)
    }
}

sonar {
    properties {
        // Pastikan nilai ini sesuai dengan "Project Information" di dashboard SonarCloud kamu
        property("sonar.projectKey", "b-nezzaluna-azzahra-2406495741_Modul-2-CI-CD-DevOps")
        property("sonar.organization", "B-Nezzaluna Azzahra-2406495741")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
    }
}