package cn.jiweiqing.base.config.param;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class String2DateConverter implements Converter<String, Date> {

    private SimpleDateFormat dateFormatFull = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat dateFormatShort = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Date convert(String s) {
        if(StringUtils.hasText(s)){
            try {
                if(s.contains(":")){
                    return dateFormatFull.parse(s);
                }
                return dateFormatShort.parse(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
