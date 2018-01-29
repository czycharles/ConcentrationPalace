package com.example.charles.concentrationpalace;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;


class ScreenShot {

    /**
     * 获取指定Activity的截屏，保存到png文件。
     */

    private static Bitmap takeScreenShot(Activity activity) {
        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        //Log.d("TAG", "图片：" + b1);
        // 获取屏幕长和高
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        // 去掉标题栏
        // Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }
    // 保存到sdcard
    private static void savePic(Bitmap b, String appDir, String fileName) {

        File file = new File(appDir, fileName);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            b.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            //Log.d("savePic","success1");
        }
    }
    // 程序入口
    static void shoot(Activity a,String fileName) {
        File directory = new File(Environment.getExternalStorageDirectory().getPath());
        if(!directory.exists()){
            if(!directory.mkdir())
                Toast.makeText(a,R.string.share_path_fail,Toast.LENGTH_LONG).show();//没有目录先创建目录
        }
        ScreenShot.savePic(ScreenShot.takeScreenShot(a), Environment.getExternalStorageDirectory().getPath(),fileName);
    }
}
