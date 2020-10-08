package com.dian.mylibrary.utils.device

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import androidx.fragment.app.FragmentActivity
import com.dian.mylibrary.utils.ktx.L
import com.dian.mylibrary.utils.ktx.SP
import com.dian.mylibrary.utils.permisson.PermissionX

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/9/25 6:58 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/25 6:58 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
object Location {

    @SuppressLint("MissingPermission")
    fun requestLocationUpdates(activity: FragmentActivity) {
        PermissionX.request(
            activity,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) { allGranted, deniedList ->
            if (allGranted) {
                val manager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,      //GPS定位提供者
                    1000L,       //更新数据时间为1秒
                    1F      //位置间隔为1米
                    //位置监听器
                ) { locationUpdates(it) }
                //从GPS获取最新的定位信息
                val location: Location =
                    manager.getLastKnownLocation(LocationManager.GPS_PROVIDER) as Location
                locationUpdates(location) //将最新的定位信息传递给创建的locationUpdates()方法中
            }
        }
    }

   private fun locationUpdates(location: Location?){  //获取指定的查询信息
        //如果location不为空时
        if (location != null) {
            val stringBuilder =
                StringBuilder() //使用StringBuilder保存数据
            //获取经度、纬度、等属性值
            stringBuilder.append("您的位置信息：\n")
            stringBuilder.append("经度：")
            stringBuilder.append(location.longitude)
            stringBuilder.append("\n纬度：")
            stringBuilder.append(location.latitude)
            stringBuilder.append("\n精确度：");
            stringBuilder.append(location.getAccuracy());
            stringBuilder.append("\n高度：");
            stringBuilder.append(location.getAltitude());
            stringBuilder.append("\n方向：");
            stringBuilder.append(location.getBearing());
            stringBuilder.append("\n速度：");
            stringBuilder.append(location.getSpeed());
            L.d("$stringBuilder")
          val map =  mapOf("location" to stringBuilder.toString())
           SP.putData(map)
        } else {
            //否则输出空信息
            L.d("没有获取到GPS信息:")
        }
    }
}