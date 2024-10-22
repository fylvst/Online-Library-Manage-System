package com.library.pojo;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @Author: zbq
 * @Date: 2023/3/23 下午8:04
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class BaseUser extends Base{
    //用户名
    private String username;
    //密码
    private String password;
    //昵称
    private String nickname;
    //年龄
    private Integer age;
    //电话
    private String phone;
    //邮箱
    private String email;
}
