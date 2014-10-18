package com.hotveryspicy.maskimage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class MainActivity extends Activity implements OnClickListener {
    ImageView mImageView;
    ImageView mImageView2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // RUNTIME
        mImageView = (ImageView) findViewById(R.id.imageview_id);

        mImageView2 = (ImageView) findViewById(R.id.imageview_id2);

        Button yo_button = (Button) findViewById(R.id.yo_button);
        yo_button.setOnClickListener(this);

    }

    // Method of creating mask runtime
    public void makeMaskImageScaleFit(ImageView mImageView, int mContent) {
        // Get mask bitmap
        Bitmap mask =
                BitmapFactory.decodeResource(getResources(), R.drawable.chat_input_fri_default);

        // Scale imageView and it's bitmap to the size of the mask
        mImageView.getLayoutParams().width = mask.getWidth();
        mImageView.getLayoutParams().height = mask.getHeight();

        // Get bitmap from ImageView and store into 'original'
        mImageView.setDrawingCacheEnabled(true);
        mImageView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        mImageView.layout(0, 0, mImageView.getMeasuredWidth(), mImageView.getMeasuredHeight());
        mImageView.buildDrawingCache(true);
        // Bitmap original = mImageView.getDrawingCache();
        Bitmap original = BitmapFactory.decodeResource(getResources(), mContent);

        // Scale that bitmap
        Bitmap original_scaled =
                Bitmap.createScaledBitmap(original, mImageView.getWidth(), mImageView.getHeight(),
                        false);
        mImageView.setImageBitmap(original_scaled);
        mImageView.setDrawingCacheEnabled(false);

        // Create result bitmap
        Bitmap result =
                Bitmap.createBitmap(mImageView.getWidth(), mImageView.getHeight(), Config.ARGB_8888);

        // Perform masking
        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawBitmap(original_scaled, 0, 0, null);


        Rect src = new Rect(0, 0, mask.getWidth() / 2, mask.getHeight() / 2);
        Rect dst = new Rect(0, 0, mask.getWidth() / 2, mask.getHeight() / 2);
        Rect src2 = new Rect(mask.getWidth() / 2, 0, mask.getWidth(), mask.getHeight() / 2);
        Rect dst2 =
                new Rect(mImageView.getWidth() - mask.getWidth(), 0, mImageView.getWidth(),
                        mask.getHeight() / 2);
        Rect src3 = new Rect(0, mask.getHeight() / 2, mask.getWidth() / 2, mask.getHeight());
        Rect dst3 =
                new Rect(0, mImageView.getHeight() - mask.getHeight() / 2, mask.getWidth() / 2,
                        mImageView.getHeight());
        Rect src4 =
                new Rect(mask.getWidth() / 2, mask.getHeight() / 2, mask.getWidth(),
                        mask.getHeight());
        Rect dst4 =
                new Rect(mImageView.getWidth() - mask.getWidth() / 2, mImageView.getHeight()
                        - mask.getHeight() / 2, mImageView.getWidth(), mImageView.getHeight());
        mCanvas.drawBitmap(mask, src, dst, paint);
        mCanvas.drawBitmap(mask, src2, dst2, paint);
        mCanvas.drawBitmap(mask, src3, dst3, paint);
        mCanvas.drawBitmap(mask, src4, dst4, paint);
        // mCanvas.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);

        // Set imageView to 'result' bitmap
        mImageView.setImageBitmap(result);
        mImageView.setScaleType(ScaleType.FIT_XY);

        // Make background transparent
        mImageView.setBackgroundResource(android.R.color.transparent);
    }

    // Method of creating mask runtime
    public void makeMaskImage(ImageView mImageView, int mContent) {
        Bitmap original = BitmapFactory.decodeResource(getResources(), mContent);
        Bitmap mask = BitmapFactory.decodeResource(getResources(), R.drawable.custom_shape);
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

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.yo_button:
                makeMaskImageScaleFit(mImageView, R.drawable.nature);
                mImageView2.setVisibility(View.GONE);
                // makeMaskImage(mImageView2, R.drawable.nature);

                break;

            default:
                break;
        }
    }
}
