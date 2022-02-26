package dev.tablight.test.registries;

import dev.tablight.common.base.registry.annotation.group.Holder;
import dev.tablight.common.base.registry.holder.ConcurrentRegistrableHolder;

@Holder("dummyGroup")
public class DummyHolder extends ConcurrentRegistrableHolder {}
