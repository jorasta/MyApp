package by.a1.popov.mycalculator;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText resultEditText;
    private TableLayout rootLayout;

    private double firstNumber = Double.MIN_VALUE;
    private double secondNumber  = Double.MIN_VALUE;
    private String operator = "";
    private String KEY_FIRST = "FIRST_NUM";
    private String KEY_SCND = "SECOND_NUM";
    private String KEY_OPERTR = "OPERATOR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null){
            firstNumber = savedInstanceState.getDouble(KEY_FIRST);
            secondNumber = savedInstanceState.getDouble(KEY_SCND);
            operator = savedInstanceState.getString(KEY_OPERTR);
        }

        resultEditText = findViewById(R.id.result);
        rootLayout = findViewById(R.id.root_view);
        resultEditText.setEnabled(false);

        // getting display size for sizing buttons
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        int rowCount = rootLayout.getChildCount();
        // running through rows and buttons to set size and onClickListener
        for (int i = 0; i < rowCount; i++) {
            View childView = rootLayout.getChildAt(i);

            if (childView instanceof TableRow) {
                TableRow tableRow = (TableRow) childView;
                int childCount = tableRow.getChildCount();
                for (int j = 0; j < childCount; j++) {
                    childView = tableRow.getChildAt(j);
                    if (childView instanceof Button) {
                        childView.setOnClickListener(this);
                        ((Button) childView).setWidth(width/5);
                        ((Button) childView).setHeight(height/7);
                    }
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putDouble(KEY_FIRST,firstNumber);
        outState.putDouble(KEY_SCND,secondNumber);
        outState.putString(KEY_OPERTR,operator);
        super.onSaveInstanceState(outState);
    }

    private boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }

    @Override
    public void onClick(View view) {

        if (view instanceof Button) {
            Button button = (Button) view;
            String buttonValue = button.getText().toString();

            // button with number
            if (isNumeric(buttonValue)) {
                String currentValue = resultEditText.getText().toString();
                if (isNumeric(currentValue)) {
                    currentValue += buttonValue;
                    resultEditText.setText(currentValue);
                } else {
                    resultEditText.setText(buttonValue);
                }
            // button with operator
            } else {
                if ("+".equals(buttonValue) || "-".equals(buttonValue) || "*".equals(buttonValue) || "/".equals(buttonValue)) {
                    String currentValue = resultEditText.getText().toString();
                    if (isNumeric(currentValue)) {
                        firstNumber = Double.parseDouble(currentValue);
                    } else {
                        firstNumber = Double.MIN_VALUE;
                    }

                    operator = buttonValue;
                    resultEditText.setText(buttonValue);
                // button C to clean all data
                } else if ("c".equals(buttonValue)) {
                    firstNumber = Double.MIN_VALUE;
                    secondNumber = Double.MIN_VALUE;
                    resultEditText.setText("");
                //Button to make result
                } else if ("=".equals(buttonValue)) {
                    String currentValue = resultEditText.getText().toString();
                    if (isNumeric(currentValue)) {
                        secondNumber = Double.parseDouble(currentValue);
                    } else {
                        secondNumber = Double.MIN_VALUE;
                    }

                    double resultNumber = 0;

                    if ("+".equals(operator)) {
                        resultNumber = firstNumber + secondNumber;
                    } else if ("-".equals(operator)) {
                        resultNumber = firstNumber - secondNumber;
                    } else if ("*".equals(operator)) {
                        resultNumber = firstNumber * secondNumber;
                    } else if ("/".equals(operator)) {
                        resultNumber = firstNumber / secondNumber;
                    }
                    // show number without floating format if result is integer
                    resultEditText.setText(resultNumber % 1 != 0 ? String.valueOf(resultNumber) : String.valueOf((int) resultNumber));
                    secondNumber = resultNumber;
                    firstNumber = Double.MIN_VALUE;
                }
            }
        }
    }
}