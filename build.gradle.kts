plugins {
	application
	kotlin("jvm") version "1.3.50"
	id("com.github.johnrengelman.shadow") version "5.1.0"
}

repositories {
	jcenter()
}

dependencies {
	implementation(kotlin("stdlib-jdk8", "1.3.50"))
}

application {
	mainClassName = "pl.pitcer.rammachine.BootstrapKt"
}

tasks {
	compileKotlin {
		kotlinOptions {
			apiVersion = "1.3"
			languageVersion = "1.3"
			jvmTarget = "12"
		}
	}
}
