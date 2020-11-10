package com.dian.mylibrary.utils.file

import android.app.Activity
import android.os.Build
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.dian.mylibrary.utils.ktx.L
import com.dian.mylibrary.utils.ktx.showToast
import com.dian.mylibrary.utils.permisson.PermissionX
import okhttp3.ResponseBody
import java.io.*
import java.nio.channels.FileChannel

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/10/12 10:18 AM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/12 10:18 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
object FileUtil {
    /**
     * Delete all files in directory.
     *
     * @param dir The directory.
     * @return `true`: success<br></br>`false`: fail
     */
    fun deleteFilesInDir(dir: File?): Boolean {
        return deleteFilesInDirWithFilter(dir, FileFilter { pathname -> pathname.isFile })
    }

    /**
     * Delete all files that satisfy the filter in directory.
     *
     * @param dir    The directory.
     * @param filter The filter.
     * @return `true`: success<br></br>`false`: fail
     */
    fun deleteFilesInDirWithFilter(
        dir: File?,
        filter: FileFilter?
    ): Boolean {
        if (dir == null || filter == null) return false
        // dir doesn't exist then return true
        if (!dir.exists()) return true
        // dir isn't a directory then return false
        if (!dir.isDirectory) {
            return dir.delete()
        } else {
            val files = dir.listFiles()
            if (files != null && files.size != 0) {
                for (file in files) {
                    if (filter.accept(file)) {
                        if (!file.delete()) return false
                    } else if (file.isDirectory) {
                        if (!deleteDir(file)) return false
                    }
                }
            }
        }
        return true
    }

    /**
     * Delete the directory.
     *
     * @param dir The directory.
     * @return `true`: success<br></br>`false`: fail
     */
    private fun deleteDir(dir: File?): Boolean {
        if (dir == null) return false
        // dir doesn't exist then return true
        if (!dir.exists()) return true
        // dir isn't a directory then return false
        if (!dir.isDirectory) return false
        val files = dir.listFiles()
        if (files != null && files.size != 0) {
            for (file in files) {
                if (file.isFile) {
                    if (!file.delete()) return false
                } else if (file.isDirectory) {
                    if (!deleteDir(file)) return false
                }
            }
        }
        return dir.delete()
    }

    fun writeResponseBodyToDisk(
        activity: Activity,
        body: ResponseBody,
        filePath: String,
        fileName: String
    ): Boolean {
        var filePath = filePath
        var fileName = fileName
        return try {
            // todo change the file location/name according to your needs
            if (TextUtils.isEmpty(filePath)) {
                filePath = File.separator
            }
            if (TextUtils.isEmpty(fileName)) {
                fileName = "test"
            }
            val futureStudioIconFile =
                File(activity.getExternalFilesDir(null).toString() + filePath + fileName)
            Log.d("FileUtils", "file path: " + futureStudioIconFile.absolutePath)
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            try {
                val fileReader = ByteArray(4096)
                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0
                inputStream = body.byteStream()
                outputStream = FileOutputStream(futureStudioIconFile)
                while (true) {
                    val read = inputStream.read(fileReader)
                    if (read == -1) {
                        break
                    }
                    outputStream.write(fileReader, 0, read)
                    fileSizeDownloaded += read.toLong()
                    Log.d(
                        "FileUtils",
                        "file download: $fileSizeDownloaded of $fileSize"
                    )
                }
                outputStream.flush()
                true
            } catch (e: IOException) {
                false
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: IOException) {
            false
        }
    }


    fun writeResponseBodyToDisk(
        activity: Activity?,
        body: ResponseBody,
        futureStudioIconFile: File?
    ): Boolean {
        return try {
            // todo change the file location/name according to your needs
            if (futureStudioIconFile == null) return false
            Log.d("FileUtils", "file path: " + futureStudioIconFile.absolutePath)
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            try {
                val fileReader = ByteArray(4096)
                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0
                inputStream = body.byteStream()
                outputStream = FileOutputStream(futureStudioIconFile)
                while (true) {
                    val read = inputStream.read(fileReader)
                    if (read == -1) {
                        break
                    }
                    outputStream.write(fileReader, 0, read)
                    fileSizeDownloaded += read.toLong()
                    Log.d(
                        "FileUtils",
                        "file download: $fileSizeDownloaded of $fileSize"
                    )
                }
                outputStream.flush()
                true
            } catch (e: IOException) {
                false
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: IOException) {
            false
        }
    }

    fun writeResponseBodyToDisk(
        file: File?,
        futureStudioIconFile: File?
    ): Boolean {
        return try {
            // todo change the file location/name according to your needs
            if (futureStudioIconFile == null) return false
            Log.d("FileUtils", "file path: " + futureStudioIconFile.absolutePath)
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            try {
                val fileReader = ByteArray(4096)
                var fileSizeDownloaded: Long = 0
                inputStream = FileInputStream(file)
                outputStream = FileOutputStream(futureStudioIconFile)
                while (true) {
                    val read = inputStream.read(fileReader)
                    if (read == -1) {
                        break
                    }
                    outputStream.write(fileReader, 0, read)
                    fileSizeDownloaded += read.toLong()
                    Log.d("FileUtils", "file download: $fileSizeDownloaded of ")
                }
                outputStream.flush()
                true
            } catch (e: IOException) {
                false
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: IOException) {
            false
        }
    }

    /**
     * 获取文件大小
     *
     * @param path
     */
    fun getFileSize(path: String): Long {
        var fc: FileChannel? = null
        try {
            val f = File(path)
            if (f.exists() && f.isFile) {
                val fis = FileInputStream(f)
                fc = fis.channel
                L.d(path + "文件大小：")
                L.d(fc.size().toString() + "")
                return fc.size()
            } else {
                L.d("file doesn't exist or is not a file")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (null != fc) {
                try {
                    fc.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return 0
    }

}