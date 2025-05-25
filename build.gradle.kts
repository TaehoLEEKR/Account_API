plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.5"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.kotlinprc"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
    // 모든 의존성에서 충돌되는 로깅 라이브러리 제외
    all {
        exclude(group = "org.slf4j", module = "slf4j-simple")
        exclude(group = "commons-logging", module = "commons-logging")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // logger 사용
    implementation("io.github.microutils:kotlin-logging:3.0.5")

    // 분산락을 위한 redisson
    implementation("org.redisson:redisson:3.22.0")
    // 임베디드 레디스
//    implementation("it.ozimov:embedded-redis:0.7.3")
    implementation("com.github.kstyrc:embedded-redis:0.6")

    implementation("org.jetbrains.kotlin:kotlin-reflect")

    compileOnly("org.projectlombok:lombok")

    runtimeOnly("com.h2database:h2")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    // mokito 말고 mockk 사용
    testImplementation("io.mockk:mockk:1.13.8")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}