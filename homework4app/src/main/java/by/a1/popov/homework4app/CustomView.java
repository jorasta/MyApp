package by.a1.popov.homework4app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class CustomView extends View {

    float x;
    float y;

    private ArrayList<Integer> sectorColors = new ArrayList<Integer>();
    private int circleRadius;
    private int sectorRadius;
    private Paint paintCircle;
    private Paint paintSector1;
    private Paint paintSector2;
    private Paint paintSector3;
    private Paint paintSector4;
    Switch aSwitch;

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
        for (int i = 0; i < colors.length; i++) sectorColors.add(Color.parseColor(colors[i]));
        int circleColor = ta.getColor(R.styleable.CustomView_center_citcle_color, getResources().getColor(R.color.colorCenter));
        circleRadius = ta.getDimensionPixelSize(R.styleable.CustomView_center_citcle_size, (int)getResources().getDimension(R.dimen.circle_radius));
        sectorRadius = ta.getDimensionPixelSize(R.styleable.CustomView_center_citcle_size, (int)getResources().getDimension(R.dimen.sector_radius));
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

    public void shuffleColors(){
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

        float cx, cy;
        cx = getWidth() / 2;
        cy = getHeight() / 2;

        final RectF oval = new RectF();
        oval.set(cx - sectorRadius, cy - sectorRadius,cx + sectorRadius,cy + sectorRadius);
        canvas.drawArc(oval, 0, 90, true, paintSector1);
        canvas.drawArc(oval, 90, 90, true, paintSector2);
        canvas.drawArc(oval, 180, 90, true, paintSector3);
        canvas.drawArc(oval, 270, 90, true, paintSector4);
        canvas.drawCircle(cx,cy,circleRadius,paintCircle);
        buildDrawingCache();
        super.onDraw(canvas);
    }

    @Override
    public boolean onFilterTouchEventForSecurity(MotionEvent event) {
        x = event.getX();
        y = event.getY();

        ConstraintLayout relLayout = (ConstraintLayout) this.getParent();
        aSwitch = (Switch) relLayout.findViewById(R.id.switchView);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                int clr = getDrawingCache().getPixel((int) x, (int) y);;
                int clr2 = getResources().getColor(R.color.colorCenter);
                String textMsg = "Coordinates [x:" + (int)x + "; y:" + (int)y +"]";
                if ((clr != clr2) && (clr != 0))   {
                    if (aSwitch.isChecked()) {

                        Snackbar snackbar = Snackbar.make(relLayout, textMsg, Snackbar.LENGTH_SHORT);
                        View snackbarView = snackbar.getView();
                        TextView textView = (TextView) snackbarView.findViewById(R.id.snackbar_text);
                        textView.setTextColor(clr);
                        snackbar.show();
                        // get current time for new log record
                        Date currentTime = Calendar.getInstance().getTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss", Locale.getDefault());
                        String strDate = sdf.format(currentTime);
                        // add new log record in file snackbarLog.log
                        try {
                            FileWriter fw = new FileWriter(this.getContext().getFilesDir()+"/snackbarLog.log", true);
                            BufferedWriter bw = new BufferedWriter(fw);
                            PrintWriter printWriter = new PrintWriter(bw);
                            printWriter.println(strDate+" - "+textMsg);
                            printWriter.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(relLayout.getContext(), textMsg, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (clr == clr2)
                        this.shuffleColors();
                }
                break;
        }
        return true;
    }
}