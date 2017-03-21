package com.android.sipdemo.callback

/**
 * Created by zhoujian on 2017/3/20.
 */
interface Callback<T> {
    fun onResult(callbackCode: CallbackCode, t: T)
}