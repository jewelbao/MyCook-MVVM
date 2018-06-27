package com.jewel;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.Arrays;
import java.util.List;

/**
 * greenDao List<String>字段转换工具。
 * @author Jewel
 * @version 1.0
 * @since 2017/11/1 0001
 */

public class ListStringConverter implements PropertyConverter<List<String>, String> {

    private String separatorChar = ",";

    @Override
    public List<String> convertToEntityProperty(String databaseValue) {
        if(databaseValue == null) {
            return null;
        } else {
            return Arrays.asList(databaseValue.split(separatorChar));
        }
    }

    @Override
    public String convertToDatabaseValue(List<String> entityProperty) {
        if(entityProperty == null) {
            return null;
        } else {
            StringBuilder builder = new StringBuilder();
            boolean split = false;
            for(String item : entityProperty) {
                if(split) {
                    builder.append(separatorChar);
                }
                builder.append(item);
                split = true;
            }
            return builder.toString();
        }
    }
}
