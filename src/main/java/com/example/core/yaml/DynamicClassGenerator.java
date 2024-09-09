package com.example.core.yaml;

import javassist.*;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

public class DynamicClassGenerator {

    public static Class<?> generateClass(String className, Map<String, Object> fields) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass(className);

        for (Map.Entry<String, Object> field : fields.entrySet()) {
            CtField ctField = new CtField(pool.get(field.getValue().getClass().getName()), field.getKey(), cc);
            ctField.setModifiers(Modifier.PRIVATE);
            cc.addField(ctField);

            // Create getter method
            cc.addMethod(CtNewMethod.getter("get" + capitalize(field.getKey()), ctField));
            // Create setter method
            cc.addMethod(CtNewMethod.setter("set" + capitalize(field.getKey()), ctField));
        }

        return cc.toClass();
    }

    private static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
