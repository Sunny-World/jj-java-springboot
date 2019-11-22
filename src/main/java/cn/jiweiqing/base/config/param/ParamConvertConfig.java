package cn.jiweiqing.base.config.param;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.text.DateFormat;

@Configuration
public class ParamConvertConfig {

    public ParamConvertConfig(RequestMappingHandlerAdapter handlerAdapter, MappingJackson2HttpMessageConverter jacksonConverter){
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter.getWebBindingInitializer();
        GenericConversionService genericConversionService = (GenericConversionService) initializer.getConversionService();
        if(genericConversionService!=null){
            genericConversionService.addConverter(new String2DateConverter());
        }

        ObjectMapper mapper = jacksonConverter.getObjectMapper();
        // ObjectMapper为了保障线程安全性，里面的配置类都是一个不可变的对象
        // 所以这里的setDateFormat的内部原理其实是创建了一个新的配置类
        DateFormat dateFormat = mapper.getDateFormat();
        dateFormat = new DateFormatCompat(dateFormat);
        mapper.setDateFormat(dateFormat);
    }

}
