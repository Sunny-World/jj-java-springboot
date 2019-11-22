package cn.jiweiqing.base.config.shiro;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@ConfigurationProperties("shiro")
@Data
public class ShiroResources {

    private HashSet<String> anon;

    private String unlogin;

    public boolean isAnonResource(String uri){
        return anon !=null && anon.contains(uri);
    }

}
