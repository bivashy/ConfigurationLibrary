package com.ubivashka.configuration.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.ubivashka.configuration.resolver.field.ConfigurationFieldResolver;

/**
 * Mark field that resolves from section. Also can be used for list of section
 * objects <br>
 * 
 * <pre>
 * <code>
 * public class Person{
 *     private String name;
 *     private int age;
 *     public Person(String name,int age){
 *         this.name = name;
 *         this.age = age;
 *     }
 *     
 *     //getters and setters
 * }
 * 
 * public PersonFieldResolver implements ConfigurationFieldResolver<Person>{
 *     &#64;Override
 *     public Person resolveField(ConfigurationFieldResolverContext resolverContext){
 *         ConfigurationSectionHolder sectionHolder = context.configuration().getSection(context.path());
 *         return new Person(sectionHolder.getString("name"),sectionHolder.getInteger("age"));
 *     }
 * }
 *      // And field resolver register
 * </code>
 * </pre>
 * 
 * This is only works if object doesn`t have factory resolver and has
 * {@link ConfigurationFieldResolver} from configuration section
 *
 */
@Deprecated
@Retention(RUNTIME)
@Target(ElementType.FIELD)
public @interface SectionObject {

}
