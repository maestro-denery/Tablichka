package io.denery

import io.denery.entityregistry.spawn.SpawnCategories
import org.junit.Test

class CheckSpawn:

  import SpawnCategories._
  import SpawnCategories.SpawnSettings._
  import SpawnCategories.SpawnSettingsNode._
  def spawnSettings[A]: CommonSpawnSettingsDictionary[A] =
    Node("root",
      MaximumLight(15),
      MinimumLight(10),
      MaximumPerChunk(20)
    )

  @Test
  def check(): Unit = {

  }