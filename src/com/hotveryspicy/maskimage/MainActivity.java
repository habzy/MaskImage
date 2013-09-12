package com.hotveryspicy.maskimage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.view.View.MeasureSpec;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // RUNTIME
        ImageView mImageView = (ImageView) findViewById(R.id.imageview_id);
        makeMaskImageScaleFit(mImageView, R.drawable.nature);
        
        ImageView mImageView2 = (ImageView) findViewById(R.id.imageview_id2);
        makeMaskImage(mImageView2, R.drawable.nature);
    }

    // Method of creating mask runtime
    public void makeMaskImageScaleFit(ImageView mImageView, int mContent) {
        // Get mask bitmap
        Bitmap mask = BitmapFactory.decodeResource(getResources(), R.drawable.custom_shape);

        //Scale imageView and it's bitmap to the size of the mask
        mImageView.getLayoutParams().width = mask.getWidth();
        mImageView.getLayoutParams().height = mask.getHeight();
        
        // Get bitmap from ImageView and store into 'original'
        mImageView.setDrawingCacheEnabled(true);
        mImageView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        mImageView.layout(0, 0, mImageView.getMeasuredWidth(), mImageView.getMeasuredHeight());
        mImageView.buildDrawingCache(true);
        Bitmap original = mImageView.getDrawingCache();
        
        // Scale that bitmap 
        Bitmap original_scaled = Bitmap.createScaledBitmap(original, mask.getWidth(), mask.getHeight(), false);
        mImageView.setImageBitmap(original_scaled);
        mImageView.setDrawingCacheEnabled(false);
        
        // Create result bitmap
        Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Config.ARGB_8888);
        
        // Perform masking
        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawBitmap(original_scaled, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);
        
        // Set imageView to 'result' bitmap
        mImageView.setImageBitmap(result);
        mImageView.setScaleType(ScaleType.FIT_XY);

        // Make background transparent
        mImageView.setBackgroundResource(android.R.color.transparent);
    }
    
  // Method of creating mask runtime
    public void makeMaskImage(ImageView mImageView, int mContent)
    {
        Bitmap original = BitmapFactory.decodeResource(getResources(), mContent);
        Bitmap mask = BitmapFactory.decodeResource(getResources(),R.drawable.custom_shape);
        Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Config.ARGB_8888);
        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawBitmap(original, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);
        mImageView.setImageBitmap(result);
        mImageView.setScaleType(ScaleType.CENTER);
        mImageView.setBackgroundResource(android.R.color.transparent);
    }
}
