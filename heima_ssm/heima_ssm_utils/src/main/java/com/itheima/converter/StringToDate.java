package com.itheima.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDate implements Converter<String,Date> {
    @Override
    public Date convert(String source) {
        if (source == null){
            throw  new  RuntimeException("传入的数据有误");
        }

        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            return sm.parse(source);
        } catch (ParseException e) {
            throw  new RuntimeException("数据转换异常");
        }
    }
}
