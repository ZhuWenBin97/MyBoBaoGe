package com.zcy.hnkjxy.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by admin on 2017/10/6.
 */

public class IconFontTextView extends android.support.v7.widget.AppCompatTextView {

    private static final String TAG = "TAG";
    String value;
    String fontFile;

    public IconFontTextView(Context context) {
        super(context);
    }

    public IconFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs == null) {
            return;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IconFontTextView);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.IconFontTextView_value) {
                value = a.getString(attr);
                setText(value);
                Log.d(TAG, "value : " + value);

            } else if (attr == R.styleable.IconFontTextView_fontFile) {
                fontFile = a.getString(attr);
                Log.d(TAG, "fontFile : " + fontFile);
                try {
                    Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontFile);
                    setTypeface(typeface);
                } catch (Throwable e) {

                }

            }
        }
        a.recycle();
    }
}
