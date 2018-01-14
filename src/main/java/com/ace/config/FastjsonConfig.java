package com.ace.config;

import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

/**
 * @author bamboo
 */
@Configuration
public class FastjsonConfig {
    /*注入Bean : HttpMessageConverters，以支持fastjson*/
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConvert = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        SimplePropertyFilter filter = new SimplePropertyFilter();
        fastJsonConfig.setSerializeFilters(filter);
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteNullStringAsEmpty);
        fastConvert.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters((HttpMessageConverter<?>) fastConvert);
    }
}

class SimplePropertyFilter implements PropertyFilter {
    /**
     * @param obj  需要过滤的对象
     * @param s    属性
     * @param obj1 属性在内存中的存值 过滤不需要被序列化的属性，主要用于hibernate的代理和管理。
     *             obj1是属性的值，hibernate加载出来后可能是HibernateProxy或PersistentCollection
     *             过滤它
     */
    @Override
    public boolean apply(Object obj, String s, Object obj1) {
        if (obj1 instanceof HibernateProxy) {// hibernate代理对象
            LazyInitializer initializer = ((HibernateProxy) obj1)
                    .getHibernateLazyInitializer();
            if (initializer.isUninitialized()) {
                return false;
            }
        } else if (obj1 instanceof PersistentCollection) {// 实体关联一对多
            PersistentCollection collection = (PersistentCollection) obj1;
            if (!collection.wasInitialized()) {
                return false;
            }
        }
        return true;
    }
}