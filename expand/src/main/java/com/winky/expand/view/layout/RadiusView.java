package com.winky.expand.view.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

public class RadiusView extends android.support.v7.widget.AppCompatImageView {

    public RadiusView(Context context) {
        this(context, null);
    }

    public RadiusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadiusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //控件矩形 除开padding
    private final RectF rectF = new RectF();
    //遮罩层画笔
    private final Paint maskPaint = new Paint();
    //中心部分画笔
    private final Paint corePaint = new Paint();

    private float angle = 30.0f;

    private void init() {
        maskPaint.setAntiAlias(true);//去边锯齿
//        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        corePaint.setAntiAlias(true);
        corePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        rectF.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (angle <= 0) {
            return;
        }
        canvas.saveLayer(rectF, corePaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawRoundRect(rectF, angle, angle, corePaint);
//        canvas.saveLayer(rectF, maskPaint, Canvas.ALL_SAVE_FLAG);
    }
}
