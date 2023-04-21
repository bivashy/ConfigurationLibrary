
package com.bivashy.configuration.test;

import org.bukkit.configuration.ConfigurationSection;

import com.bivashy.configuration.annotation.ConfigField;

public class PrimitiveTestConfiguration {

	@ConfigField("int")
	private int number;
	@ConfigField("int")
	private Integer wrappedNumber;
	@ConfigField("double")
	private double doubleNumber;
	@ConfigField("double")
	private Double wrappedDoubleNumber;
	@ConfigField("float")
	private float floatNumber;
	@ConfigField("float")
	private Float wrappedFloatNumber;
	
	public PrimitiveTestConfiguration(ConfigurationSection section) {
		TestMain.getConfigurationProcessor().resolve(section, this);
		System.out.println(toString());
	}

	@Override
	public String toString() {
		return "PrimitiveTestConfiguration [number=" + number + ", wrappedNumber=" + wrappedNumber + ", doubleNumber=" + doubleNumber + ", wrappedDoubleNumber="
				+ wrappedDoubleNumber + ", floatNumber=" + floatNumber + ", wrappedFloatNumber=" + wrappedFloatNumber + "]";
	}
}
