package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.model.entity.AttrKey;
import com.atguigu.lease.web.admin.mapper.AttrKeyMapper;
import com.atguigu.lease.web.admin.service.AttrKeyService;
import com.atguigu.lease.web.admin.vo.attr.AttrKeyVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【attr_key(房间基本属性表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class AttrKeyServiceImpl extends ServiceImpl<AttrKeyMapper, AttrKey>
        implements AttrKeyService {

//    @Autowired
//    private AttrKeyMapper attrKeyMapper;

    @Override
    public List<AttrKeyVo> listAttrInfo() {
        // baseMapper是ServiceImpl的属性 ,等价于注入的 attrKeyMapper。内存地址是一样的。是同一个对象
        // System.out.println(baseMapper == attrKeyMapper); ==> 输出 true
        // 调用mapper的方法，因为要连表查询，所以需要自己写
        List<AttrKeyVo> list =  baseMapper.listAttrInfo();
        return list;
    }
}




