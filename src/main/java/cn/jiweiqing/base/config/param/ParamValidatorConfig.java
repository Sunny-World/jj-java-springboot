package cn.jiweiqing.base.config.param;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;

@Configuration
public class ParamValidatorConfig {

    @Bean
    public Validator validator(){
        Validator validator = Validation.byProvider(HibernateValidator.class)
                .configure()
                //第一个参数校验失败之后不再继续校验下一个参数，节省时间
                .failFast(true)
                .buildValidatorFactory()
                .getValidator();
        return validator;
    }

}
