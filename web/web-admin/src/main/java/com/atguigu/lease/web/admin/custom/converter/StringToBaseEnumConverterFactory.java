package com.atguigu.lease.web.admin.custom.converter;

import com.atguigu.lease.model.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

@Component
public class StringToBaseEnumConverterFactory implements ConverterFactory<String, BaseEnum> {
    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return source -> {
            // 通过反射获取枚举的所有值
            for (T enumConstant : targetType.getEnumConstants()) {
                if (enumConstant.getCode().equals(Integer.valueOf(source))) {
                    return enumConstant;
                }
            }
            throw new IllegalArgumentException("非法的枚举值:" + source);
        };
    }
}