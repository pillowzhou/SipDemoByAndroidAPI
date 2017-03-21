package com.android.sipdemo.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.android.sipdemo.R
import com.android.sipdemo.manager.VoipManager

class CallActivity : AppCompatActivity(), View.OnClickListener {

    var btnIncomingHangup: Button? = null
    var btnIncomingAnswer: Button? = null

    var btnHangup: Button? = null

    var btnHold: Button? = null
    var btnMute: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        btnHangup = findViewById(R.id.btn_hangup) as Button
        btnIncomingHangup = findViewById(R.id.btn_incoming_hangup) as Button
        btnIncomingAnswer = findViewById(R.id.btn_incoming_answer) as Button
        btnHold = findViewById(R.id.btn_hold) as Button
        btnMute = findViewById(R.id.btn_mute) as Button

        btnHangup?.setOnClickListener(this)
        btnIncomingHangup?.setOnClickListener(this)
        btnIncomingAnswer?.setOnClickListener(this)
        btnHold?.setOnClickListener(this)
        btnMute?.setOnClickListener(this)

        btnIncomingHangup?.visibility = View.GONE
        btnIncomingAnswer?.visibility = View.GONE
        btnHold?.visibility = View.GONE
        btnMute?.visibility = View.GONE
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_hangup -> {
                VoipManager.handup()
                finish()
            }
        }
    }
}
