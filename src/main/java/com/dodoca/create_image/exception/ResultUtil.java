package com.dodoca.create_image.exception;

/**
 * @description:
 * @author: tianguanghui
 * @create: 2019-06-28 14:48
 **/
public class ResultUtil {

    /**
     * 操作成功返回数据
     * @param object
     * @return
     */
    public static BaseResult success(Object object) {
        BaseResult baseResult = new BaseResult();
        baseResult.setCode(ResultEnum.SUCCESS.getCode());
        baseResult.setMsg(ResultEnum.SUCCESS.getMsg());
        baseResult.setData(object);
        return baseResult;
    }

    /**
     * 操作成功不返回数据
     * @return
     */
    public static BaseResult success() {
        return success(null);
    }

    /**
     * 操作失败返回的消息
     * @param code
     * @param msg
     * @return
     */
    public static BaseResult error(int code,String msg) {
        BaseResult baseResult = new BaseResult();
        baseResult.setCode(code);
        baseResult.setMsg(msg);
        return baseResult;
    }

    public static BaseResult error(int code,String msg, Object object) {
        BaseResult baseResult = new BaseResult();
        baseResult.setCode(code);
        baseResult.setMsg(msg);
        baseResult.setData(object);
        return baseResult;
    }

    /**
     * 操作失败返回消息，对error的重载
     * @param resultEnum
     * @return
     */
    public static BaseResult error(ResultEnum resultEnum){
        BaseResult baseResult = new BaseResult();
        baseResult.setCode(resultEnum.getCode());
        baseResult.setMsg(resultEnum.getMsg());
        return baseResult;
    }

    /**
     * 操作失败 返回数据
     * @param resultEnum
     * @param object
     * @return
     */
    public static BaseResult error(ResultEnum resultEnum, Object object){
        BaseResult baseResult = new BaseResult();
        baseResult.setCode(resultEnum.getCode());
        baseResult.setMsg(resultEnum.getMsg());
        baseResult.setData(object);
        return baseResult;
    }
}
