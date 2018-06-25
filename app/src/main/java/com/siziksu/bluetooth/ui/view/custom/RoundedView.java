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

public class RoundedView extends View implements RoundedViewContract {

    private static final int PADDING = 5;
    private static final int START_ANGLE = 120;
    private static final int SWEEP_ANGLE = 300;

    private boolean debug = false;

    private int layoutHeight = 0;
    private int layoutWidth = 0;

    private RectF barBounds = new RectF();
    private Paint barPaint = new Paint();
    private int barWidth = 20;
    private int barColor = 0xAA000000;

    private RectF rimBounds = new RectF();
    private Paint rimPaint = new Paint();
    private int rimWidth = 20;
    private int rimColor = 0xAADDDDDD;

    private Paint whitePaint = new Paint();
    private int whiteColor = 0xFFFFFFFF;

    private Paint textPaint = new Paint();
    private int textX;
    private int textY;
    private int textMargin = 0;
    private float textSize = 30;
    private int textStyle = Typeface.NORMAL;
    private int textColor = 0xAA000000;
    private String text = "0";

    private int potNumber = 0;

    private boolean canMove;

    private Paint currentPositionPaint = new Paint();
    private int centerX;
    private int centerY;
    private int radius;
    private int halfRadius;

    private float currentPointXa;
    private float currentPointYa;
    private float currentPointXb;
    private float currentPointYb;

    private Func.Consumer<Void> listener;

    private int validMidiValue;
    private int lastValidMidiValueSent;

    private RoundedViewMaths roundedViewMaths;

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
        roundedViewMaths = new RoundedViewMaths();
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
                    roundedViewMaths.onActionDown(event.getX() - centerX, event.getY() - centerY);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (canMove) {
                    roundedViewMaths.onActionMove(event.getX() - centerX, event.getY() - centerY);
                    setText(roundedViewMaths.value());
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                canMove = false;
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        // Draw the rim
        if (rimWidth > 0) {
            canvas.drawArc(rimBounds, START_ANGLE, SWEEP_ANGLE, false, rimPaint);
        }
        // Draw the bar
        if (barWidth > 0) {
            canvas.drawArc(barBounds, START_ANGLE, roundedViewMaths.value(), false, barPaint);
        }
        // Draw horizontal line
        canvas.drawLine(centerX - halfRadius, centerY, centerX + halfRadius, centerY, whitePaint);
        // Draw current value line
        calculateLinePointsForCurrentValue(roundedViewMaths.value());
        canvas.drawLine(currentPointXb, currentPointYb, currentPointXa, currentPointYa, currentPositionPaint);
        // Draw the text below horizontal line
        calculateTextCoordinates();
        canvas.drawText(text, textX, textY, textPaint);
        if (potNumber != 0) {
            // Draw pot number
            calculatePotTextCoordinates();
            canvas.drawText("Pot " + String.valueOf(potNumber), textX, textY, textPaint);
        }
        if (debug) {
            canvas.drawLine(centerX - halfRadius, centerY, centerX + halfRadius, centerY, whitePaint);
            canvas.drawLine(centerX, centerY - halfRadius, centerX, centerY + halfRadius, whitePaint);
        }
    }

    @Override
    public int getPotId() {
        return getId();
    }

    @Override
    public void setListener(Func.Consumer<Void> listener) {
        this.listener = listener;
    }

    @Override
    public void setValue(int value) {
        text = String.valueOf(value);
        roundedViewMaths.update((value));
    }

    @Override
    public int getValue() {
        return Integer.parseInt(text);
    }

    private void setupBounds() {
        textX = 0;
        textY = 0;
        centerX = (getMeasuredWidth() / 2);
        centerY = (getMeasuredHeight() / 2);

        // Width should equal to Height, find the min value to setup the circle
        int minWidthHeightValue = Math.min(layoutWidth, layoutHeight);
        int maxBarRimValue = Math.max(barWidth, rimWidth) / 2;

        radius = minWidthHeightValue / 2;
        halfRadius = radius / 2;

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
            barPaint.setStrokeCap(Paint.Cap.ROUND);
        }
        if (rimWidth > 0) {
            rimPaint.setAntiAlias(true);
            rimPaint.setColor(rimColor);
            rimPaint.setStrokeWidth(rimWidth);
            rimPaint.setStyle(Paint.Style.STROKE);
            rimPaint.setStrokeCap(Paint.Cap.ROUND);
        }
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, textStyle));
        textPaint.setTextAlign(Paint.Align.CENTER);

        whitePaint.setAntiAlias(true);
        whitePaint.setColor(whiteColor);
        whitePaint.setTextSize(textSize);
        whitePaint.setStrokeWidth(2);
        whitePaint.setTypeface(Typeface.create(Typeface.DEFAULT, textStyle));
        whitePaint.setTextAlign(Paint.Align.CENTER);

        currentPositionPaint.setAntiAlias(true);
        currentPositionPaint.setColor(barColor);
        barPaint.setStyle(Paint.Style.STROKE);
        currentPositionPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void calculateTextCoordinates() {
        textX = centerX;
        textY = (int) (centerY - ((textPaint.descent() + textPaint.ascent()) / 2) + textMargin);
    }

    private void calculatePotTextCoordinates() {
        textY = (int) (centerY - ((textPaint.descent() + textPaint.ascent()) / 2) - textMargin);
    }

    private void calculateLinePointsForCurrentValue(int value) {
        currentPointXa = centerX + (float) ((radius - barWidth) * Math.cos(Math.toRadians(value + START_ANGLE)));
        currentPointYa = centerY + (float) ((radius - barWidth) * Math.sin(Math.toRadians(value + START_ANGLE)));
        currentPointXb = centerX + (float) ((radius - halfRadius) * Math.cos(Math.toRadians(value + START_ANGLE)));
        currentPointYb = centerY + (float) ((radius - halfRadius) * Math.sin(Math.toRadians(value + START_ANGLE)));
    }

    private void setText(int value) {
        validMidiValue = value;
        validMidiValue *= 0.4234;
        text = validMidiValue >= 0 && validMidiValue <= 127 ? String.valueOf(validMidiValue) : text;
        if (lastValidMidiValueSent != validMidiValue) {
            valueChanged();
            lastValidMidiValueSent = validMidiValue;
        }
    }

    private void valueChanged() {
        listener.accept(null);
    }

    /**
     * Parse the attributes passed to the view from the XML
     *
     * @param attributes the attributes to parse
     */
    private void parseAttributes(TypedArray attributes) {
        barColor = attributes.getColor(R.styleable.RoundedView_barColor, barColor);
        barWidth = (int) attributes.getDimension(R.styleable.RoundedView_barWidth, barWidth);
        rimColor = attributes.getColor(R.styleable.RoundedView_rimColor, rimColor);
        rimWidth = (int) attributes.getDimension(R.styleable.RoundedView_rimWidth, rimWidth);
        textMargin = (int) attributes.getDimension(R.styleable.RoundedView_textMargin, textMargin);
        textColor = attributes.getColor(R.styleable.RoundedView_android_textColor, textColor);
        textSize = attributes.getDimension(R.styleable.RoundedView_android_textSize, textSize);
        textStyle = attributes.getInt(R.styleable.RoundedView_android_textStyle, textStyle);
        potNumber = attributes.getInt(R.styleable.RoundedView_potNumber, potNumber);
        attributes.recycle();
    }
}