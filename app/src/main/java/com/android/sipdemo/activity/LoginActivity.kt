package com.android.sipdemo.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.sipdemo.*
import com.android.sipdemo.activity.DialerActivity
import com.android.sipdemo.listener.OnStateChangedListener
import com.android.sipdemo.manager.VoipManager

class LoginActivity : AppCompatActivity(), OnStateChangedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.setTitle("登录")
        setSupportActionBar(toolbar)

        VoipManager.addOnStateChangedListener(this)

        var sipAccount = findViewById(R.id.sip_device_id) as TextView
        var sipPassword = findViewById(R.id.sip_password) as TextView
        var sipDomain = findViewById(R.id.sip_domain) as TextView

        sipAccount.text = "280085"
        sipPassword.text = "1234"
        sipDomain.text = "10.2.58.214"

        val btnLogin = findViewById(R.id.agent_login) as Button
        btnLogin.setOnClickListener {
            VoipManager.initVoipManager(this.applicationContext)
            VoipManager.init(sipAccount.text.toString(), sipPassword.text.toString(), sipDomain.text.toString())
        }
    }

    override fun onChanged(state: Int, o: Object?) {
        when (state) {
            STATE_REGISTERING -> {
                runOnUiThread {
                    Toast.makeText(this, "registering", Toast.LENGTH_SHORT).show()
                }
            }

            STATE_REGISTER_SUCCESS -> {
                runOnUiThread {
                    Toast.makeText(this, "registersuccess", Toast.LENGTH_SHORT).show()
                    var intent = Intent()
                    intent.setClass(this, DialerActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            STATE_REGISTER_FAILED -> {
                runOnUiThread {
                    Toast.makeText(this, "registerfailed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        VoipManager.removeOnStateChangedListener(this)
        super.onDestroy()
    }
}
