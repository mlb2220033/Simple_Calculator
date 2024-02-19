package com.example.calculator;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTv, solutionTv;
    MaterialButton btnC, btnOpenBracket, btnCloseBracket;
    MaterialButton btnDivide, btnMultiply, btnPlus, btnMinus, btnEquals;
    MaterialButton btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    MaterialButton btnAC, btnDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        assignId(btnC, R.id.btnC);
        assignId(btnOpenBracket, R.id.btnOpenBracket);
        assignId(btnCloseBracket, R.id.btnCloseBracket);
        assignId(btnDivide, R.id.btnDivede);
        assignId(btnMultiply, R.id.btnMultiply);
        assignId(btnPlus, R.id.btnPlus);
        assignId(btnMinus, R.id.btnMinus);
        assignId(btnEquals, R.id.btnEquals);
        assignId(btn0, R.id.btn0);
        assignId(btn1, R.id.btn1);
        assignId(btn2, R.id.btn2);
        assignId(btn3, R.id.btn3);
        assignId(btn4, R.id.btn4);
        assignId(btn5, R.id.btn5);
        assignId(btn6, R.id.btn6);
        assignId(btn7, R.id.btn7);
        assignId(btn8, R.id.btn8);
        assignId(btn9, R.id.btn9);
        assignId(btnAC, R.id.btnAC);
        assignId(btnDot, R.id.btnDot);
    }

    void assignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MaterialButton btn = (MaterialButton) v;
        String btnText = btn.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();
        if (btnText.equals("AC")) {
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }
        if (btnText.equals("=")) {
            resultTv.setText(resultTv.getText());
            return;
        }
        if (btnText.equals("C")) {
            dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
        } else {
            dataToCalculate = dataToCalculate + btnText;
        }
        solutionTv.setText(dataToCalculate);
        String finalResult = getResult(dataToCalculate);

        if (!finalResult.equals("Err")) {
            resultTv.setText(finalResult);
        }
    }

    String getResult(String data) {
        try {
            org.mozilla.javascript.Context rhinoContext = org.mozilla.javascript.Context.enter();
            rhinoContext.setOptimizationLevel(-1);
            Scriptable scriptable = rhinoContext.initStandardObjects();
            String finalResult = rhinoContext.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "Err";
        } finally {
            org.mozilla.javascript.Context.exit();
        }
    }
}
