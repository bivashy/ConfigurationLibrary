package com.bivashy.configuration.test;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import com.bivashy.configuration.ConfigurationProcessor;
import com.bivashy.configuration.holders.BukkitConfigurationHolder;
import com.bivashy.configuration.processor.DefaultConfigurationProcessor;
import com.bivashy.configuration.test.PersonTestConfiguration.PersonId;

public class TestMain extends JavaPlugin {
	private static final ConfigurationProcessor CONFIGURATION_PROCESSOR = new DefaultConfigurationProcessor()
			.registerConfigurationHolderWrapper(ConfigurationSection.class, BukkitConfigurationHolder::new)
			.registerFieldResolver(PersonId.class, (context) -> new PersonId(context.getString()));

	@Override
	public void onEnable() {
		saveDefaultConfig();
		new PrimitiveTestConfiguration(getConfig().getConfigurationSection("primitive-test"));
		new ListTestConfiguration(getConfig().getConfigurationSection("list-test"));
		new PersonTestConfiguration(getConfig());
		new EnumTestConfiguration(getConfig().getConfigurationSection("enum-test"));
	}

	public static ConfigurationProcessor getConfigurationProcessor() {
		return CONFIGURATION_PROCESSOR;
	}
}
