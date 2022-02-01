package io.denery.entityregistry.behaviour.invoke

import io.denery.entityregistry.entity.CustomizableEntity

class BehaviourInvocationEvent:
  private var invocation: Option[(CustomizableEntity, CustomizableEntity => Unit)] = None

  def getInvocation(): Option[(CustomizableEntity, CustomizableEntity => Unit)] = invocation

  def setInvocation(invocation: (CustomizableEntity, CustomizableEntity => Unit)): Unit = this.invocation = Option(invocation)
  