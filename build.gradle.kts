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
    implementation(project(":api-gateway"))
    implementation(project(":cat-client"))
    implementation(project(":owner-client"))
    implementation(project(":owner"))
    implementation(project(":cat"))
    implementation(project(":domain"))

    testImplementation(platform("org.junit:junit-bom:5.8.2"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    testImplementation("com.h2database:h2:2.2.224")

    implementation("org.hibernate.orm:hibernate-core:6.2.5.Final")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.0.6")
    implementation("jakarta.persistence:jakarta.persistence-api:2.2")

    testImplementation("org.springframework:spring-test:6.0.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.0.6")
    testImplementation("org.springframework.boot:spring-boot-test-autoconfigure:3.0.6")

    implementation("org.springframework:spring-web:6.0.0")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")

    implementation("jakarta.servlet:jakarta.servlet-api:6.1.0-M2")

    testImplementation("org.hamcrest:hamcrest:2.2")

    testImplementation("org.springframework.security:spring-security-test:6.0.0")
}

tasks.test {
    useJUnitPlatform()
}
