package com.ycy.voicerobot.callback;

public interface RequestCallback<T> {
    /**
     * 请求开始之前
     */
    void onBefore();

    /**
     * 请求完成
     */
    void onCompleted();

    /**
     * 请求成功
     */
    void onSuccess(T data);

    /**
     * 请求错误
     */
    void onError(Throwable throwable);
}