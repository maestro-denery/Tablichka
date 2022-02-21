package dev.tablight.entities.commands;

import com.destroystokyo.paper.event.brigadier.CommandRegisteredEvent;
import com.mojang.brigadier.arguments.StringArgumentType;

import dev.tablight.entities.EntitiesPlugin;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@SuppressWarnings("deprecation")
public class CommandListener implements Listener {
	private static CommandListener instance;
	public static CommandListener getInstance() {
		if (instance == null) instance = new CommandListener();
		return instance;
	}
	
	@EventHandler
	public void onCommandRegister(CommandRegisteredEvent<CommandSourceStack> e) {
		if (e.getCommand().getName().equals("tl-entities")) {
			EntitiesPlugin.logger.info("Command: " + e.getCommand().getName());
			EntitiesPlugin.logger.info("Command Label: " + e.getCommandLabel());
			var configuredERSpawn = e.getLiteral().createBuilder()
					.then(Commands.literal("spawn")
							.then(Commands.argument("id", StringArgumentType.word()).executes(context -> {
										EntitiesPlugin.logger.info("ID: " + context.getInput());
										return 0;
									})
							).build())
					.executes(context -> {
						EntitiesPlugin.logger.info("Input: " + context.getInput());
						return 0;
					})
					.build();
			e.setLiteral(configuredERSpawn);
		}
	}
}
