package com.moveo.epicure.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperSingleton {
    private static ObjectMapper objectMapper;

    private ObjectMapperSingleton(){
    }

    public static ObjectMapper get() {
        if(objectMapper == null) {
            synchronized (ObjectMapperSingleton.class) {
                if (objectMapper == null) {
                    objectMapper = new ObjectMapper();
                }
            }
        }
        return objectMapper;
    }
}
