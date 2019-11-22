package cn.jiweiqing.base.bean;

import lombok.Data;

/**
 * @Author: yb.zhang
 * @Date: 2018/10/16 16:25
 * @Description: 返回结果的基本类
 */
@Data
public class ResultBean<T>{

    private boolean success;
    private String msg;
    private int code;
    private T data;

    public ResultBean(boolean success, int code, String msg, T data) {
        this.success = success;
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public static <DataType> ResultBean<DataType> success(DataType data,String message) {
        return new ResultBean<>(true, 200, message, data);
    }

    public static <DataType> ResultBean<DataType> success(DataType data) {
        return new ResultBean<>(true, 200, "操作成功！", data);
    }

    public static <DataType> ResultBean<DataType> error(int code, String message) {
        return new ResultBean<>(false, code, message, null);
    }

}
