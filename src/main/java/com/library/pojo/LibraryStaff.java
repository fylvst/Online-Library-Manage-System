package com.library.pojo;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class LibraryStaff extends BaseUser {
    //职员id
    private Integer sid;
}
