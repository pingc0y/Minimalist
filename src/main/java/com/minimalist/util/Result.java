package com.minimalist.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private String msg;
    private Integer code;
    private Integer count;
    private Object data;
}
