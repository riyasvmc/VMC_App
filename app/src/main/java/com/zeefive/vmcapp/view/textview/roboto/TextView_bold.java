package com.zeefive.vmcapp.view.textview.roboto;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextView_bold extends TextView{

	public TextView_bold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextView_bold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextView_bold(Context context) {
        super(context);
        init();
    }
	private void init() {
        if (!isInEditMode()) {
            Typeface typeFace = Typeface.createFromAsset(getContext().getAssets(),
                    "fonts/roboto/bold.ttf");
            setTypeface(typeFace);
        }
    }
}