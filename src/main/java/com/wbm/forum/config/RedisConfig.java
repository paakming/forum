package com.wbm.forum.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author：Ming
 * @Date: 2023/2/25 16:57
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)     {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // String的序列化
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringSerializer);
        // hash的key采用String的序列化方式
        template.setHashKeySerializer(stringSerializer);

       // Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = jackson2JsonRedisSerializerConfig();
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        //value采用jackson序列化方式
        template.setValueSerializer(fastJsonRedisSerializer);
        //hash的value也采用jackson序列化方式
        template.setHashValueSerializer(fastJsonRedisSerializer);

        template.afterPropertiesSet();


        return template;
    }

//    private Jackson2JsonRedisSerializer jackson2JsonRedisSerializerConfig() {
//        // jackson序列化所有的类
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        // jackson序列化的一些配置
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance);
//             om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//       om.registerModule(new JavaTimeModule());
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        return jackson2JsonRedisSerializer;
 //   }
}
