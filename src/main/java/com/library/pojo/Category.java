package com.library.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @Author ingo
 * @CreatedAt 2023/3/27 19:35
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Category extends Base{
    //分类id
    @TableId(value = "cid", type = IdType.AUTO)
    private Integer cid;
    //分类名
    private String cateName;
}
