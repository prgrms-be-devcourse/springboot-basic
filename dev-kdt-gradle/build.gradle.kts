plugins {
    java
    application
}

group = "org.prgrms"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}
dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}
tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
tasks.compileJava {
    options.isFork = true
}
application {
    mainClass.set("org.prgrms.kdt.App")
}
