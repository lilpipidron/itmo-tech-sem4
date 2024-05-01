plugins {
    id("java")
}

group = "ru.kramskoi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation(project(":service"))
    implementation(project(":dao"))

    testImplementation("junit:junit:4.13.1")

    compileOnly("org.projectlombok:lombok")
    implementation("org.postgresql:postgresql:42.7.3")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:3.0.6")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
    implementation("javax.validation:validation-api:2.0.1.Final")


    testImplementation("org.springframework.boot:spring-boot-starter-test:3.0.6")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("org.projectlombok:lombok:1.18.22")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

    implementation("org.springframework.boot:spring-boot-starter-web:3.0.6")
    implementation("javax.persistence:javax.persistence-api:2.2")

    implementation("org.springframework.data:spring-data-jpa:3.2.2")

    implementation("org.springframework.boot:spring-boot-starter-security:3.0.6")

}

tasks.test {
    useJUnitPlatform()
}