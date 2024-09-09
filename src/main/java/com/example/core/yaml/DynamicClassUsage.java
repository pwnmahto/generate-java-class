package com.example.core.yaml;

public class DynamicClassUsage {

    public static void main(String[] args) throws Exception {
        YamlParser parser = new YamlParser();
        Map<String, Object> yamlData = parser.parseYaml("person.yaml");

        DynamicClassGenerator generator = new DynamicClassGenerator();
        Class<?> dynamicClass = generator.generateClass("Person", (Map<String, Object>) yamlData.get("Person"));

        Object personInstance = dynamicClass.getDeclaredConstructor().newInstance();
        
        // Use reflection to set field values
        dynamicClass.getMethod("setName", String.class).invoke(personInstance, "John Doe");
        dynamicClass.getMethod("setAge", int.class).invoke(personInstance, 30);

        // Print field values using reflection
        System.out.println(dynamicClass.getMethod("getName").invoke(personInstance));
        System.out.println(dynamicClass.getMethod("getAge").invoke(personInstance));
    }
}
