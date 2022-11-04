package com.imchat.common.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imchat.common.infra.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Administrator
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
