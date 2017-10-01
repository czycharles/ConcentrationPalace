package com.example.charles.concentrationpalace;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Return the clicked item in the screen.
 */

public class FindSelectedItem extends FrameLayout {



    private int width = -1;

    private int height = -1;

    private Bitmap bitmap;



    public FindSelectedItem(Context context) {

        super( context);

    }



    public FindSelectedItem(Context context, AttributeSet attrs, int defStyle) {

        super( context, attrs, defStyle);

    }



    public FindSelectedItem(Context context, AttributeSet attrs) {

        super( context, attrs);

    }



    @Override

    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();


        if(action != MotionEvent.ACTION_DOWN) {

            return super.onTouchEvent( event);

        }

        int x = (int)event.getX();

        int y = (int)event.getY();

        if(width == -1 || height == -1) {

            Drawable drawable = getBackground().getCurrent();

            bitmap = ((BitmapDrawable)drawable).getBitmap();

            width = getWidth();

            height = getHeight();

            int newWidth = bitmap.getWidth();
            int newHeight = bitmap.getHeight();

            // 计算缩放比例
            float scaleWidth = 1/(((float) newWidth) / width);
            float scaleHeight = 1/(((float) newHeight) / height);
            // 取得想要缩放的matrix参数
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // 得到新的图片
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, newWidth, newHeight, matrix, true);

        }

        if(null == bitmap || x < 0 || y < 0 || x >= width || y >= height) {

            return false;

        }

        int pixel = bitmap.getPixel( x, y);

        int[] rgb = new int[3];
        rgb[0] = (pixel & 0xff0000) >> 16;
        rgb[1] = (pixel & 0xff00) >> 8;
        rgb[2] = (pixel & 0xff);
        Log.d("special", x + " and " + y +" clicked. Pixel is " + rgb[0] + ","
                + rgb[1] + "," + rgb[2] + " w and H is " + bitmap.getWidth() + "," + bitmap.getHeight());

//        if(Color.TRANSPARENT == pixel) {
//
//            return false;
//
//        }
        return (Color.TRANSPARENT != pixel) && (super.onTouchEvent(event));

        //return super.onTouchEvent( event);

    }

}
