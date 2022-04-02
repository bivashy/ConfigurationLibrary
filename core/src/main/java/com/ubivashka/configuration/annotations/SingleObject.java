package com.ubivashka.configuration.annotations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.ubivashka.configuration.resolvers.ConfigurationFieldResolver;

/**
 * Mark field that resolves from single object, for example string. Also can be
 * used for list of section objects <br>
 * 
 * <pre>
 * <code>
 * public class Command{
 *     private String rawCommand;
 *     public Command(String command){
 *         this.rawCommand = command;
 *     }
 *     
 *     //getters and setters
 * }
 * //Optionally
 * public CommandFieldResolver implements ConfigurationFieldResolver<Command>{
 *     &#64;Override
 *     public Command resolveField(ConfigurationFieldResolverContext resolverContext){
 *     	   SingleObjectResolverContext singleObjectContext = resolverContext.as(SingleObjectResolverContext.class);
 *         String configurationValue = singleObjectContext.getConfigurationValue();
 *         return new Command(configurationValue);
 *     }
 * }
 *      // And field resolver register 
 * </code>
 * </pre>
 * 
 * <pre>
 * This is only works if object doesn`t has factory resolver, and has field resolver
 * {@link ConfigurationFieldResolver} like in example.
 * </pre>
 *
 */
@Retention(RUNTIME)
@Target(ElementType.FIELD)
public @interface SingleObject {

}
