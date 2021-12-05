package io.denery

import io.denery.entityregistry.behaviour.BehaviourCategories._
import io.denery.entityregistry.entity.CustomizableEntity
import org.junit.Test

class CheckBehaviour:

  import io.denery.entityregistry.behaviour.BehaviourCategories.BehaviourNodeDictionary._
  import io.denery.entityregistry.behaviour.BehaviourCategories.BehaviourActionDictionary._

  def sample[A]: CommonBehaviourDictionary[A] =
    ActionNode("root",
      Action("a", s => {
        
      }),
      ActionNode("sub-a",
        Action("b", s => {
          
        }),
        Action("c", s => {
          
        })
      )
    )

  
  
  @Test
  def check: Unit = {
    sample[Seq[CustomizableEntity[_] => Unit]].foreach(s => {
      //Randomizer.applyRandom(s, 50)
    })
  }
