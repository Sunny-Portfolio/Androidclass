package com.example.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import org.apache.commons.lang3.StringUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;


import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView primaryScreen, secondaryScreen;
    MaterialButton button_MS, button_MR, button_mPlus, button_mMinus;
    MaterialButton button_AC, button_C, button_MC;
    MaterialButton button_Divide, button_Multiply, button_Plus, button_Minus;
    MaterialButton button_0, button_1, button_2, button_3, button_4;
    MaterialButton button_5, button_6, button_7, button_8, button_9;
    MaterialButton button_Decimal, button_Back, button_Equals;

    String currentEntry = "";
    String previousEntry = "";
    String currentOperator = "";
    String lastOperator = "";
    String secondLastOperator = "";
    int openParentheses = 0;
    boolean openDecimal = false;

    double result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        primaryScreen = (TextView) findViewById(R.id.textView_primary);
        secondaryScreen = (TextView) findViewById(R.id.textView_secondary);

//        button_MS = (MaterialButton) findViewById(R.id.oper_btn1);
//        button_MR = (MaterialButton) findViewById(R.id.oper_btn2);
//        button_mPlus = (MaterialButton) findViewById(R.id.oper_btn3);
//        button_mMinus = (MaterialButton) findViewById(R.id.oper_btn4);
//
//        button_AC = (MaterialButton) findViewById(R.id.oper_btn5);
//        button_C = (MaterialButton) findViewById(R.id.oper_btn6);
//        button_MC = (MaterialButton) findViewById(R.id.oper_btn7);
//        button_Divide = (MaterialButton) findViewById(R.id.oper_btn8);
//
//        button_Multiply = (MaterialButton) findViewById(R.id.oper_btn9);
//        button_Minus = (MaterialButton) findViewById(R.id.oper_btn10);
//        button_Plus = (MaterialButton) findViewById(R.id.oper_btn11);
//        button_Equals = (MaterialButton) findViewById(R.id.oper_btn12);
//
//        button_Back = (MaterialButton) findViewById(R.id.oper_btn13);
//        button_Decimal = (MaterialButton) findViewById(R.id.oper_btn14);

//        button_0 = (MaterialButton) findViewById(R.id.digi_btn0);
//        button_1 = (MaterialButton) findViewById(R.id.digi_btn1);
//        button_2 = (MaterialButton) findViewById(R.id.digi_btn2);
//        button_3 = (MaterialButton) findViewById(R.id.digi_btn3);
//        button_4 = (MaterialButton) findViewById(R.id.digi_btn4);
//        button_5 = (MaterialButton) findViewById(R.id.digi_btn5);
//        button_6 = (MaterialButton) findViewById(R.id.digi_btn6);
//        button_7 = (MaterialButton) findViewById(R.id.digi_btn7);
//        button_8 = (MaterialButton) findViewById(R.id.digi_btn8);
//        button_9 = (MaterialButton) findViewById(R.id.digi_btn9);

        setupButton(button_MS, R.id.oper_btn1);
        setupButton(button_MR, R.id.oper_btn2);
        setupButton(button_mPlus, R.id.oper_btn3);
        setupButton(button_mMinus, R.id.oper_btn4);

        setupButton(button_AC, R.id.oper_btn5);
        setupButton(button_C, R.id.oper_btn6);
        setupButton(button_MC, R.id.oper_btn7);
        setupButton(button_Divide, R.id.oper_btn8);

        setupButton(button_Multiply, R.id.oper_btn9);
        setupButton(button_Minus, R.id.oper_btn10);
        setupButton(button_Plus, R.id.oper_btn11);
        setupButton(button_Equals, R.id.oper_btn12);

        setupButton(button_Back, R.id.oper_btn13);
        setupButton(button_Decimal, R.id.oper_btn14);

        setupButton(button_0, R.id.digi_btn0);
        setupButton(button_1, R.id.digi_btn1);
        setupButton(button_2, R.id.digi_btn2);
        setupButton(button_3, R.id.digi_btn3);
        setupButton(button_4, R.id.digi_btn4);
        setupButton(button_5, R.id.digi_btn5);
        setupButton(button_6, R.id.digi_btn6);
        setupButton(button_7, R.id.digi_btn7);
        setupButton(button_8, R.id.digi_btn8);
        setupButton(button_9, R.id.digi_btn9);


    }

    // Initialize all buttons with listener
    private void setupButton(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(button_Listener);
    }

    View.OnClickListener button_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MaterialButton btn = (MaterialButton) view;
            String btn_text = btn.getText().toString();

            // Change special characters to programming operators
            if (btn_text.equals("÷"))
                btn_text = "/";
            else if (btn_text.equals("×"))
                btn_text = "*";

            Log.d("TAG", "######## onClick: Begins........ btn = " + btn_text + "\tcurrent = " + currentEntry + "\t previous = " + previousEntry + "\t brackets = " + openParentheses + "\tDecimal = " + openDecimal);

            // Take care of which bracket to use
            if (btn_text.equals("( )")) {
                btn_text = operation_parentheses();
            }

            // Processing each user onClick entry
            if (btn_text.equals("AC")) {
                operation_AC();
            } else if (btn_text.equals("MS")) {

            } else if (btn_text.equals("MR")) {

            } else if (btn_text.equals("M+")) {

            } else if (btn_text.equals("M-")) {

            } else if (btn_text.equals("MC")) {

            } else if (btn_text.equals("=")) {
                secondaryScreen.setText(getResult());
            } else if (btn.getId() == R.id.oper_btn13) {
                // This condition take cares of the backspace
                operation_backspace();
            } else if (currentEntry.isEmpty()) {
                // When entry is empty, append entry accordingly
                processFirstEntry(btn_text);
            } else if (currentEntry.length() == 1 && isOperators(btn_text)) {
                Log.d("TAG", "onClick: here");
                return;
            }

            // When entry is not empty, append entry accordingly
            else if (!currentEntry.isEmpty()) {



                // TODO: 10/6/23 /-/ into //    -- -+
                // if 2ndlast is operator && last is + - then following must be num or dot
                // When preceding operator is + - do the following actions
                Log.d("TAG", "onClick: dd " + currentEntry.endsWith("-"));
                if (currentEntry.endsWith("+") || currentEntry.endsWith("-")) {
                    Log.d("TAG", "onClick: +- 1 \tcurrent = " + currentEntry + "\t previous = " + previousEntry );

                    if (isOperators(previousEntry.substring(previousEntry.length()-1)) && isOperators(btn_text)) {
                        if (btn_text.equals("+") || btn_text.equals("*") || btn_text.equals("/"))
                            setOldEntry(btn_text);
                    }
                    else if (isOperators(btn_text)) {
                        // Replace the operator
                        setOldEntry(btn_text);
                        Log.d("TAG", "onClick: +- 2 \tcurrent = " + currentEntry + "\t previous = " + previousEntry );
                        setOperator(btn_text);
                        appendText(btn_text);
                    } else if (!btn_text.equals(")")) {
                        Log.d("TAG", "onClick: Just append it");
                        if (btn_text.equals("."))
                            setDecimal();
                        appendText(btn_text);
                    }
                }

                // When preceding operator is * / do the following actions
                else if (currentEntry.endsWith("*") || currentEntry.endsWith("/")) {
                    if (isOperators(btn_text) && !btn_text.equals("-")) {
                        toOldEntry(btn_text);
                        Log.d("TAG", "onClick: */ \tcurrent = " + currentEntry + "\t previous = " + previousEntry );
                        setOperator(btn_text);
                        appendText(btn_text);
                    } else if (!btn_text.equals(")")) {
                        // todo add not * / + to above condition. Only allow - .
                        Log.d("TAG", "onClick: Just append it");
                        if (btn_text.equals("."))
                            setDecimal();
                        appendText(btn_text);
                    }

                }


                else if (currentEntry.endsWith(".")) {
                    if (btn_text.equals(")") || isOperators(btn_text)) {
                        setOldEntry(btn_text);
                        if (openDecimal)
                            setDecimal();
                    } else if (btn_text.equals("(")) {
                        toOldEntry(btn_text);
                        setOperator("*");
                        if (openDecimal)
                            setDecimal();
                        // Javascript can process 23(23)
//                        appendText("*");
                        appendText(btn_text);
                    } else if (StringUtils.isNumeric(btn_text) && openDecimal) {
//                        setDecimal();
                        appendText(btn_text);
                    } else if (btn_text.equals(".") && openDecimal) {
                    } else
                        appendText(btn_text);
                }

                else if (StringUtils.isNumeric(currentEntry.substring(currentEntry.length()-1))) {
                    Log.d("TAG", "onClick: num1 \tcurrent = " + currentEntry + "\t previous = " + previousEntry + "\tDecimal = " + openDecimal);
                    if (btn_text.equals("(")) {
                        setOperator("*");
                        // Javascript can process 23(23)
//                        appendText("*");
                        appendText(btn_text);
                        if (openDecimal)
                            setDecimal();
                    } else if (btn_text.equals(".") && !openDecimal) {
                        setDecimal();
                        appendText(btn_text);
                        Log.d("TAG", "onClick: num2 \tcurrent = " + currentEntry + "\t previous = " + previousEntry + "\tDecimal = " + openDecimal);
                    } else if (btn_text.equals(".") && openDecimal) {
                        Log.d("TAG", "onClick: num3 \tcurrent = " + currentEntry + "\t previous = " + previousEntry + "\tDecimal = " + openDecimal);
                    } else if (!StringUtils.isNumeric(btn_text) && openDecimal){
                        setDecimal();
                        appendText(btn_text);
                    } else
                        appendText(btn_text);
                    Log.d("TAG", "onClick: num4 \tcurrent = " + currentEntry + "\t previous = " + previousEntry + "\tDecimal = " + openDecimal);

                }

                // When preceding operator is ( do the following actions
                else if (currentEntry.endsWith("(")) {
                    if (btn_text.equals("-") || btn_text.equals("(") || btn_text.equals(".") || StringUtils.isNumeric(btn_text))
                        processFirstEntry(btn_text);
//                        appendText(btn_text);
                }

                // When preceding operator is ) do the following actions
                else if (currentEntry.endsWith(")")) {
                    if (StringUtils.isNumeric(btn_text)) {
                        setOperator("*");
                        appendText(btn_text);
                        if (openDecimal)
                            setDecimal();
                    } else
                        appendText(btn_text);
                }


            }
            Log.d("TAG", "######## onClick: Finish........ btn = " + btn_text + "\tcurrent = " + currentEntry + "\t previous = " + previousEntry + "\t brackets = " + openParentheses + "\tDecimal = " + openDecimal);

        }
    };

    private String getResult() {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable script = context.initStandardObjects();

            return context.evaluateString(script, currentEntry, "Javascript", 1, null).toString();
        } catch (Exception e) {
            return "Error Format";
        }
    }

    // This method set entry1 = entry2
    // So when append new entry, entry1 should be previousEntry, and entry2 be currentEntry

    private void setOldEntry(String btn_text) {
        toOldEntry(btn_text);
        setOperator(currentEntry.substring(currentEntry.length()-1));
        primaryScreen.setText(currentEntry);
    }

    private void toOldEntry (String btn_text) {
        Log.d("TAG", "setOldEntry: 1 \tcurrent = " + currentEntry + "\t previous = " + previousEntry);
        if (currentEntry.length() >= 2) {
            Log.d("TAG", "setOldEntry: 2 \tcurrent = " + currentEntry + "\t previous = " + previousEntry);
            currentEntry = previousEntry;
//            previousEntry = "";
            previousEntry = currentEntry.substring(0,currentEntry.length()-1);
            Log.d("TAG", "setOldEntry: 3 \tcurrent = " + currentEntry + "\t previous = " + previousEntry);
        } else if (currentEntry.length() == 1) {
            Log.d("TAG", "setOldEntry: 4 \tcurrent = " + currentEntry + "\t previous = " + previousEntry);
            currentEntry = btn_text;
            previousEntry = "";
            Log.d("TAG", "setOldEntry: 5 \tcurrent = " + currentEntry + "\t previous = " + previousEntry);
        } else {
            Log.d("TAG", "setOldEntry: 6 \tcurrent = " + currentEntry + "\t previous = " + previousEntry);
            currentEntry = previousEntry;
            previousEntry = currentEntry.substring(0,currentEntry.length()-1);
            Log.d("TAG", "setOldEntry: 7 \tcurrent = " + currentEntry + "\t previous = " + previousEntry);
        }
    }

    private void operation_backspace() {
        //currentEntry = previousEntry;
//        primaryScreen.setText(currentEntry);

        String lastChar = currentEntry.substring(currentEntry.length()-1);

        Log.d("ddd", "operation_backspace: ++ " + currentEntry.length());
        if (currentEntry.length() == 0) {
            return;
        } else if (currentEntry.length() == 1)
            operation_AC();
        else {
            if (lastChar.equals("(")) {
                openParentheses--;
            } else if (lastChar.equals(")")) {
                openParentheses++;
            } else if (lastChar.equals(".")) {
                openDecimal = false;
            }
            previousEntry = currentEntry.substring(0, currentEntry.length() - 2);
            currentEntry = currentEntry.substring(0, currentEntry.length() - 1);
            primaryScreen.setText(currentEntry);
        }
    }


    private boolean isOperators(String btn_text) {
        return btn_text.equals("+") || btn_text.equals("-") || btn_text.equals("*") || btn_text.equals("/");
    }

    private String operation_parentheses() {
        Log.d("TAG", "operation_parentheses 1: count : " + openParentheses);
        if (openParentheses > 0) {
            if (currentEntry.endsWith("(") || isOperators(currentEntry.substring(currentEntry.length()-1))) {
                openParentheses++;
                Log.d("TAG", "operation_parentheses 2: count : " + openParentheses);
                return "(";
            } else if (StringUtils.isNumeric(currentEntry.substring(currentEntry.length()-1)) || currentEntry.endsWith(")")) {
                openParentheses--;
                Log.d("TAG", "operation_parentheses 3: count : " + openParentheses);
                return ")";
            }
        } else {
            openParentheses++;
            return "(";
        }
        return ")";
    }

    private void setOperator(String btn_text) {
        currentOperator = btn_text;
        Log.d("ddd", "setOperator: " + currentOperator);
    }

    private void setDecimal () {
        if (openDecimal)
            openDecimal = false;
        else
            openDecimal = true;
    }

    private void appendText(String btn_text) {
        previousEntry = currentEntry;
        currentEntry += btn_text;
        Log.d("ddd", "appendText: " + currentEntry);
        primaryScreen.setText(currentEntry);
    }

    private void operation_AC() {
        currentEntry = "";
        previousEntry = "";
        currentOperator = "";
        openParentheses = 0;
        openDecimal = false;
        primaryScreen.setText(currentEntry);
    }

    // Set operators. When new entry, only show + - . and numbers. Not * /
    private void processFirstEntry(String btn_text) {

        if (btn_text.equals("-")) {
            setOperator(btn_text);
            appendText(btn_text);
//        } else if (btn_text.equals("+")) {
//            setOperator(btn_text);
//            appendText(btn_text);
//        } else if (btn_text.equals("*")) {
//            setOperator(btn_text);
//
//        } else if (btn_text.equals("/")) {
//            setOperator(btn_text);

        } else if (btn_text.equals(".") && !openDecimal) {
            appendText(btn_text);
            openDecimal = true;
        } else if (btn_text.equals("(")) {
            appendText(btn_text);
        } else if (StringUtils.isNumeric(btn_text)) {
            appendText(btn_text);
        }

    }

}