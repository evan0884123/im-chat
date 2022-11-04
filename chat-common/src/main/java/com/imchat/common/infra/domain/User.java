package com.imchat.common.infra.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息
 *
 * @author Administrator
 */
@Data
@NoArgsConstructor
@TableName("user")
public class User {

    /**
     *
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 登录名
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户名
     */
    private String fullName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * email地址
     */
    private String email;

    /**
     * 居住地址
     */
    private String address;

    /**
     * '创建时间'
     */
    private Long createTime;

    /**
     * '修改时间'
     */
    private Long updateTime;

    public User(User user) {
        this.id = user.getId();
        this.account = user.getAccount();
        this.password = user.getPassword();
    }

}
