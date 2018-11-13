package com.softserve.presentations.ORM;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${JDEEK} on ${11.11.2018}.
 */
public class TableCreator {

    public static void main(String[] args) throws Exception{

        if(args.length < 1){
            System.out.println("arguments: annotated classes");
            System.exit(0);
        }

        for (String className : args){
            Class<?> c1 = Class.forName(className);
            DBTable dbTable = c1.getAnnotation(DBTable.class);
            if (dbTable == null){
                System.out.println("No DBTable annotations in class"+ className);
                continue;
            }

            String tableName = dbTable.name();

            //Если имени нет => Использовать имя Class
            if (tableName.length() < 1)
                tableName = c1.getName().toUpperCase();
                List<String> columnDefs = new ArrayList<>();

                for (Field field: c1.getDeclaredFields()){
                    String columnName = null;
                    Annotation[] anns = field.getDeclaredAnnotations();

                    if (anns.length < 1)
                        continue; // Не является столбцом таблицы БД
                    if (anns[0] instanceof SQLInteger){
                        SQLInteger sInt = (SQLInteger) anns[0];
                        //Использовать имя поля если имя не указано
                        if (sInt.name().length() < 1)
                            columnName = field.getName().toUpperCase();
                        else
                            columnName = sInt.name();
                        columnDefs.add(columnName+ " INT "+getConstraints(sInt.constraints()));
                    }
                    if (anns[0] instanceof SQLString){
                        SQLString sStirng = (SQLString) anns[0];
                        //Использовать имя поля если имя не указано
                        if (sStirng.name().length() < 1)
                            columnName = field.getName().toUpperCase();
                        else
                            columnName = sStirng.name();
                        columnDefs.add(columnName+ " VARCHAR( "+sStirng.value()+")"+ getConstraints(sStirng.constraints()));
                    }

                    StringBuilder createCommand = new StringBuilder(
                            "CREATE TABLE "+tableName + "(");
                    for (String columnDef: columnDefs)
                        createCommand.append("\n      "+columnDef +",");
                        //Удалить завершающую запятую
                        String tableCreate = createCommand.substring(0,createCommand.length()-1) +");";
                        System.out.println("Table creation SQL for"+ className+ "is :\n"+ tableCreate);
                }
            }
        }

        private static String getConstraints(Constraints con){
            String constraints = "";

            if (!con.allowNull())
                constraints += "NOT NULL";
            if (con.primaryKey())
                constraints += " PRIMARY KEY";
            if (con.unique())
                constraints += "UNIQUE";
            return constraints;

        }
    }
