package com.cl.androidstudy.ui.me

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.cl.androidstudy.MyApplication
import com.cl.androidstudy.R
import com.cl.androidstudy.common.LoginState
import com.cl.androidstudy.ui.me.author.AuthorActivity
import com.cl.androidstudy.ui.me.collection.CollectionActivity
import com.cl.androidstudy.ui.me.login.LoginActivity
import com.cl.androidstudy.ui.me.integralme.MyIntegralActivity
import com.cl.androidstudy.ui.me.integralrank.IntegralRankActivity
import com.cl.androidstudy.ui.me.permission.GithubActivity
import com.cl.androidstudy.ui.me.quit.QuitDialogFragment
import com.cl.androidstudy.ui.me.share.ShareActivity
import kotlinx.android.synthetic.main.fragment_me.*


class MeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_me, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fl_me_collection.setOnClickListener {
            meIntent(CollectionActivity::class.java)
        }
        fl_me_myIntegral.setOnClickListener {
            meIntent(MyIntegralActivity::class.java)
        }
        fl_me_integralrank.setOnClickListener {
            meIntent(IntegralRankActivity::class.java)
        }
        fl_me_share.setOnClickListener {
            meIntent(ShareActivity::class.java)
        }
        fl_me_permission.setOnClickListener {
            meIntent(GithubActivity::class.java)
        }
        fl_me_author.setOnClickListener {
            meIntent(AuthorActivity::class.java)
        }
        val dialog = QuitDialogFragment()
        fl_me_quit.setOnClickListener {
            if (activity != null) {
                dialog.show(requireActivity().supportFragmentManager, "quitFragment")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val cookie = LoginState.preferences.getStringSet("cookies", null)
        val userName = LoginState.preferences.getString("username", "")
        val userid = LoginState.preferences.getString("userid", "")
        if (cookie != null) {
            iv_login.setImageResource(R.drawable.head_pic)
            tv_me_username.text = userName
            tv_me_userid.text = "ID:$userid"
        } else {
            tv_me_username.text = "点击图片登录"
            tv_me_userid.text = "ID:000000"
        }
        iv_login.setOnClickListener {
            if (cookie == null) {
                meIntent(LoginActivity::class.java)
            } else {
                Toast.makeText(requireContext(), "请先退出登录！", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun meIntent(cls: Class<*>) {
        val intent = Intent(requireContext(), cls)
        startActivity(intent)
    }

}