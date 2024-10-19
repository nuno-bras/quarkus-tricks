import com.diffplug.gradle.spotless.SpotlessExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.time.LocalDate

group = "bras.casa"
version = "1.0.0-SNAPSHOT"
val quarkusVersion: String by project

plugins {
    kotlin("jvm") version "2.0.10"
    kotlin("plugin.allopen") version "2.0.10"
    id("io.quarkus") apply false
    id("com.diffplug.spotless") version "6.25.0" apply false
}

repositories {
    mavenCentral()
    mavenLocal()
}

subprojects {
    apply(plugin = "io.quarkus")
    apply(plugin = "kotlin")
    apply(plugin = "org.jetbrains.kotlin.plugin.allopen")
    apply(plugin = "com.diffplug.spotless")
    apply(plugin = "jacoco")

    repositories {
        mavenCentral()
        mavenLocal()
    }

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
        implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:${quarkusVersion}"))
        implementation("io.quarkus:quarkus-rest-jackson")
        implementation("io.quarkus:quarkus-kotlin")
        implementation("io.quarkus:quarkus-arc")
        implementation("io.quarkus:quarkus-rest")
        implementation("io.quarkus:quarkus-hibernate-orm")
        implementation("io.quarkus:quarkus-jdbc-postgresql")

        testImplementation("io.quarkus:quarkus-junit5")
        testImplementation("io.rest-assured:rest-assured")
        testImplementation("io.quarkus:quarkus-test-h2")
    }

    allOpen {
        annotation("jakarta.ws.rs.Path")
        annotation("jakarta.enterprise.context.ApplicationScoped")
        annotation("jakarta.persistence.Entity")
        annotation("io.quarkus.test.junit.QuarkusTest")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    tasks {
        withType<KotlinCompile> {

            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_21)
                javaParameters.set(true)
            }
        }

        withType<Test> {
            systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
            useJUnitPlatform()
        }
    }

    configure<SpotlessExtension> {
        val year = LocalDate.now().year
        val header = "/**\n * Development by Nuno Brás, $year \n */"

        kotlin {
            ktfmt().kotlinlangStyle().configure {
                it.setRemoveUnusedImport(true)
            }
            licenseHeader(header)
            trimTrailingWhitespace()
            endWithNewline()
        }
    }
}

