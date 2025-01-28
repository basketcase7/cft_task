package ru.task.service;

import ru.task.type.DataType;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DataTypeIdentifier {

    private boolean isInteger(String str) {
        try {
            new BigInteger(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isFloat(String str) {
        try {
            new BigDecimal(str);
            return str.contains(".");
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public DataType identifyDataType(String str) {
        if (isInteger(str)) {
            return DataType.INTEGER;
        } else if (isFloat(str)) {
            return DataType.DECIMAL;
        }
        return DataType.STRING;
    }
}
