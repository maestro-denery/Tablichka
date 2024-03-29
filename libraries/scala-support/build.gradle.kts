apply(plugin = "scala")

dependencies {
    implementation("tf.tofu:tofu-core_2.13:0.10.3")
    implementation("org.scala-lang:scala3-library_3:3.1.0")
}

bukkit {
    name = "scala-support-lib"
    version = "a1.0"
    description = "Plugin adding support for scala plugins via shading scala standard library."
    main = "org.foton.scalasupport.ScalaLauncher"
    apiVersion = "1.18"
    authors = listOf("Denery")
}
