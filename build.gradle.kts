plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")

    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    testCompileOnly("org.projectlombok:lombok:1.18.30")

    implementation("org.hibernate:hibernate-core:5.4.32.Final")
    implementation("org.postgresql:postgresql:42.7.0")
    testImplementation("com.h2database:h2:2.2.224")

    implementation("org.hibernate:hibernate-core:5.4.32.Final")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.3")
    testImplementation("org.mockito:mockito-core:4.0.0")

    implementation(project("controller"))
    implementation(project("service"))
    implementation(project("dao"))

    testImplementation("org.springframework:spring-test:6.1.6")

    testImplementation("org.springframework.boot:spring-boot-test:3.2.4")

    implementation("javax.persistence:javax.persistence-api:2.2")
    testImplementation("org.springframework.boot:spring-boot-test-autoconfigure:3.2.4")
    implementation("org.springframework:spring-web:5.3.10")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")
}

tasks.test {
    useJUnitPlatform()
}
