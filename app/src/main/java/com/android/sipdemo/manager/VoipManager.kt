package com.android.sipdemo.manager

import android.content.Context
import android.net.sip.SipAudioCall
import android.net.sip.SipManager
import android.net.sip.SipProfile
import android.net.sip.SipRegistrationListener
import android.text.TextUtils
import android.util.Log
import com.android.sipdemo.*
import com.android.sipdemo.callback.Callback
import com.android.sipdemo.callback.CallbackCode
import com.android.sipdemo.listener.OnStateChangedListener

/**
 * Created by zhoujian on 2017/3/20.
 */
object VoipManager : SipRegistrationListener {

    var manager: SipManager? = null
    var me: SipProfile? = null
    var onStateChangedListeners: MutableList<OnStateChangedListener> = ArrayList<OnStateChangedListener>()
    var currentCall: SipAudioCall? = null

    fun initVoipManager(context: Context) {
        if (manager == null) {
            manager = SipManager.newInstance(context)
        }
    }

    /**
     * 初始化注册
     * */
    fun init(account: String, password: String, demion: String) {
        closeLocalProfile()
        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(demion)) {
            val sipProflieBuild = SipProfile.Builder(account, demion)
            sipProflieBuild.setPassword(password)
            me = sipProflieBuild.build()
            manager?.open(me)
            manager?.register(me, 20, this)
            manager?.setRegistrationListener(me?.uriString, this)
        }
    }

    /**
     * 关闭profile，释放资源，同时解除注册
     * */
    fun closeLocalProfile() {
        if (manager == null) {
            return
        }
        if (me != null) {
            manager?.close(me?.uriString)
        }
    }

    /**
     * 呼叫
     * */
    fun makeCall(number: String, callback: Callback<Boolean>) {
        if (manager == null)
            return
        var listener = object : SipAudioCall.Listener() {
            override fun onCallEstablished(call: SipAudioCall?) {
                Log.d("debug", "enter onCallEstablished method")
                super.onCallEstablished(call)
                currentCall = call
                call?.startAudio()
                call?.setSpeakerMode(true)
                //call?.toggleMute()
                callback.onResult(CallbackCode.SUCCESS, false)
            }

            override fun onCallBusy(call: SipAudioCall?) {
                super.onCallBusy(call)
                Log.d("debug", "enter onCallEstablished method")
                callback.onResult(CallbackCode.FAILED, false)
            }

            override fun onCallEnded(call: SipAudioCall?) {
                super.onCallEnded(call)
                currentCall = null
                Log.d("debug", "enter onCallEnded method")
            }

            override fun onRinging(call: SipAudioCall?, caller: SipProfile?) {
                super.onRinging(call, caller)
                Log.d("debug", "enter onRinging method")
            }

            override fun onCallHeld(call: SipAudioCall?) {
                super.onCallHeld(call)
                Log.d("debug", "enter onCallHeld method")
            }

            override fun onCalling(call: SipAudioCall?) {
                super.onCalling(call)
                Log.d("debug", "enter onCalling method")
            }

            override fun onChanged(call: SipAudioCall?) {
                super.onChanged(call)
                Log.d("debug", "enter onChanged method")
            }

            override fun onError(call: SipAudioCall?, errorCode: Int, errorMessage: String?) {
                super.onError(call, errorCode, errorMessage)
                Log.d("debug", "enter onChanged method")
                callback.onResult(CallbackCode.FAILED, false)
            }

            override fun onReadyToCall(call: SipAudioCall?) {
                super.onReadyToCall(call)
                Log.d("debug", "enter onReadyToCall method")
            }

            override fun onRingingBack(call: SipAudioCall?) {
                super.onRingingBack(call)
                Log.d("debug", "enter onRingingBack method")
            }
        }
        manager?.makeAudioCall(me?.uriString, "sip:" + number + "@10.2.58.214", listener, 30)
    }


    fun handup() {
        if (manager == null)
            return

        if (currentCall != null) {
            currentCall?.endCall()
        }
    }

    /**
     * 添加状态改变监听
     * */
    fun addOnStateChangedListener(listener: OnStateChangedListener) {
        if (listener != null && !onStateChangedListeners.contains(listener)) {
            onStateChangedListeners.add(listener)
        }
    }

    /**
     * 移除状态改变监听
     * */
    fun removeOnStateChangedListener(listener: OnStateChangedListener) {
        if (listener != null && onStateChangedListeners.contains(listener)) {
            onStateChangedListeners.remove(listener)
        }
    }

    override fun onRegistering(localProfileUri: String?) {
        Log.d("debug", "enter onRegistering method; localProfileUtl = " + localProfileUri)
        updateState(STATE_REGISTERING, null)
    }

    override fun onRegistrationDone(localProfileUri: String?, expiryTime: Long) {
        Log.d("debug", "enter onRegistrationDone method; localProfileUtl = " + localProfileUri)
        updateState(STATE_REGISTER_SUCCESS, null)
    }

    override fun onRegistrationFailed(localProfileUri: String?, errorCode: Int, errorMessage: String?) {
        Log.d("debug", "enter onRegistrationFailed method; localProfileUtl = " + localProfileUri + ", errorMessage = " + errorMessage)
        updateState(STATE_REGISTER_FAILED, null)
    }

    fun updateState(state: Int, o: Object?) {
        for (listener in onStateChangedListeners) {
            listener.onChanged(state, o)
        }
    }
}
