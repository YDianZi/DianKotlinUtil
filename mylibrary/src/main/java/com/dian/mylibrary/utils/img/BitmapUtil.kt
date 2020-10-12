package com.dian.mylibrary.utils.img

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Base64
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/10/8 8:41 AM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/8 8:41 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
object BitmapUtil {
    fun getBitmapWithUrl(
        imageView: ImageView,
        url: String,
        onGlideLoadBitmapCallback: (Bitmap?) -> Unit
    ) {
        Glide.with(imageView)
            .asBitmap()
            .load(url)
            .into(object : BitmapImageViewTarget(imageView) {
                override fun setResource(resource: Bitmap?) {
                    onGlideLoadBitmapCallback(resource)
                }
            })
    }

    /**
     * 保存文件到指定路径
     *
     * @param context
     * @param bmp
     * @return
     */
    fun saveImageToGallery(
        context: Context,
        bmp: Bitmap,
        filepath: String
    ): Boolean {
        // 首先保存图片
        val storePath = Environment.getExternalStorageDirectory()
            .absolutePath + File.separator + filepath
        val appDir = File(storePath)
        if (!appDir.exists()) {
            appDir.mkdir()
        }
        val fileName = System.currentTimeMillis().toString() + ".jpg"
        val file = File(appDir, fileName)
        try {
            val fos = FileOutputStream(file)
            //通过io流的方式来压缩保存图片
            val isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()

            //把文件插入到系统图库
//            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
//            Uri uri = Uri.fromFile(file);
//            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            MediaStore.Images.Media.insertImage(
                context.contentResolver,
                BitmapFactory.decodeFile(file.absolutePath),
                file.name,
                null
            )
            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            val uri = Uri.fromFile(file)
            intent.data = uri
            context.sendBroadcast(intent)
            return if (isSuccess) {
                true
            } else {
                false
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 保存文件到指定路径
     *
     * @param context
     * @param bmp
     * @return
     */
    fun saveImageToGalleryGetPath(
        context: Context,
        bmp: Bitmap,
        filepath: String
    ): String? {
        // 首先保存图片
        val storePath = Environment.getExternalStorageDirectory()
            .absolutePath + File.separator + filepath
        val appDir = File(storePath)
        if (!appDir.exists()) {
            appDir.mkdir()
        }
        val fileName = System.currentTimeMillis().toString() + ".jpg"
        val file = File(appDir, fileName)
        try {
            val fos = FileOutputStream(file)
            //通过io流的方式来压缩保存图片
            val isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()

            //把文件插入到系统图库
//            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
//            Uri uri = Uri.fromFile(file);
//            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

            //通知相册更新
            MediaStore.Images.Media.insertImage(
                context.contentResolver,
                BitmapFactory.decodeFile(file.absolutePath),
                file.name,
                null
            )
            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            val uri = Uri.fromFile(file)
            intent.data = uri
            context.sendBroadcast(intent)
            return if (isSuccess) {
                storePath
            } else {
                ""
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 保存文件到指定路径
     *
     * @param context
     * @param bmp
     * @return
     */
    fun saveImageToGallery(
        context: Context,
        bmp: Bitmap,
        filepath: String,
        name: String
    ): String? {
        // 首先保存图片
        val storePath = Environment.getExternalStorageDirectory()
            .absolutePath + File.separator + filepath
        val appDir = File(storePath)
        if (!appDir.exists()) {
            appDir.mkdir()
        }
        val fileName = "$name.jpg"
        val file = File(appDir, fileName)
        try {
            val fos = FileOutputStream(file)
            //通过io流的方式来压缩保存图片
            val isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()

            //把文件插入到系统图库
//            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            val uri = Uri.fromFile(file)
            context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
            return if (isSuccess) {
                file.absolutePath
            } else {
                ""
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 保存文件到指定路径
     *
     * @param context
     * @return
     */
    fun delImageToGallery(
        context: Context,
        filepath: String
    ): Boolean {
        if (TextUtils.isEmpty(filepath)) return false
        val where = MediaStore.Audio.Media.DATA + " like \"" + filepath + "%" + "\""
        val i = context.contentResolver
            .delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, where, null)
        return if (i > 0) {
            true
        } else false
    }


    fun bmpToByteArray(bmp: Bitmap, needRecycle: Boolean): ByteArray? {
        val output = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output)
        if (needRecycle) {
            bmp.recycle()
        }
        val result = output.toByteArray()
        try {
            output.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }


    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    fun base64ToBitmap(base64Data: String): Bitmap? {
        var base64Data = base64Data
        base64Data = base64Data.replace("data:image/png;base64,", "")
        val bytes =
            Base64.decode(base64Data, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

}