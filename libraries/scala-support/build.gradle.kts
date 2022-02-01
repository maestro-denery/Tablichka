dependencies {
    implementation("tf.tofu:tofu-core_2.13:0.10.3")
    implementation("org.scala-lang:scala3-library_3:3.1.0")
}

bukkit {
    name = "ScalaSupport"
    version = "a1.0"
    main = "org.foton.scalasupport.ScalaLauncher"
    apiVersion = "1.17"
    authors = listOf("Denery")
}
