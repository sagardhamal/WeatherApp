package com.test.weather.ui.Help

import android.view.View
import android.webkit.WebView
import android.widget.RadioGroup
import com.test.weather.R
import com.test.weather.ui.base.BaseFragmentKotlin
import kotlinx.android.synthetic.main.help_fragment.*


class HelpFragment : BaseFragmentKotlin(), View.OnClickListener,
    RadioGroup.OnCheckedChangeListener {
    private lateinit var webView: WebView
    companion object {
        fun newInstance() = HelpFragment()
    }


    override fun getFragmentLayout(): Int {
        return R.layout.help_fragment
    }

    override fun initView(view: View) {

        setWebView()
    }

    private fun setWebView() {
        val wv: WebView
      //  wv = (R.id.webview) as WebView
        webview.loadUrl("file:///android_asset/help.html")
    }

    private fun getCheckedUnit(checkedId: Int) {
        when (checkedId) {
        }
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
        }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        getCheckedUnit(checkedId)
    }


}
