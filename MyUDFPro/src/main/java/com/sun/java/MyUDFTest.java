package com.sun.java;


import org.apache.hadoop.hive.ql.exec.UDF;

public class MyUDFTest extends UDF {


    public int  evaluate(String str){
        if (str == null){
            return  0;
        }else {

            return  str.length();
        }

    }

}
