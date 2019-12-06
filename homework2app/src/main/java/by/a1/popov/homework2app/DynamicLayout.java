package by.a1.popov.homework2app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class DynamicLayout extends LinearLayout {
    public DynamicLayout(Context context) {
        super(context);
    }

    public DynamicLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DynamicLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void addItem(String name, String contact, int resId){
        // Добавляем новое View из getView
        addView(getView(name,contact,resId));
    }

    private View getView(String name, String contact, int resId){
        /*
         * Создаем разметку в памяти, чтобы потом наполнить ее данными и добавить в контейнер
         * https://developer.android.com/reference/android/view/LayoutInflater
         */
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_contact_view, null, true);

        ((TextView)view.findViewById(R.id.textViewName)).setText(name);
        ((TextView)view.findViewById(R.id.textViewContact)).setText(contact);
        ((ImageView)view.findViewById(R.id.imageView)).setImageResource(resId);
        return view;
    }
}
