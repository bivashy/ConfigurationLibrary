package com.bivashy.configuration.test;

import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import com.bivashy.configuration.annotation.ConfigField;

public class ListTestConfiguration {
	@ConfigField("string-list")
	private List<String> stringList;
	@ConfigField("double-list")
	private List<Double> doubleList;
	@ConfigField("integer-list")
	private List<Integer> integerList;

	public ListTestConfiguration(ConfigurationSection configurationSection) {
		TestMain.getConfigurationProcessor().resolve(configurationSection, this);
		stringList.forEach(string -> string.charAt(0));
		doubleList.forEach(number -> {
			number += 0.1;
		});
		integerList.forEach(number -> {
			number += 1;
		});
		System.out.println(toString());
	}

	@Override
	public String toString() {
		return "ListTestConfiguration [stringList=" + stringList + ", doubleList=" + doubleList + ", integerList="
				+ integerList + "]";
	}

}
