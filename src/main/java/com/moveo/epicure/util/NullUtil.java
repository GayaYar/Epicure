package com.moveo.epicure.util;

import com.moveo.epicure.exception.NullException;

public class NullUtil {

    public static void validate(Object obj, String name) {
        if (obj == null) {
            throw new NullException(name);
        }
    }

    public static void validate(Object obj) {
        if(obj==null) {
            throw new NullException();
        }
    }
}
