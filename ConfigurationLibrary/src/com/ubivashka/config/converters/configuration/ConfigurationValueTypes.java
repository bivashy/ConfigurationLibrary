package com.ubivashka.config.converters.configuration;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public final class ConfigurationValueTypes {
	static enum ConfigurationTypes {
		STRING, DOUBLE, INTEGER, LONG, BOOLEAN, LIST, LOCATION, ITEMSTACK, COLOR, OFFLINE_PLAYER, CONFIGURATION_SECTION;
	}

	static enum StringConfigurationValue implements ConfigurationValue<String> {
		STRING;

		@Override
		public boolean isValid(ConfigurationSection section, String key) {
			return section.isString(key);
		}

		@Override
		public ConfigurationTypes getType() {
			return ConfigurationTypes.STRING;
		}
	}

	static enum DoubleConfigurationValue implements ConfigurationValue<Double> {
		DOUBLE;

		@Override
		public boolean isValid(ConfigurationSection section, String key) {
			return section.isDouble(key);
		}

		@Override
		public ConfigurationTypes getType() {
			return ConfigurationTypes.DOUBLE;
		}
	}

	static enum IntegerConfigurationValue implements ConfigurationValue<Integer> {
		INTEGER;

		@Override
		public boolean isValid(ConfigurationSection section, String key) {
			return section.isInt(key);
		}

		@Override
		public ConfigurationTypes getType() {
			return ConfigurationTypes.INTEGER;
		}
	}

	static enum LongConfigurationValue implements ConfigurationValue<Long> {
		LONG;

		@Override
		public boolean isValid(ConfigurationSection section, String key) {
			return section.isLong(key);
		}

		@Override
		public ConfigurationTypes getType() {
			return ConfigurationTypes.LONG;
		}
	}

	static enum BooleanConfigurationValue implements ConfigurationValue<Boolean> {
		BOOLEAN;

		@Override
		public boolean isValid(ConfigurationSection section, String key) {
			return section.isBoolean(key);
		}

		@Override
		public ConfigurationTypes getType() {
			return ConfigurationTypes.BOOLEAN;
		}
	}

	static enum ListConfigurationValue implements ConfigurationValue<List<?>> {
		LIST;

		@Override
		public boolean isValid(ConfigurationSection section, String key) {
			return section.isList(key);
		}

		@Override
		public ConfigurationTypes getType() {
			return ConfigurationTypes.LIST;
		}
	}

	static enum LocationConfigurationValue implements ConfigurationValue<Location> {
		LOCATION;

		@Override
		public boolean isValid(ConfigurationSection section, String key) {
			return section.isLocation(key);
		}

		@Override
		public ConfigurationTypes getType() {
			return ConfigurationTypes.LOCATION;
		}
	}

	static enum ItemStackConfigurationValue implements ConfigurationValue<ItemStack> {
		ITEMSTACK;

		@Override
		public boolean isValid(ConfigurationSection section, String key) {
			return section.isItemStack(key);
		}

		@Override
		public ConfigurationTypes getType() {
			return ConfigurationTypes.ITEMSTACK;
		}
	}

	static enum ColorConfigurationValue implements ConfigurationValue<Color> {
		COLOR;

		@Override
		public boolean isValid(ConfigurationSection section, String key) {
			return section.isColor(key);
		}

		@Override
		public ConfigurationTypes getType() {
			return ConfigurationTypes.COLOR;
		}
	}

	static enum OfflinePlayerConfigurationValue implements ConfigurationValue<OfflinePlayer> {
		OFFLINE_PLAYER;

		@Override
		public boolean isValid(ConfigurationSection section, String key) {
			return section.isOfflinePlayer(key);
		}

		@Override
		public ConfigurationTypes getType() {
			return ConfigurationTypes.OFFLINE_PLAYER;
		}
	}

	static enum ConfigurationSectionConfigurationValue implements ConfigurationValue<ConfigurationSection> {
		CONFIGURATION_SECTION;

		@Override
		public boolean isValid(ConfigurationSection section, String key) {
			return section.isConfigurationSection(key);
		}

		@Override
		public ConfigurationTypes getType() {
			return ConfigurationTypes.CONFIGURATION_SECTION;
		}
	}

	public static final StringConfigurationValue STRING = StringConfigurationValue.STRING;
	public static final DoubleConfigurationValue DOUBLE = DoubleConfigurationValue.DOUBLE;
	public static final IntegerConfigurationValue INTEGER = IntegerConfigurationValue.INTEGER;
	public static final LongConfigurationValue LONG = LongConfigurationValue.LONG;
	public static final BooleanConfigurationValue BOOLEAN = BooleanConfigurationValue.BOOLEAN;
	public static final ListConfigurationValue LIST = ListConfigurationValue.LIST;
	public static final LocationConfigurationValue LOCATION = LocationConfigurationValue.LOCATION;
	public static final ItemStackConfigurationValue ITEMSTACK = ItemStackConfigurationValue.ITEMSTACK;
	public static final ColorConfigurationValue COLOR = ColorConfigurationValue.COLOR;
	public static final OfflinePlayerConfigurationValue OFFLINE_PLAYER = OfflinePlayerConfigurationValue.OFFLINE_PLAYER;
	public static final ConfigurationSectionConfigurationValue CONFIGURATION_SECTION = ConfigurationSectionConfigurationValue.CONFIGURATION_SECTION;

	public static final ConfigurationValue<?>[] VALUES = { STRING, DOUBLE, INTEGER, LONG, BOOLEAN, LIST,
			LOCATION, ITEMSTACK, COLOR, OFFLINE_PLAYER, CONFIGURATION_SECTION };

	public static ConfigurationValue<?> getValidConfigurationValueType(ConfigurationSection configurationSection,
			String key) {
		return Arrays.stream(VALUES)
				.filter(configurationValueType -> configurationValueType.isValid(configurationSection, key)).findFirst()
				.orElse(null);
	}
}
