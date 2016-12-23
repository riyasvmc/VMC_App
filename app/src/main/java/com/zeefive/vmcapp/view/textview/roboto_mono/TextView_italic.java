package com.zeefive.vmcapp.view.textview.roboto_mono;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextView_italic extends TextView{

	public TextView_italic(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextView_italic(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextView_italic(Context context) {
        super(context);
        init();
    }
	private void init() {
        if (!isInEditMode()) {
            Typeface typeFace = Typeface.createFromAsset(getContext().getAssets(),
                    "fonts/roboto_mono/italic.ttf");
            setTypeface(typeFace);
        }
    }
}
