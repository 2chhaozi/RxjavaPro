package cn.liuzehao.kotlin.kotlinOnePra.caseOne

import android.util.Log
import java.net.URL

/**
 * Created by liuzehao on 2019-09-12.
 */
class Request(val url: String) {
    val TAG = "Request"
    public fun run(){
        val forecastJsonStr = URL(url).readText()
        Log.e(TAG, "//"+forecastJsonStr)
    }
}