package com.redpack.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//不单是用户注册需要录入修改时间，其他的实体类也有，且很多，为了减少重复操作故设立自动处理器。
/**
 * 自定义元数据对象处理器
 */
//Component注解，作用为实现bean注入，相当于xml配置文件中的 <bean id="" class=""/>
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入操作时，自动填充
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充[insert]: "+metaObject.toString());
        metaObject.setValue("addTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
//        metaObject.setValue("createUser",BaseContext.getThreadLocalId());
//        metaObject.setValue("updateUser",BaseContext.getThreadLocalId());
    }

    /**
     * 更新操作时，自动填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充[update]: "+metaObject.toString());
        metaObject.setValue("updateTime", LocalDateTime.now());
//        metaObject.setValue("updateUser", BaseContext.getThreadLocalId());
    }
}

