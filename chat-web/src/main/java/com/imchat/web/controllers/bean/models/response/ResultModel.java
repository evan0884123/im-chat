package com.imchat.web.controllers.bean.models.response;

/**
 * 返回前端的数据模型.
 *
 * @param <T>
 * @author Administrator
 */
public class ResultModel<T> {

    /**
     * 返回编码
     */
    protected Integer code;

    /**
     * 返回信息
     */
    protected String msg;

    /**
     * 返回数据
     */
    protected T data;

    /**
     * interface timestamp
     */
    protected Long timestamp = System.currentTimeMillis();

    /**
     * token 失效
     *
     * @return
     */
    public static <T> ResultModel<T> unauthorized() {
        return new ResultModel<T>(ResponseStatus.UNAUTHORIZED);
    }

    public static <T> ResultModel<T> unauthorized(T data) {
        return new ResultModel<T>(ResponseStatus.UNAUTHORIZED, data);
    }

    /**
     * 返回成功
     *
     * @return 视图模型
     */
    public static <T> ResultModel<T> succeed() {
        return new ResultModel<T>(ResponseStatus.SUCCESS);
    }

    public static <T> ResultModel<T> succeed(T data) {
        return new ResultModel<T>(ResponseStatus.SUCCESS, data);
    }

    /**
     * 返回失败
     *
     * @return 视图模型
     */
    public static <T> ResultModel<T> failed() {
        return new ResultModel<T>(ResponseStatus.FAILURE);
    }

    public static <T> ResultModel<T> failed(T data) {
        return new ResultModel<T>(ResponseStatus.FAILURE, data);
    }

    protected ResultModel() {
    }

    protected ResultModel(ResponseStatus status) {
        this.code = status.code();
        this.msg = status.status();
    }

    protected ResultModel(ResponseStatus status, T data) {
        this.code = status.code();
        this.msg = status.status();
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
