package io.denery.behaviour;

import org.bukkit.entity.LivingEntity;

import java.util.Map;
import java.util.OptionalInt;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Behaviour implements BiConsumer<String, LivingEntity> {
    private final Map<String, Consumer<LivingEntity>> actions;
    public Behaviour(Map<String, Consumer<LivingEntity>> actions) {
        this.actions = actions;
    }

    public void acceptRandom(LivingEntity entity) {
        Random random = new Random();
        OptionalInt rand = random.ints(0, actions.size() + 1).findFirst();
        if (rand.isEmpty()) throw new NullPointerException("Cannot get random value in range!");
        Consumer<LivingEntity>[] funcs = (Consumer<LivingEntity>[]) actions.values().toArray();
        funcs[rand.getAsInt()].accept(entity);
    }

    @Override
    public void accept(String s, LivingEntity entity) {
        actions.get(s).accept(entity);
    }
}

