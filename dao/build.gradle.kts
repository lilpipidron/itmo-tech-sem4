plugins {
    id("java")
}

group = "ru.kramskoi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.0.6")
    implementation("org.springframework.boot:spring-boot-starter-web:3.0.6")
    testImplementation("junit:junit:4.13.1")

    implementation("org.hibernate.orm:hibernate-core:6.2.5.Final")

    implementation("org.postgresql:postgresql:42.7.3")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:3.0.6")

    testImplementation("org.springframework.boot:spring-boot-starter-test:3.0.6")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

    implementation("javax.persistence:javax.persistence-api:2.2")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.0.6")

    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")



}

tasks.test {
    useJUnitPlatform()
}