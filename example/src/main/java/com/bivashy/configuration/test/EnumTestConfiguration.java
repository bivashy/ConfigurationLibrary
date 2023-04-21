package com.bivashy.configuration.test;

import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import com.bivashy.configuration.annotation.ConfigField;

public class EnumTestConfiguration {
	@ConfigField("first-enum")
	private TestEnum firstEnum;
	@ConfigField("enum-list")
	private List<TestEnum> enumList;

	public EnumTestConfiguration(ConfigurationSection section) {
		TestMain.getConfigurationProcessor().resolve(section, this);
		enumList.forEach(Enum::name);
		System.out.println(toString());
	}

	@Override
	public String toString() {
		return "EnumTestConfiguration [firstEnum=" + firstEnum + ", enumList=" + enumList + "]";
	}

	public enum TestEnum {
		FIRST, SECOND, THIRD, FOURTH
	}

}
