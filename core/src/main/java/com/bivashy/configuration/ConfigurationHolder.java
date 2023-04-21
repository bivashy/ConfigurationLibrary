package com.bivashy.configuration;

/**
 * Class ConfigurationHolder need for multiple configuration classes, for example:
 *
 * <pre>
 * public class PluginConfig {
 *      public static final ConfigurationProcessor PROCESSOR = new DefaultConfigurationProcessor();
 *      &#64;ConfigField
 *      private Settings settingsConfiguration;
 *      public PluginConfig(ConfigurationSectionHolder holder){
 *          PROCESSOR.resolve(holder,this);
 *      }
 * }
 *
 * public class Settings implements ConfigurationHolder{
 *     &#64;ConfigField("secret")
 *     private int secretCode;
 *     public Settings(ConfigurationSectionHolder holder){
 *         PluginConfig.PROCESSOR.resolve(holder,this);
 *     }
 * }
 * </pre>
 * <p>
 * If field class implements ConfigurationHolder interface, ConfigurationHolderResolverFactory will call constructor with ConfigurationSectionHolder parameter.
 * (Only one argument on the constructor)
 */
public interface ConfigurationHolder {
}
