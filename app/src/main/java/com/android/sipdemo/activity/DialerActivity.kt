package com.android.sipdemo.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.android.sipdemo.callback.Callback
import com.android.sipdemo.callback.CallbackCode
import com.android.sipdemo.R
import com.android.sipdemo.manager.VoipManager

class DialerActivity : AppCompatActivity(), View.OnClickListener {
    var tvNumber: TextView? = null
    var firstTime: Long? = 0
    var context: Context? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialer)
        context = this

        findViewById(R.id.tv_dialer_0).setOnClickListener(this)
        findViewById(R.id.tv_dialer_1).setOnClickListener(this)
        findViewById(R.id.tv_dialer_2).setOnClickListener(this)
        findViewById(R.id.tv_dialer_3).setOnClickListener(this)
        findViewById(R.id.tv_dialer_4).setOnClickListener(this)
        findViewById(R.id.tv_dialer_5).setOnClickListener(this)
        findViewById(R.id.tv_dialer_6).setOnClickListener(this)
        findViewById(R.id.tv_dialer_7).setOnClickListener(this)
        findViewById(R.id.tv_dialer_8).setOnClickListener(this)
        findViewById(R.id.tv_dialer_9).setOnClickListener(this)
        findViewById(R.id.tv_dialer_del).setOnClickListener(this)
        findViewById(R.id.tv_dialer_dial).setOnClickListener(this)

        tvNumber = findViewById(R.id.tv_display) as TextView
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_dialer_0 -> {
                tvNumber?.text = tvNumber?.text.toString().plus("0")
            }
            R.id.tv_dialer_1 -> {
                tvNumber?.text = tvNumber?.text.toString().plus("1")
            }
            R.id.tv_dialer_2 -> {
                tvNumber?.text = tvNumber?.text.toString().plus("2")
            }
            R.id.tv_dialer_3 -> {
                tvNumber?.text = tvNumber?.text.toString().plus("3")
            }
            R.id.tv_dialer_4 -> {
                tvNumber?.text = tvNumber?.text.toString().plus("4")
            }
            R.id.tv_dialer_5 -> {
                tvNumber?.text = tvNumber?.text.toString().plus("5")
            }
            R.id.tv_dialer_6 -> {
                tvNumber?.text = tvNumber?.text.toString().plus("6")
            }
            R.id.tv_dialer_7 -> {
                tvNumber?.text = tvNumber?.text.toString().plus("7")
            }
            R.id.tv_dialer_8 -> {
                tvNumber?.text = tvNumber?.text.toString().plus("8")
            }
            R.id.tv_dialer_9 -> {
                tvNumber?.text = tvNumber?.text.toString().plus("9")
            }
            R.id.tv_dialer_del -> {
                if (tvNumber?.length() as Int > 0) {
                    var tempText: String? = tvNumber?.text?.substring(0, tvNumber?.length()?.minus(1) as Int)
                    tvNumber?.text = tempText
                }
            }
            R.id.tv_dialer_dial -> {
                var callback = object : Callback<Boolean> {
                    override fun onResult(callbackCode: CallbackCode, t: Boolean) {
                        if (callbackCode == CallbackCode.SUCCESS) {
                            var intent = Intent()
                            intent.setClass(context, CallActivity::class.java)
                            intent.putExtra("CALL_TYPE", 0)
                            startActivity(intent)
                        }
                    }
                }
                VoipManager.makeCall(tvNumber?.text.toString(), callback)
            }
        }
    }

    override fun onBackPressed() {
        var secondTime: Long = System.currentTimeMillis()
        if (secondTime.minus(firstTime as Long) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show()
            firstTime = secondTime
            return
        } else {
            Toast.makeText(this, "正在退出...", Toast.LENGTH_SHORT).show()
            VoipManager.closeLocalProfile()
            super.onBackPressed()
        }
    }
}
