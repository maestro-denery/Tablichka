package com.danikvitek

import com.danikvitek.waystone.misc.Waystone
import org.bukkit.Location

package object waystone {
  def waystoneToLocation(waystone: Waystone): Location =
    new Location(
      waystone.world,
      waystone.x, waystone.y, waystone.z
    )
}
