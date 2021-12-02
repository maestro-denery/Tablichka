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

  case class DefaultSettings(delay: Int = 100, maximumPerChunk: Int = 20, maximumLight: Int = 15, minimumLight: Int = 0)

  trait SpawnSettingsAlgebra[A]:
    def defaultSettings(id: String, settings: DefaultSettings): A

  trait SpawnSettingsNodeAlgebra[A]:
    def node(id: String, node: Seq[A]): A

  import tofu.syntax.monadic._
  import tofu.syntax.raise._
  import tofu.Raise

  case class SpawnError(message: String = "Cannot spawn entity!") extends Throwable:
    override def getMessage: String = message

  type SpawnSettings = [A] => SpawnSettingsAlgebra[A] ?=> A
  type SpawnSettingsNode = [A] => SpawnSettingsNodeAlgebra[A] ?=> A

  type SpawnSettingsDictionary[A] = SpawnSettingsAlgebra[A] ?=> A
  type SpawnSettingsNodeDictionary[A] = SpawnSettingsNodeAlgebra[A] ?=> A
  type CommonSpawnSettingsDictionary[A] = (SpawnSettingsNodeAlgebra[A], SpawnSettingsAlgebra[A]) ?=> A

  object SpawnSettings:
    def Settings[A](name: String, settings: DefaultSettings): SpawnSettingsDictionary[A] =
      (alg: SpawnSettingsAlgebra[A]) ?=> alg.defaultSettings(name, settings)

  object SpawnSettingsNode:
    def Node[A](id: String, node: A*): SpawnSettingsNodeDictionary[A] =
      (alg: SpawnSettingsNodeAlgebra[A]) ?=> alg.node(id, node);

  given SpawnSettingsNodeAlgebra[Seq[DefaultSettings]] with
    override def node(id: String, node: Seq[Seq[DefaultSettings]]): Seq[DefaultSettings] = node.flatten

  trait SpawnAlgebra[F[_]: Monad]:
    def spawn(settings: CommonSpawnSettingsDictionary[Int],
              server: Server,
              locationNear: Location,
              list: List[AbstractCustomizableEntityType])(using raise: Raise[F, SpawnError]): F[LivingEntity]

  trait SpawnNodeAlgebra[F[_]: Monad]:
    def node(node: Seq[F[LivingEntity]]): F[LivingEntity]

  type SpawnDictionary[F[_]] = SpawnAlgebra[F] ?=> F[LivingEntity]
  type SpawnNodeDictionary[F[_]] = SpawnNodeAlgebra[F] ?=> F[LivingEntity]
  type CommonSpawnDictionary[F[_]] = (SpawnNodeAlgebra[F], SpawnAlgebra[F]) ?=> F[LivingEntity]

  object SpawnEngine:
    def Spawn[F[_]: Monad](settings: CommonSpawnSettingsDictionary[Int],
                    server: Server,
                    locationNear: Location,
                    list: List[AbstractCustomizableEntityType])
                          (using raise: Raise[F, SpawnError]): SpawnDictionary[F] =
      (alg: SpawnAlgebra[F]) ?=> alg.spawn(settings, server, locationNear, list)

  object SpawnEngineNode:
    def Node[F[_]: Monad](node: F[LivingEntity]*): SpawnNodeDictionary[F] =
      (alg: SpawnNodeAlgebra[F]) ?=> alg.node(node)

  given [F[_]: Monad]: SpawnAlgebra[F] with
    override def spawn(settings: CommonSpawnSettingsDictionary[Int],
                       server: Server,
                       locationNear: Location,
                       list: List[AbstractCustomizableEntityType])(using raise: Raise[F, SpawnError]): F[LivingEntity] = {

      raise.raise(SpawnError())
    }
