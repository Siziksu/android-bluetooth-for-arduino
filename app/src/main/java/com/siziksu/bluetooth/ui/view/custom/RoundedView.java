package com.siziksu.bluetooth.ui.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.siziksu.bluetooth.R;
import com.siziksu.bluetooth.common.function.Func;

public class RoundedView extends View {

    private static final int PADDING = 5;

    private int layoutHeight = 0;
    private int layoutWidth = 0;

    private int barWidth = 20;
    private int barColor = 0xAA000000;
    private int rimWidth = 20;
    private int rimColor = 0xAADDDDDD;

    private RectF barBounds = new RectF();
    private Paint barPaint = new Paint();
    private RectF rimBounds = new RectF();
    private Paint rimPaint = new Paint();

    private Paint textPaint = new Paint();

    private int textX;
    private int textY;
    private float textSize = 30;
    private int textStyle = Typeface.NORMAL;
    private int textColor = 0xAA000000;
    private String text = "0";

    private boolean canMove;
    private int centerX;
    private int centerY;
    private float degree = 0;
    private float dx;
    private float dy;
    private int validDegree;
    private int validMidiValue;
    private int lastValidMidiValueSent;

    private Func.Consumer<Integer> listener;

    public RoundedView(Context context) {
        super(context);
        init(context, null);
    }

    public RoundedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundedView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            parseAttributes(context.obtainStyledAttributes(attrs, R.styleable.RoundedView));
        }
    }

    /**
     * Use onSizeChanged instead of onAttachedToWindow to get the dimensions of the view,
     * because this method is called after measuring the dimensions of MATCH_PARENT & WRAP_CONTENT.
     * Use this dimensions to setup the bounds and paints.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        layoutWidth = w;
        layoutHeight = h;
        setupBounds();
        setupPaints();
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!canMove) {
                    canMove = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (canMove) {
                    dx = event.getX() - centerX;
                    dy = event.getY() - centerY;
                    calculateDegree();
                }
                break;
            case MotionEvent.ACTION_UP:
                canMove = false;
                break;
            default:
                break;
        }
        if (canMove) {
            invalidate();
        }
        return true;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        // Draw the rim
        if (rimWidth > 0) {
            canvas.drawArc(rimBounds, 120, 300, false, rimPaint);
        }
        // Draw the bar
        if (barWidth > 0) {
            canvas.drawArc(barBounds, 120, validDegree, false, barPaint);
        }
        // Draw the text
        textX = (getMeasuredWidth() / 2);
        textY = (int) ((getMeasuredHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2));
        canvas.drawText(text, textX, textY, textPaint);
    }

    public void setListener(Func.Consumer<Integer> listener) {
        this.listener = listener;
    }

    public void setValue(int value) {
        degree = value / 0.4233333333333333f;
        text = String.valueOf(value);
    }

    public int getValue() {
        return Integer.parseInt(text);
    }

    private void setupBounds() {
        textX = 0;
        textY = 0;
        centerX = layoutHeight / 2;
        centerY = layoutWidth / 2;

        // Width should equal to Height, find the min value to setup the circle
        int minWidthHeightValue = Math.min(layoutWidth, layoutHeight);
        int maxBarRimValue = Math.max(barWidth, rimWidth) / 2;

        // To compensate the values when the view is not a square
        int h = (layoutHeight < layoutWidth ? layoutWidth - layoutHeight : 0) / 2;
        int v = (layoutWidth < layoutHeight ? layoutHeight - layoutWidth : 0) / 2;

        int left, top, right, bottom;

        left = h + PADDING + rimWidth / 2;
        top = v + PADDING + rimWidth / 2;
        right = h - PADDING - rimWidth / 2 + minWidthHeightValue;
        bottom = v - PADDING - rimWidth / 2 + minWidthHeightValue;
        rimBounds = new RectF(left, top, right, bottom);

        left = h + PADDING + maxBarRimValue;
        top = v + PADDING + maxBarRimValue;
        right = h - PADDING - maxBarRimValue + minWidthHeightValue;
        bottom = v - PADDING - maxBarRimValue + minWidthHeightValue;
        barBounds = new RectF(left, top, right, bottom);
    }

    private void setupPaints() {
        if (barWidth > 0) {
            barPaint.setAntiAlias(true);
            barPaint.setColor(barColor);
            barPaint.setStrokeWidth(barWidth);
            barPaint.setStyle(Paint.Style.STROKE);
        }
        if (rimWidth > 0) {
            rimPaint.setAntiAlias(true);
            rimPaint.setColor(rimColor);
            rimPaint.setStrokeWidth(rimWidth);
            rimPaint.setStyle(Paint.Style.STROKE);
        }
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, textStyle));
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    private void calculateDegree() {
        degree = (float) Math.toDegrees(Math.atan2(dy, dx));
        degree = (degree < 0) ? degree + 360 : degree;
        degree -= 90;
        degree = (degree < 0) ? 360 + degree : degree;
        degree -= 30;
        validDegree = degree >= 0 && degree <= 318 ? (int) degree : validDegree;
        validDegree = validDegree > 300 ? 300 : validDegree;
        setText();
    }

    private void setText() {
        validMidiValue = validDegree;
        validMidiValue *= 0.4234;
        text = validMidiValue >= 0 && validMidiValue <= 127 ? String.valueOf(validMidiValue) : text;
        if (lastValidMidiValueSent != validMidiValue) {
            sendValue();
            lastValidMidiValueSent = validMidiValue;
        }
    }

    private void sendValue() {
        listener.accept(Integer.parseInt(text));
    }

    /**
     * Parse the attributes passed to the view from the XML
     *
     * @param attributes the attributes to parse
     */
    private void parseAttributes(TypedArray attributes) {
        barColor = attributes.getColor(R.styleable.RoundedView_barColor, barColor);
        barWidth = (int) attributes.getDimension(R.styleable.RoundedView_barWidth, barWidth);
        rimWidth = (int) attributes.getDimension(R.styleable.RoundedView_rimWidth, rimWidth);
        rimColor = attributes.getColor(R.styleable.RoundedView_rimColor, rimColor);
        textColor = attributes.getColor(R.styleable.RoundedView_android_textColor, textColor);
        textSize = attributes.getDimension(R.styleable.RoundedView_android_textSize, textSize);
        textStyle = attributes.getInt(R.styleable.RoundedView_android_textStyle, textStyle);
        attributes.recycle();
    }
}