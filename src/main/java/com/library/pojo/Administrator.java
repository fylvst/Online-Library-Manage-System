package com.library.pojo;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @Author: zbq
 * @Date: 2023/3/23 下午8:08
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Administrator extends Base{
    /**
     * 管理员id
     */
    private Integer aid;
    //用户名
    private String username;
    //密码
    private String password;
    //昵称
    private String nickname;

}
