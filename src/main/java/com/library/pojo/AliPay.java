package com.library.pojo;
import lombok.Data;

/**
 * @Author: zbq
 * @Date: 2023/4/22 下午6:30
 */
@Data
public class AliPay {
    private String traceNo;
    private String totalAmount;
    private String subject;
    private String alipayTraceNo;
}

