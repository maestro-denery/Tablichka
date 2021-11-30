package com.danikvitek.waystone.misc

import org.bukkit.World
import org.jetbrains.annotations.{NotNull, Nullable}

case class Waystone(x: Int, y: Int, z: Int, @NotNull name: String, @Nullable world: World)
