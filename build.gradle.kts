plugins {
    java
    application
    `jvm-test-suite`
}

application {
    applicationDefaultJvmArgs = listOf("--enable-preview")
    mainClass = "Main"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(22)
    }
}

tasks {
    withType<JavaCompile> {
        options.compilerArgs.add("--enable-preview")
        options.encoding = "UTF-8"
    }
}

testing {
    suites {
        named<JvmTestSuite>("test") {
            useJUnitJupiter()
        }
    }
}
