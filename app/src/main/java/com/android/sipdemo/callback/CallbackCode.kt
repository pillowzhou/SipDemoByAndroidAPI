package com.android.sipdemo.callback

/**
 * Created by zhoujian on 2017/3/20.
 */
enum class CallbackCode {
    SUCCESS(0, "SUCCESS"), FAILED(1, "FAILED");

    var code: Int = 0
    var msg: String = ""

    constructor(code: Int, msg: String) {
        this.code = code
        this.msg = msg
    }

    fun getValue(): Int {
        return code
    }

    fun getMessage(): String {
        return msg
    }
}