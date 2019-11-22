package cn.jiweiqing.base.config.param;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatCompat  extends DateFormat{

    private static final SimpleDateFormat dateFormatLong = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private static final SimpleDateFormat dateFormatShort = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);

    private final DateFormat configedDateFormat;

    public DateFormatCompat(DateFormat dateFormat){
        configedDateFormat = dateFormat;
        setCalendar(configedDateFormat.getCalendar());
        setNumberFormat(configedDateFormat.getNumberFormat());
        setTimeZone(configedDateFormat.getTimeZone());
    }

    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        return configedDateFormat.format(date,toAppendTo,fieldPosition);
    }

    @Override
    public Date parse(String source, ParsePosition pos) {
        try {
            boolean isLongDate = source.contains(":");
            if(isLongDate){
                return (Date) dateFormatLong.parseObject(source,pos);
            }
            return (Date) dateFormatShort.parseObject(source,pos);
        }catch (Exception e){
            if(configedDateFormat!=null){
                return configedDateFormat.parse(source,pos);
            }
        }
        return null;
    }

    @Override
    public Object clone() {
        return new DateFormatCompat(configedDateFormat);
    }

}
