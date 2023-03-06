package com.wbm.forum.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

/**
 * @Author：Ming
 * @Date: 2023/2/25 17:57
 */
@Configuration
public class FastJson   {
    @Bean //使用@Bean注入fast]sonHttpMessageconvert
    public HttpMessageConverter fastJsonHttpMessageConverters(){
        //1.需要定义一个Convert转换消息的对象
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH :mm:ss ");
        SerializeConfig.globalInstance.put(Long.class, ToStringSerializer.instance);
        fastJsonConfig.setSerializeConfig(SerializeConfig.globalInstance);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig) ;
        HttpMessageConverter<?> converter = fastJsonHttpMessageConverter;
        return converter;
    }

/*    public void configureMessageConverters(List<HttpMessageConverter<?>> converters){
        converters.add (fastJsonHttpMessageConverters());
    }*/

}