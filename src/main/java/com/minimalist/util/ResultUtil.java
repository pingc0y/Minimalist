
package com.minimalist.util;

public class ResultUtil {
    public static Result success(Object object,Integer count) {
        Result result = new Result();
        result.setCode(0);
        result.setMsg("成功");
        result.setCount(count);
        result.setData(object);
        return result;
    }

    public static Result success() {
        return success(null,0);
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setCount(0);
        return result;
    }
}