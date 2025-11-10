plugins {
	java
	id("org.springframework.boot") version "3.5.7"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.ford"
version = "0.0.1-SNAPSHOT"
description = "Project for Schedule and Delivery Module"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// ============================================
	// Spring Boot Starters
	// ============================================
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")

	// ============================================
	// H2 Database (In-Memory Database)
	// ============================================
	implementation("com.h2database:h2")

	// ============================================
	// Lombok (Optional - Reduces Boilerplate Code)
//	// ============================================
//	compileOnly("org.projectlombok:lombok")
//	annotationProcessor("org.projectlombok:lombok")

	// ============================================
	// OpenAPI/Swagger Documentation
	// ============================================
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

	// ============================================
	// Development Tools
	// ============================================
	developmentOnly("org.springframework.boot:spring-boot-devtools")



	// ============================================
	// Testing
	// ============================================
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	//========================
	// Email
	//======================
	implementation("org.springframework.boot:spring-boot-starter-mail")


}

tasks.withType<Test> {
	useJUnitPlatform()
}
