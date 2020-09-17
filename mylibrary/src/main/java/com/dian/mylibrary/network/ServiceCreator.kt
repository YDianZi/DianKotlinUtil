package com.dian.mylibrary.network

import com.dian.mylibrary.utils.ktx.LogKtx
import com.dian.mylibrary.utils.ktx.SP
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/8/27 6:01 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/27 6:01 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
object ServiceCreator {
    private const val BASE_URL = "http://192.168.1.64:8090/"
    private val retrofit = getRetrofit()
    private val DEFAULT_TIMEOUT = 20L
    private val TOKEN = "token"

    fun <T> create(serviceClass: Class<T>) = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)

    private fun getRetrofit(headers: HashMap<String, String> = HashMap()): Retrofit {
        //手动创建一个OkHttpClient并设置超时时间
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        clientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        clientBuilder.addInterceptor {
            //发起请求
            //请求开始时间
            val startTime = System.currentTimeMillis()
            LogKtx.d("startTime:$startTime")
            val request = it.request()
            //打印POST请求参数
            val method = request.method()
            if ("POST" == method) {
                val requestBody = request.body();
                val buffer = Buffer();
                requestBody?.writeTo(buffer);
                var charset = Charset.forName("UTF-8");
                val contentType = requestBody?.contentType();
                if (contentType != null) {
                    charset = contentType.charset(Charset.defaultCharset());//默认utf-9
                }
                val paramsStr = buffer.readString(charset);
                LogKtx.d("POST_PARAM:$paramsStr");
            }
            val newRequestBuilder = request.newBuilder()
            //默认的头
            newRequestBuilder.header("from", "Android")
                .header("timestamp", startTime.toString())
                .header("Authorization", SP.getData(key = TOKEN, defaultValue = ""))
                .header("Content-Type", "application/json");
            for ((key, value) in headers) {
                newRequestBuilder.header(key, value)
            }
            val newRequest = newRequestBuilder.build()
            LogKtx.d(
                String.format(
                    "请求方式：%s\t\n\n请求地址：%s\t\n\n请求头：\t\n%s\t\n请求体：%s",
                    request.method(),
                    request.url(),
                    newRequest.headers(),
                    request.body()
                )
            )
            //收到响应
            val response = it.proceed(newRequest)
            val endTime = System.currentTimeMillis()
            val responseBody = response.peekBody(1024 * 1024)
            LogKtx.d("请求地址：" + request.url() + "\n请求响应：" + responseBody.string())
            LogKtx.d("耗时：${(endTime - startTime)}毫秒")
            //返回值
            response
        }
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()
        return Retrofit.Builder()
            .client(clientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .build()
    }
}