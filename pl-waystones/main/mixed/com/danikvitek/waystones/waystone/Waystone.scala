package com.danikvitek.waystones.waystone

import org.bukkit.World
import org.jetbrains.annotations.{NotNull, Nullable}

final case class Waystone(x: Int, y: Int, z: Int, @NotNull name: String, @Nullable world: World)
