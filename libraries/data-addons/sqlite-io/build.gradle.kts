dependencies {
    compileOnly(project(":library:data-addons"))
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")

    testImplementation(project(":library:data-addons"))
}
