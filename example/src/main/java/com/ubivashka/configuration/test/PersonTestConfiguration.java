package com.ubivashka.configuration.test;

import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import com.ubivashka.configuration.annotation.ConfigField;
import com.ubivashka.configuration.annotation.SectionObject;
import com.ubivashka.configuration.annotation.SingleObject;
import com.ubivashka.configuration.holder.ConfigurationSectionHolder;

public class PersonTestConfiguration {
	@SectionObject
	@ConfigField("section-test")
	private List<Person> personsList;
	@SectionObject
	@ConfigField("section-test.hi")
	private Person artem;

	@SingleObject
	@ConfigField("section-test.many")
	private List<PersonId> idObjects;
	@SingleObject
	@ConfigField("section-test.single")
	private PersonId idObject;

	public PersonTestConfiguration(ConfigurationSection section) {
		TestMain.getConfigurationProcessor().resolve(section, this);
		System.out.println(toString());
	}

	@Override
	public String toString() {
		return "PersonTestConfiguration [personsList=" + personsList + ", artem=" + artem + ", idObjects=" + idObjects
				+ ", idObject=" + idObject + "]";
	}

	public static class PersonId {
		private final String id;

		public PersonId(String id) {
			this.id = id;
		}

		@Override
		public String toString() {
			return "PersonId [id=" + id + "]";
		}

	}

	public static class Person {
		private final String name;
		private final String subName;
		private int age;

		private Person(String name, String subName, int age) {
			this.name = name;
			this.subName = subName;
			this.age = age;
		}

		public Person(ConfigurationSectionHolder personSection) {
			this(personSection.getString("name"), personSection.getString("subname"), personSection.getInteger("age"));
		}

		@Override
		public String toString() {
			return "Person [name=" + name + ", subName=" + subName + ", age=" + age + "]";
		}

	}
}
