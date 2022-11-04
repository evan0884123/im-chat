package com.imchat.web.cache;

import com.imchat.common.infra.domain.User;
import com.imchat.common.infra.mapper.UserMapper;
import com.sychina.admin.infra.domain.AdminMenu;
import com.sychina.admin.infra.domain.AdminRole;
import com.sychina.admin.infra.domain.AdminUser;
import com.sychina.admin.infra.mapper.AdminMenuMapper;
import com.sychina.admin.infra.mapper.AdminUserMapper;
import com.sychina.admin.service.impl.AdminRoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 */
@Component
@CacheConfig(cacheNames = "admin")
public class UserCache {

    private UserMapper userMapper;

    @Cacheable(key = "'adminUser' + #userId")
    public User cacheUser(String userId) {
        User adminUser = userMapper.selectById(userId);

        return adminUser;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
