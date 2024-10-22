package com.library.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zbq
 * @Date: 2023/5/29 下午12:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dashboard {
    private double money;
    private int patronNum;
    private int staffNum;
    private int bookNum;
    private int bookAvail;
}
