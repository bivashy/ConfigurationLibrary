package com.ubivashka.configuration.test;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import com.ubivashka.configuration.ConfigurationProcessor;
import com.ubivashka.configuration.contexts.defaults.SingleObjectResolverContext;
import com.ubivashka.configuration.holder.ConfigurationSectionHolder;
import com.ubivashka.configuration.holders.BukkitConfigurationHolder;
import com.ubivashka.configuration.processor.DefaultConfigurationProcessor;
import com.ubivashka.configuration.test.PersonTestConfiguration.Person;
import com.ubivashka.configuration.test.PersonTestConfiguration.PersonId;

public class TestMain extends JavaPlugin {
	private static final ConfigurationProcessor CONFIGURATION_PROCESSOR = new DefaultConfigurationProcessor()
			.registerConfigurationHolderWrapper(ConfigurationSection.class,
					configurationSection -> new BukkitConfigurationHolder(configurationSection))
			.registerFieldResolver(Person.class, (context) -> {
				ConfigurationSectionHolder section = context.configuration().getSection(context.path());
				if (section.as(BukkitConfigurationHolder.class).getSection() == null)
					return null;
				return new Person(section);
			}).registerFieldResolver(PersonId.class, (context) -> {
				SingleObjectResolverContext singleResolverContext = context.as(SingleObjectResolverContext.class);
				return new PersonId(singleResolverContext.getConfigurationValue());
			});

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
