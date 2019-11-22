package cn.jiweiqing.base.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String,Object> StringObjectRedisTemplate(RedisConnectionFactory factory){

        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();

        //配置连接工厂
        redisTemplate.setConnectionFactory(factory);

        //设置key的序列化方式为：String.getBytes("UTF-8")
        //设置key的反序列化方式为：new String(bytes, "UTF-8")
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        //设置value的序列化方式为：使用ObjectOutputStream
        //设置value的反序列化方式为：使用ObjectInputStream
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());

        //配置hash key 和 value 的序列化模式
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate StringRedisTemplate(RedisConnectionFactory factory){
        return new StringRedisTemplate(factory);
    }

}
