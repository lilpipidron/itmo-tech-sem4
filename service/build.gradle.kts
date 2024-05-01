plugins {
    java
}

group = "ru.kramskoi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation(project(":dao"))

    implementation("org.springframework.boot:spring-boot-starter-validation:3.0.6")
    testImplementation("junit:junit:4.13.1")

    compileOnly("org.projectlombok:lombok:1.18.22")
    implementation("org.postgresql:postgresql:42.7.3")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:3.0.6")
    annotationProcessor("org.projectlombok:lombok:1.18.22")

    testImplementation("org.springframework.boot:spring-boot-starter-test:3.0.6")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

    implementation("org.springframework.data:spring-data-jpa:3.0.6")

    implementation("jakarta.validation:jakarta.validation-api:3.0.2")



    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:3.0.6")
    implementation("org.springframework.boot:spring-boot-starter-security:3.0.6")
    implementation("at.favre.lib:bcrypt:0.9.0")
}

tasks.test {
    useJUnitPlatform()
}
