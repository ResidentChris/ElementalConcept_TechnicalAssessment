plugins {
	java
	id("org.springframework.boot") version "3.4.1"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "scot.chriswalker.elemental_concept"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

val cucumberVersion = "7.20.1"
val h2Version = "2.3.232"
val jsonUnitVersion = "4.1.0"
val junitVersion = "5.11.4"
val wiremockVersion = "3.4.0"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")

	testImplementation("org.springframework.boot:spring-boot-starter-test")

	testImplementation(platform("org.junit:junit-bom:$junitVersion"))
	testImplementation("org.junit.platform:junit-platform-suite")

	testImplementation(platform("io.cucumber:cucumber-bom:$cucumberVersion"))
	testImplementation("io.cucumber:cucumber-java")
	testImplementation("io.cucumber:cucumber-junit-platform-engine")
	testImplementation("io.cucumber:cucumber-spring")

	testImplementation("com.h2database:h2:$h2Version")
	testImplementation("net.javacrumbs.json-unit:json-unit-assertj:$jsonUnitVersion")
	testImplementation("org.wiremock.integrations:wiremock-spring-boot:$wiremockVersion")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
