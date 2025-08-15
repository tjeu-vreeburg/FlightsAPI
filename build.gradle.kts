plugins {
    id("org.springframework.boot") version("3.5.4")
    id("io.spring.dependency-management") version("1.1.0")
    id("java")
}

springBoot {
    mainClass = "com.tjeuvreeburg.flightapi.FlightApiApplication"
}

group = "com.tjeuvreeburg.flightapi"
version = "1.0.11-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_21

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.5.4"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.h2database:h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testCompileOnly("org.mockito:mockito-core:+")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()

    doFirst {
        listOf("byte-buddy-agent", "mockito-inline").forEach { agentName ->
            classpath.find { it.name.contains(agentName) }?.let { agentJar ->
                jvmArgs("-javaagent:${agentJar.absolutePath}")
            }
        }
    }
}