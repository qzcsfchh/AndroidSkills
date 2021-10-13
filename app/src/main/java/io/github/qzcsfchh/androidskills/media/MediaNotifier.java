package io.github.qzcsfchh.androidskills.media;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.Arrays;

/**
 * Refresh album immediately after taking/deleting a photo on device.
 *
 * @author huanghao
 * @version v1.0
 * @since 2021/10/13 20:14
 * @see <a href='https://blog.csdn.net/plokmju88/article/details/80083566'>三种方法，刷新 Android 的 MediaStore！让你保存的图片立即出现在相册里！</a>
 */
public class MediaNotifier {
    private static final String TAG = "MediaNotifier";

    /**
     * 将图片保存至 Picture 目录下，同时向 {@link MediaStore} 中插入图片数据，并产生一个缩略图。
     * @param context
     * @param bitmap
     * @return 返回图片保存路径
     */
    public static String notifyInsert(Context context, Bitmap bitmap){
        return MediaStore.Images.Media.insertImage(context.getContentResolver(),bitmap,"image_file","insert image file");
    }

    /**
     * 通过广播通知{@link MediaStore}进行刷新
     * @param context
     * @param uri 文件绝对路径，必须是以 {@link Environment#getExternalStorageDirectory()} 方法的返回值开头
     */
    public static void notifyInsertByBroadcast(Context context, Uri uri) {
        // Android 4.4 之后，这个广播只能由系统进行广播，App 只能对该广播进行监听
//        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED ,uri));
        // 新系统采用这个广播
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public static void notifyDelete(Context context, Uri uri){
        context.getContentResolver().delete(uri, null);
    }


    /**
     *
     * @param context
     * @param paths
     * @param mimeTypes 可以不传，但不能传通配符
     * @param callback
     */
    public static void notifyInsert(Context context, String[] paths, String[] mimeTypes, MediaScannerConnection.OnScanCompletedListener callback) {
        MediaScannerConnection.scanFile(context, paths, mimeTypes, callback == null ? emptyListener() : callback);
    }

    public static void notifyInsertImage(Context context, String[] paths, MediaScannerConnection.OnScanCompletedListener callback) {
        String[] mimeTypes = new String[paths.length];
        Arrays.fill(mimeTypes, "image/jpeg");
        MediaScannerConnection.scanFile(context, paths, mimeTypes, callback == null ? emptyListener() : callback);
    }

    private static MediaScannerConnection.OnScanCompletedListener emptyListener(){
        return new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String path, Uri uri) {
                Log.d(TAG, "onScanCompleted() called with: path = [" + path + "], uri = [" + uri + "]");
            }
        };
    }
}
