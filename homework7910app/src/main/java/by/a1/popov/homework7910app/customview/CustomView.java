package by.a1.popov.homework7910app.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;

import by.a1.popov.homework7910app.R;

public class CustomView extends View {

    private ArrayList<Integer> sectorColors = new ArrayList<>();
    private int circleRadius;
    private int sectorRadius;
    private Paint paintCircle;
    private Paint paintSector1;
    private Paint paintSector2;
    private Paint paintSector3;
    private Paint paintSector4;

    public CustomView(Context context) {
        super(context);
        setFocusable(true);
        this.setDrawingCacheEnabled(true);
        init(null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        this.setDrawingCacheEnabled(true);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFocusable(true);
        this.setDrawingCacheEnabled(true);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setFocusable(true);
        this.setDrawingCacheEnabled(true);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {

        if (set == null)
            return;

        TypedArray ta = getContext().obtainStyledAttributes(set, R.styleable.CustomView);
        String[] colors = getResources().getStringArray(R.array.sectorColors);
        for (String color : colors) {
            sectorColors.add(Color.parseColor(color));
        }
        int circleColor = ta.getColor(R.styleable.CustomView_center_citcle_color,
                ContextCompat.getColor(getContext(), R.color.colorCenter));
        circleRadius = ta.getDimensionPixelSize(R.styleable.CustomView_center_citcle_size, (int) getResources().getDimension(R.dimen.circle_radius));
        sectorRadius = ta.getDimensionPixelSize(R.styleable.CustomView_center_citcle_size, (int) getResources().getDimension(R.dimen.sector_radius));
        paintSector1 = new Paint();
        paintSector1.setAntiAlias(true);
        paintSector1.setColor(sectorColors.get(0));
        paintSector1.setStyle(Paint.Style.FILL);
        paintSector2 = new Paint();
        paintSector2.setAntiAlias(true);
        paintSector2.setColor(sectorColors.get(1));
        paintSector2.setStyle(Paint.Style.FILL);
        paintSector3 = new Paint();
        paintSector3.setAntiAlias(true);
        paintSector3.setColor(sectorColors.get(2));
        paintSector3.setStyle(Paint.Style.FILL);
        paintSector4 = new Paint();
        paintSector4.setAntiAlias(true);
        paintSector4.setColor(sectorColors.get(3));
        paintSector4.setStyle(Paint.Style.FILL);
        paintCircle = new Paint();
        paintCircle.setAntiAlias(true);
        paintCircle.setColor(circleColor);
        ta.recycle();
    }

    public void shuffleColors() {
        Collections.shuffle(sectorColors);
        paintSector1.setColor(sectorColors.get(0));
        paintSector2.setColor(sectorColors.get(1));
        paintSector3.setColor(sectorColors.get(2));
        paintSector4.setColor(sectorColors.get(3));
        destroyDrawingCache();
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float cx, cy;
        cx = getWidth() / 2;
        cy = getHeight() / 2;

        final RectF oval = new RectF();
        oval.set(cx - sectorRadius, cy - sectorRadius, cx + sectorRadius, cy + sectorRadius);
        canvas.drawArc(oval, 0, 90, true, paintSector1);
        canvas.drawArc(oval, 90, 90, true, paintSector2);
        canvas.drawArc(oval, 180, 90, true, paintSector3);
        canvas.drawArc(oval, 270, 90, true, paintSector4);
        canvas.drawCircle(cx, cy, circleRadius, paintCircle);
        buildDrawingCache();
    }

    public int getPxlColor(float x, float y) {
        return getDrawingCache().getPixel((int) x, (int) y);
    }
}