plugins {
    java
}

group = "ru.kramskoi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(20)
    }
}

dependencies {
    implementation(project(":owner-client"))
    implementation(project(":domain"))

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("junit:junit:4.13.1")

    implementation("org.springframework.boot:spring-boot-starter-validation:3.0.6")
    implementation("org.springframework.boot:spring-boot-starter-test:3.0.6")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.0.6")
    implementation("org.springframework.boot:spring-boot-starter-security:3.0.6")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:3.0.6")

    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
    implementation("org.projectlombok:lombok:1.18.22")

    implementation("com.fasterxml.jackson.core:jackson-annotations:2.2.1")
    implementation("at.favre.lib:bcrypt:0.9.0")
}

tasks.test {
    useJUnitPlatform()
}
