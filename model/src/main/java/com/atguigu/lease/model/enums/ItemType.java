package com.atguigu.lease.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;


public enum ItemType implements BaseEnum {

    APARTMENT(1, "公寓"),

    ROOM(2, "房间");


    @EnumValue
    @JsonValue
    /*
    * @EnumValue:mybatis-plus提供,将枚举对象和属性进行映射
    * ps:当前标记在 code 字段上面。
    *
    * 以ItemType.APARTMENT对象为例，查询数据库的时候，会将ItemType.APARTMENT.code的值传入到sql语句中
    *
    *   没加@EnumValue注解的时候
    *       sql=>SELECT id,type,name,create_time,update_time,is_deleted FROM label_info WHERE is_deleted=0 AND (type = APARTMENT)
    *       type = APARTMENT，为当前枚举ItemType.APARTMENT的名称
    *
    *   添加@EnumValue注解的时候
    *       sql=>SELECT id,type,name,create_time,update_time,is_deleted FROM label_info WHERE is_deleted=0 AND (type = 1)
    *       type = 1，为当前枚举ItemType.APARTMENT.code的值
    * */

    /*
     * @JsonValue:jackson提供,将枚举对象和属性进行映射
     * ps:当前标记在 code 字段上面。
     *
     * 以ItemType.APARTMENT对象为例，查询数据库的时候，mybatis-plus已将查询到的记录封装成了ItemType.APARTMENT对象
     *
     *   没加@JsonValue
     *       返回给前端的数据=>"type": "APARTMENT",
     *
     *   添加@JsonValue
     *       返回给前端的数据=>"type": '1',
     * */
    private Integer code;
    private String name;

    @Override
    public Integer getCode() {
        return this.code;
    }


    @Override
    public String getName() {
        return name;
    }

    ItemType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

}
