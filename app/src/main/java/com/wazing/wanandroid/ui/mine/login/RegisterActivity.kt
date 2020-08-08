package com.wazing.wanandroid.ui.mine.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus
import com.wazing.wanandroid.R
import com.wazing.common.data.ResultCallback
import com.wazing.wanandroid.util.EVENT_USER_INFO
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private val viewModel by viewModel<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        toolbar.apply {
            setSupportActionBar(this)
            setNavigationIcon(R.drawable.ic_arrow_back_24)
            setNavigationOnClickListener { finish() }
        }

        login_username.setText("wazing")
        login_password.setText("123456")

        login_sign_in.setOnClickListener(this)
        login_sign_up.setOnClickListener(this)
        initViewModel()
    }

    override fun onClick(view: View) {
        val username = login_username.text.trim().toString()
        val password = login_password.text.trim().toString()
        when (view.id) {
            R.id.login_sign_in -> viewModel.signIn(username, password)
            R.id.login_sign_up -> viewModel.signUp(username, password)
        }
    }

    private fun initViewModel() {
        viewModel.user.observe(this, Observer {
            login_loading.isVisible = it is ResultCallback.Loading
            login_sign_up.isEnabled = it !is ResultCallback.Loading
            login_sign_in.isEnabled = it !is ResultCallback.Loading
            login_username.isEnabled = it !is ResultCallback.Loading
            login_password.isEnabled = it !is ResultCallback.Loading
            when (it) {
                is ResultCallback.Success -> {
                    val userInfo = it.data
                    val text = getString(R.string.login_welcome, userInfo.publicName)
                    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
                    LiveEventBus.get(EVENT_USER_INFO).post(userInfo)
                    this.finish()
                }
                is ResultCallback.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}