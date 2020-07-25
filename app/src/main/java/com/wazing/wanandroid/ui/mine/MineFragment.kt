package com.wazing.wanandroid.ui.mine

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.core.content.edit
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.jeremyliao.liveeventbus.LiveEventBus
import com.wazing.wanandroid.R
import com.wazing.wanandroid.model.User
import com.wazing.wanandroid.ui.mine.coin.CoinActivity
import com.wazing.wanandroid.ui.mine.collect.CollectArticleActivity
import com.wazing.wanandroid.ui.mine.login.RegisterActivity
import com.wazing.wanandroid.util.EVENT_USER_INFO
import com.wazing.wanandroid.util.SP_USER_INFO
import com.wazing.wanandroid.util.Single
import com.wazing.wanandroid.util.navigateUpTo
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MineFragment : Fragment(R.layout.fragment_mine), View.OnClickListener {

    private val userViewModel by viewModel<UserViewModel>()
    private val sharedPrefs by inject<SharedPreferences>()

    private var userJob: Job? = null
    private var coinJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 注册订阅
        initLiveEventBusObserve()
        initViewModel()

        // 跳转到登录和注册
        mine_center.setOnClickListener(this)
        // 收藏的文章
        mine_collect_article.setOnClickListener(this)
        // 积分详情
        mine_coin_info.setOnClickListener(this)
        mine_night_mode.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mine_center -> requireActivity().navigateUpTo(RegisterActivity::class.java)
            R.id.mine_collect_article -> requireActivity().navigateUpTo(CollectArticleActivity::class.java)
            R.id.mine_coin_info -> requireActivity().navigateUpTo(CoinActivity::class.java)
            R.id.mine_night_mode -> Unit
        }
    }

    private fun initLiveEventBusObserve() {
        LiveEventBus.get(EVENT_USER_INFO, User::class.java).observe(viewLifecycleOwner, Observer {
            println("live bus user -> $it")
            sharedPrefs.edit {
                putString(SP_USER_INFO, Single.toJson(it))
            }
            userViewModel.setUserId(it.id)
        })
    }

    private fun initViewModel() {
        val user = getUserId()
        userViewModel.setUserId(user?.id)
        userViewModel.isLogin.observe(viewLifecycleOwner, Observer {
            println("登录 ->  ${if (it) "成功" else "失败"}")
            mine_register.isVisible = !it
            mine_nickname.isVisible = it
            mine_level.isVisible = it
            mine_coin_count.isVisible = it
            mine_rank.isVisible = it
            if (it) {
                resetObserver()
                userViewModel.getCoin()
            }
        })
    }

    private fun resetObserver() {
        userJob?.cancel()
        userJob = lifecycleScope.launchWhenCreated {
            userViewModel.user().collect {
                println("user -> $it")
                it ?: return@collect
                mine_nickname.text = it.nickname
            }
        }
        coinJob?.cancel()
        coinJob = lifecycleScope.launchWhenCreated {
            userViewModel.coin().collect {
                println("coin -> $it")
                it ?: return@collect
                mine_level.text = getString(R.string.mine_level, it.level)
                mine_coin_count.text = getString(R.string.mine_coin_count, it.coinCount)
                mine_rank.text = getString(R.string.mine_rank, it.rank)
            }
        }
    }

    /**
     * 从 SharedPreferences 中获取持久化 user
     */
    private fun getUserId(): User? {
        return try {
            Single.toUser(sharedPrefs.getString(SP_USER_INFO, null))
        } catch (ex: Exception) {
            println("user is null, ${ex.message}")
            null
        }
    }

}