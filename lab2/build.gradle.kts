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

    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    testCompileOnly("org.projectlombok:lombok:1.18.30")

    implementation(project(":Service"))
    implementation(project(":DAO"))

    implementation("org.hibernate:hibernate-core:5.4.32.Final")
    implementation("org.postgresql:postgresql:42.7.0")
    implementation("org.flywaydb:flyway-core:7.15.0")

    implementation("org.hibernate:hibernate-core:5.4.32.Final")
}

tasks.test {
    useJUnitPlatform()
}
