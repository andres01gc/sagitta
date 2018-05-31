package io.github.gc.sagitta

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_initial.*
import android.net.sip.SipErrorCode.TIME_OUT
import android.content.Intent
import android.os.Handler


class InitialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)
        jumpPage()
    }

    fun jumpPage() {
        Handler().postDelayed(Runnable {
            val i = Intent(this@InitialActivity, LoginActivity::class.java)
            startActivity(i)
            finish()
        }, 3000)
    }
}
