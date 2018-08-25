apply plugin: 'java'
apply plugin: 'application'
apply plugin: 'checkstyle'
apply plugin: 'findbugs'
apply plugin: 'jacoco'
apply plugin: 'maven'

apply from: 'config/scripts/coverage.gradle'
apply from: 'config/scripts/idea.gradle'

group = '${groupName}'
version = '${version}'

sourceCompatibility = JavaVersion.VERSION_1_8
targetCompatibility = JavaVersion.VERSION_1_8

project.ext {
    guavaVersion = '25.1-jre'
    guiceVersion = '4.0'
    slf4jVersion = '1.7.25'
    logbackVersion = '1.2.3'
    junitVersion = '4.12'
    mockitoVersion = '2.20.1'
}

project.dependencies {
    compile(
            "com.google.guava:guava:$guavaVersion",
            "com.google.inject:guice:$guiceVersion",
            "org.slf4j:slf4j-api:$slf4jVersion",
    )

    runtime(
            "ch.qos.logback:logback-classic:$logbackVersion"
    )

    testCompile(
            "junit:junit:$junitVersion",
            "org.mockito:mockito-core:$mockitoVersion"
    )
}

checkstyleTest {
    enabled = false
}

findbugs {
    toolVersion = "3.0.1"
}

findbugsTest {
    enabled = false
}

jacoco {
    toolVersion = "0.8.1"
}

check.dependsOn "coverageCheck"

tasks.withType(JavaCompile) {
    options.compilerArgs << "-Xlint:unchecked"
    options.compilerArgs << "-Xlint:deprecation"
}

task wrapper(type: Wrapper) {
    gradleVersion = '4.9'
}