# ConfigurationLibrary

![Java](https://img.shields.io/badge/Java-8%2B-brightgreen)
[![Jitpack version](https://jitpack.io/v/U61vashka/ConfigurationLibrary.svg)](https://jitpack.io/#U61vashka/ConfigurationLibrary)

### Load configuration files using annotations.

**Features:**
1. Load primitives, box them, and convert among themselves (Float -> Double, String -> Number, etc.)
```
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
```
---
2. Custom resolvers, and resolver factory
> If you need to resolve just from string
```
ConfigurationProcessor#registerFieldResolver(MyClass.class, (context) -> new MyClass(context.getString()));
```
> If you need to resolve from configuration section:
```
public class MySectionClass implements ConfigurationHolder { // Do not not to be confused with ConfigurationSectionHolder 
  private final String myString;
  public MySectionClass(ConfigurationSectionHolder section) { // Important constructor
    this.myString = section.getString("my","path","to","string"); 
  }

  // And so on
}
```
> If you need to resolve interface, or something
```
ConfigurationProcessor#.registerFieldResolverFactory(MyInterface.class, (factoryContext) -> {
  String type = factoryContext.getString("default"); // If no string will be provided, "default" value be used
  switch(type){
    case "default":
      return (context) -> new MyInterfaceDefaultImpl(context.getString());
    case "other"
      return (context) -> new MyInterfaceOtherImpl(context.getString());
  }
  return (context) -> null; // Return resolver with null return
});
```
---
3. Easy collections resolving.
- Register resolver

  ```
  ConfigurationProcessor#registerFieldResolver(StringContainer.class,(context) -> new StringContainer(context.getString()));
  ```

- Define our configuration (For example YAML)

  example-list:
    - 'test'
    - 'hi'
    - 1 # Library will convert this number to String

- Create ConfigurationProcessor and ConfigurationSectionHolder (For example Bukkit configuration processor, and Bukkit ConfigurationSectionHolder)

  ```
   public class Configuration{
    private final ConfigurationProcessor processor = new BukkitConfigurationProcessor(); // We also can use DefaultConfigurationProcessor, but BukkitConfigurationProcessor has ConfigurationSection to ConfigurationSectionHolder wrapper
    
    @ConfigField("example-list")
    private List<StringContainer> stringContainers;

    public Confiugration(ConfigurationSection section) { // Out bukkit configuration
      processor.resolve(new BukkitConfigurationHolder(section),this); // Resolve all our @ConfigField annotated fields
      System.out.println(stringContainers); // ["test","hi","1"]
    }
   }
  ```
> In short it works using context object swapping.

### Usage
**Maven**
```
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependency>
  <groupId>com.github.U61vashka.ConfigurationLibrary</groupId>
  <artifactId>[MODULE]</artifactId> 
  <version>[VERION-HERE]</version>
</dependency>
```
**Gradle**
```
allprojects {
    repositories {
      ...
      maven { url 'https://jitpack.io' }
    }
  }

dependencies {
    implementation 'com.github.U61vashka.ConfigurationLibrary:[MODULE]:[VERSION-HERE]'
}
```
    