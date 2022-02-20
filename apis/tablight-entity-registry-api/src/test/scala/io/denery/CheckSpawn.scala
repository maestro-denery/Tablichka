package io.denery

import io.denery.entityregistry.spawn.SpawnCategories
import org.junit.jupiter.api.Test

class CheckSpawn:

  import SpawnCategories._
  import SpawnCategories.SpawnSettings._
  import SpawnCategories.SpawnSettingsNode._
  def spawnSettings[A]: CommonSpawnSettingsDictionary[A] =
    Node("root",
      Settings("normal", DefaultSettings())
    )

  @Test
  def check(): Unit = {
    /*
    spawnSettings[Seq[DefaultSettings]].foreach(s => {
      println(s.delay)
    }) */
  }