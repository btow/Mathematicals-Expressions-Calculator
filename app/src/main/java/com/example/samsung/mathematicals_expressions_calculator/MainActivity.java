package com.example.samsung.mathematicals_expressions_calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.samsung.mathematicals_expressions_calculator.RPN.*;

public class MainActivity extends AppCompatActivity {

    EditText etExpr;
    Button btnAbove, btnOne, btnTwo, btnThree, btnClear,
            btnFor, btnFive, btnSix, btnAdd,
            btnSeven, btnEight, btnNine, btnRem,
            btnZero, btnLeftBracket, btnRightBracket, btnMult,
            btnPrev, btnBrsps, btnNext, btnDiv;
    TextView tvResult;
    int cursorsPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etExpr = (EditText) findViewById(R.id.etExpr);
        etExpr.setRawInputType(InputType.TYPE_NULL);

        btnAbove = (Button) findViewById(R.id.btnAbove);
        tvResult = (TextView) findViewById(R.id.tvResult);

        btnOne = (Button) findViewById(R.id.btnOne);
        btnTwo = (Button) findViewById(R.id.btnTwo);
        btnThree = (Button) findViewById(R.id.btnThree);
        btnFor = (Button) findViewById(R.id.btnFor);
        btnFive = (Button) findViewById(R.id.btnFive);
        btnSix = (Button) findViewById(R.id.btnSix);
        btnSeven = (Button) findViewById(R.id.btnSeven);
        btnEight = (Button) findViewById(R.id.btnEight);
        btnNine = (Button) findViewById(R.id.btnNine);
        btnZero = (Button) findViewById(R.id.btnZero);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnRem = (Button) findViewById(R.id.btnRem);
        btnMult = (Button) findViewById(R.id.btnMult);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        btnLeftBracket = (Button) findViewById(R.id.btnLeftBracket);
        btnRightBracket = (Button) findViewById(R.id.btnRightBracket);
        btnPrev = (Button) findViewById(R.id.btnPrev);
        btnBrsps = (Button) findViewById(R.id.btnBrsps);
        btnNext = (Button) findViewById(R.id.btnNext);

    }

    /**
     * The method removes the hint if it's there, and then sets a black text color.
     */
    private void setColorEtExpr() {

        String s = etExpr.getText().toString();

        if (s.equals(getResources().getString(R.string.enter_a_mathematical_expression))) {
            etExpr.setTextColor(getResources().getColor(R.color.colorPlainText));
            etExpr.setText("");
            cursorsPosition = 0;
            etExpr.setCursorVisible(false);
        } else {
            etExpr.setSelection(cursorsPosition);
        }

    }

    /**
     * Method is called when you press "above" and refers to the static class method "RPN",
     * which calculates the expression from the field "etExp".
     * The result is written in the "tvResult".
     * @param view - button "above"
     */
    public void resultExpr(View view) {
        try {
            tvResult.setText(eval(etExpr.getText().toString()));
        } catch (ExpressionCharException e) {
            tvResult.setText(e.getMessage());
            Toast.makeText(this, "Error in expression", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * The method is a handler for the keystrokes made on the keyboard.
     * @param view - button is pressed.
     */
    public void inputChar(View view) {

        setColorEtExpr();

        switch (view.getId()) {
            case R.id.btnOne :
                setEtExpr(R.string._1);
                break;
            case R.id.btnTwo :
                setEtExpr(R.string._2);
                break;
            case R.id.btnThree :
                setEtExpr(R.string._3);
                break;
            case R.id.btnFor :
                setEtExpr(R.string._4);
                break;
            case R.id.btnFive :
                setEtExpr(R.string._5);
                break;
            case R.id.btnSix :
                setEtExpr(R.string._6);
                break;
            case R.id.btnSeven :
                setEtExpr(R.string._7);
                break;
            case R.id.btnEight :
                setEtExpr(R.string._8);
                break;
            case R.id.btnNine :
                setEtExpr(R.string._9);
                break;
            case R.id.btnZero :
                setEtExpr(R.string._0);
                break;
            case R.id.btnClear :
                clearEtExpr();
                break;
            case R.id.btnAdd :
                setEtExpr(R.string.add);
                break;
            case R.id.btnRem :
                setEtExpr(R.string.rem);
                break;
            case R.id.btnMult :
                setEtExpr(R.string.mult);
                break;
            case R.id.btnDiv :
                setEtExpr(R.string.div);
                break;
            case R.id.btnLeftBracket :
                setEtExpr(R.string.left_bracket);
                break;
            case R.id.btnRightBracket :
                setEtExpr(R.string.right_bracket);
                break;
            case R.id.btnPrev :
                if (cursorsPosition != 0) {
                    cursorsPosition--;
                }
                etExpr.setSelection(cursorsPosition);
                break;
            case R.id.btnNext :
                if (cursorsPosition != (etExpr.getText().toString().length()) - 1) {
                    cursorsPosition++;
                }
                etExpr.setSelection(cursorsPosition);
                break;
            case R.id.btnBrsps :
                if (cursorsPosition != 0) {
                    setEtExpr(R.string.brsps);
                }
                break;
        }
        etExpr.setSelection(cursorsPosition);
        etExpr.setCursorVisible(true);
    }

    /**
     * The method clears the expression string and creates in it a hint.
     */
    private void clearEtExpr() {
        etExpr.setText(getResources().getString(R.string.enter_a_mathematical_expression));
        etExpr.setTextColor(getResources().getColor(R.color.colorAccent));
        cursorsPosition = 0;
        tvResult.setText(getResources().getString(R.string.click_the_button_and_here_you_will_see_the_result_of_the_expression));
    }

    /**
     * The method makes changes to an editable string field "etExpr".
     * @param btnString - button is pressed ID
     */
    private void setEtExpr(int btnString) {
        String s0 = etExpr.getText().toString(),
        /**Если нажата любая кнопка, кроме "btnBrsps", "btnPrev" или "btnNext",
         * то вставляем символ с этой кнопки в позицию курсора со сдвигом остальной строки вправо
          */
            addChar = btnString != R.string.brsps ? getResources().getString(btnString).toString(): "";
        char[] allChars = null, prefChars = null, sufChars = null;
        Index position = new Index(cursorsPosition);
        //Если нажата кнопка "btnBrsps", то включаем режим замены символа строки в позиции курсора;
        etExpr.setText(insertSubString(s0,addChar,position,btnString == R.string.brsps));
        cursorsPosition = position.getIndex();
        etExpr.setSelection(cursorsPosition);
    }

}
