package com.ubivashka.configuration.test;

import org.bukkit.configuration.ConfigurationSection;

import com.ubivashka.configuration.annotations.ConfigField;

public class PrimitiveTestConfiguration {

	@ConfigField("int")
	private int number;
	@ConfigField("int")
	private Integer wrappedNumber;
	@ConfigField("double")
	private double doubleNumber;
	@ConfigField("double")
	private Double wrappedDoubleNumber;

	public PrimitiveTestConfiguration(ConfigurationSection section) {
		TestMain.getConfigurationProcessor().resolve(section, this);
		System.out.println(toString());
	}

	@Override
	public String toString() {
		return "PrimitiveTestConfiguration [number=" + number + ", wrappedNumber=" + wrappedNumber + ", doubleNumber="
				+ doubleNumber + ", wrappedDoubleNumber=" + wrappedDoubleNumber + "]";
	}
}
