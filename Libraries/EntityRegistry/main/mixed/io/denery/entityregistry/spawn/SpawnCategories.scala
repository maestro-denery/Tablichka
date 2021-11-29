package io.denery.entityregistry.spawn

import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.Server
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import cats.Monad
import io.denery.entityregistry.entity.AbstractCustomizableEntityType

object SpawnCategories:
  trait SpawnSettingsAlgebra[A]:
    def delay(delay: Int): A
    def maximumPerChunk(max: Int): A
    def maximumLight(max: Int): A
    def minimumLight(min: Int): A

  trait SpawnSettingsNodeAlgebra[A]:
    def node(id: String, node: Seq[A]): A

  import tofu.syntax.monadic._
  import tofu.syntax.raise._
  import tofu.Raise

  case class SpawnError() extends Throwable

  type SpawnSettings = [A] => SpawnSettingsAlgebra[A] ?=> A
  type SpawnSettingsNode = [A] => SpawnSettingsNodeAlgebra[A] ?=> A

  type SpawnSettingsDictionary[A] = SpawnSettingsAlgebra[A] ?=> A
  type SpawnSettingsNodeDictionary[A] = SpawnSettingsNodeAlgebra[A] ?=> A
  type CommonSpawnSettingsDictionary[A] = (SpawnSettingsNodeAlgebra[A], SpawnSettingsAlgebra[A]) ?=> A

  object SpawnSettings:
    def Delay[A](delay: Int = 100): SpawnSettingsDictionary[A] =
      (alg: SpawnSettingsAlgebra[A]) ?=> alg.delay(delay)

    def MaximumPerChunk[A](max: Int = 20): SpawnSettingsDictionary[A] =
      (alg: SpawnSettingsAlgebra[A]) ?=> alg.maximumPerChunk(max)

    def MaximumLight[A](max: Int = 15): SpawnSettingsDictionary[A] =
      (alg: SpawnSettingsAlgebra[A]) ?=> alg.maximumLight(max)

    def MinimumLight[A](min: Int = 0): SpawnSettingsDictionary[A] =
      (alg: SpawnSettingsAlgebra[A]) ?=> alg.minimumLight(min)

  object SpawnSettingsNode:
    def Node[A](id: String, node: A*): SpawnSettingsNodeDictionary[A] =
      (alg: SpawnSettingsNodeAlgebra[A]) ?=> alg.node(id, node);

  given SpawnSettingsNodeAlgebra[Seq[Int]] with
    override def node(id: String, node: Seq[Seq[Int]]): Seq[Int] = node.flatten

  //TODO: Fix compile error on using context bounds related to Misc subproject.

  object SpawnEngine:
    def spawn[F[_]: Monad](settings: CommonSpawnSettingsDictionary[Int],
                    server: Server,
                    locationNear: Location,
                    list: List[AbstractCustomizableEntityType])
                          (using raise: Raise[F, SpawnError]): F[LivingEntity] = {

      raise.raise(SpawnError())
    }

    //def spawnNear[F[_]: Monad](server: Server, locationNear: Location): Raise[F, SpawnError] =
