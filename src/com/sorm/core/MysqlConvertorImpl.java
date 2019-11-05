package com.sorm.core;

public class MysqlConvertorImpl implements  TypeConvertor {
    @Override
    public String dataToJava(String type) {
        switch(type)
        {
            case "VARCHAR":return "String";
            case "CHAR":return "String";
            case "INT":return "Integer";
            case "TINYINT":return "Integer";
            case "SMALLINT":return "Integer";
            case "INTEGER":return "Integer";
            case "BIGINT":return "Long";
            case "DOUBLE":return "Double";
            case "FLOAT":return "Float";
            case "CLOB" :return "java.sql.Clob";
            case "BLOB" :return "java.sql.Blob";
            case "DATE" :return "java.sql.Date";
            case "TIMESTAMP" :return "java.sql.Timestamp";
            case "TEXT":return "java.sql.Clob";
            case "BIT":return "Integer";
            case "MEDIUMBLOB": return "java.sql.Blob";
        }
        return null;
    }

    @Override
    public String JavaToData(String type) {
        return null;
    }


}
