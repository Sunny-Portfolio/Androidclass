package com.example.simplecalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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
    MaterialButton button_Deg, button_Sin, button_Cos, button_Tan;
    MaterialButton button_Pow, button_Sqrt, button_Pi, button_E;

    String currentEntry = "";
    String previousEntry = "";
    String currentOperator = "";
//    String lastOperator = "";
//    String secondLastOperator = "";
    int openParentheses = 0;
    boolean openDecimal = false;
    String expression = "";
    String memoryStored = "";
    boolean zeroSuppression = false;
    private final int expressionMax = 72;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup the two display areas, and all buttons
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


        setupButton(button_Deg, R.id.oper_btn15);
        setupButton(button_Sin, R.id.oper_btn16);
        setupButton(button_Cos, R.id.oper_btn17);
        setupButton(button_Tan, R.id.oper_btn18);
        setupButton(button_Pow, R.id.oper_btn19);
        setupButton(button_Sqrt, R.id.oper_btn20);
        setupButton(button_Pi, R.id.oper_btn21);
        setupButton(button_E, R.id.oper_btn22);


    }




    /*
    Initialize all buttons with listener
     */
    private void setupButton(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(button_Listener);
    }

     /*
     OnClickListener for all the buttons, and override onClick() to determine actions for each press
      */
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

            Log.d("TAG", "######## onClick: Begins........ btn = " + btn_text + "\tcurrent = " + currentEntry + "\t previous = " + previousEntry + "\t brackets = " + openParentheses + "\tDecimal = " + openDecimal + "\tZero = " + zeroSuppression);

            // Take care of which bracket to use
            if (btn_text.equals("( )")) {
                btn_text = operation_parentheses();
            }

             /*
             Processing each user onClick entry
             This is one big if else statement so it doesn't go via all condition cases
             It first process all the function buttons such as memory functions
             Then it take cares of cases if current expression is empty
             Then it take cares of cases if current expression is of length 1
             Then it take cares of cases if current expression is of length > 1 and < 72
             */
            if (btn_text.equals("AC")) {
                operation_AC();
            }

            else if (btn_text.equals("MS")) {
                // Using Apache Commons Lang 3 to make sure the value being saved is a number
                // isCreatable() is true if it's a valid number including Hexadecimal, octal number
                // scientific notation, signed number etc.
                // Used on MS, M+ M-, and equal button
                if (NumberUtils.isCreatable(secondaryScreen.getText().toString())) {
                    memoryStored = secondaryScreen.getText().toString();
                    Toast.makeText(MainActivity.this, "Memory: " + memoryStored, Toast.LENGTH_SHORT).show();
                } else if (memoryStored.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Memory: is empty", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(MainActivity.this, "Memory: " + memoryStored, Toast.LENGTH_SHORT).show();
            }

            else if (btn_text.equals("MR")) {
                if (!memoryStored.equals("")) {
                    operation_AC();
                    if (Double.parseDouble(memoryStored) % 1 != 0)
                        openDecimal = true;
                    currentEntry = memoryStored;
                    primaryScreen.setText(currentEntry);
                    secondaryScreen.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Memory: is empty", Toast.LENGTH_SHORT).show();
                }
            }

            else if (btn_text.equals("M+")) {
                String str;
                if (NumberUtils.isCreatable(secondaryScreen.getText().toString())) {
                    if (memoryStored.equals("")) {
                        memoryStored = secondaryScreen.getText().toString();
                    } else {
                        str = memoryStored + "+" + secondaryScreen.getText().toString();
                        fixExpression(str);
                        memoryStored = getResult();
                    }
                    Toast.makeText(MainActivity.this, "Memory: " + memoryStored, Toast.LENGTH_SHORT).show();
                } else if (memoryStored.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Memory: is empty", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(MainActivity.this, "Memory: " + memoryStored, Toast.LENGTH_SHORT).show();
            }

            else if (btn_text.equals("M-")) {
                String str;
                if (NumberUtils.isCreatable(secondaryScreen.getText().toString())) {
                    Log.d("TAG", "onClick 222: creatabel????");

                    if (memoryStored.equals("")) {
                        memoryStored = secondaryScreen.getText().toString();
                    } else {
                        str = memoryStored + "-" + secondaryScreen.getText().toString();
                        fixExpression(str);
                        memoryStored = getResult();
                    }
                    Toast.makeText(MainActivity.this, "Memory: " + memoryStored, Toast.LENGTH_SHORT).show();
                } else if (memoryStored.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Memory: is empty", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(MainActivity.this, "Memory: " + memoryStored, Toast.LENGTH_SHORT).show();
            }

            else if (btn_text.equals("MC")) {
                memoryStored = "";
                Toast.makeText(MainActivity.this, "Memory Cleared", Toast.LENGTH_SHORT).show();
            }

            else if (btn_text.equals("=")) {
                if (!currentEntry.isEmpty()) {
                    fixExpression(currentEntry);
                    String result = getResult();


                    Log.d("TAG", "onClick: equal 1 ");
                    // Using Apache Commons Lang 3 to figure out what the result is
                    // The use here is to filter error msg from showing on primary screen
                    if (!NumberUtils.isCreatable(result)) {
                        Log.d("TAG", "onClick: creatabel????");
                        secondaryScreen.setTextColor(Color.parseColor("#A31621"));
                        secondaryScreen.setText(result);
                    } else {
                        Log.d("TAG", "onClick: equal 2 ");

                        currentEntry = result;
                        primaryScreen.setText(currentEntry);
                        secondaryScreen.setText("");
                    }
                    Log.d("TAG", "onClick: equal 3 ");
                }

            }

            // This condition take cares of the backspace operation
            else if (btn.getId() == R.id.oper_btn13) {
                operation_backspace();

                // Show initial result of the current expression
                if (currentEntry.length() >= 2 && StringUtils.isNumeric(currentEntry.substring(currentEntry.length()-1))) {
                    fixExpression(currentEntry);
                    secondaryScreen.setText(getResult());
                }

            }

             /*
             If the current entry is empty, use the following logic
              */
            else if (currentEntry.isEmpty()) {
                Log.d("ZERO", "is empty: 1 \t" + "zero = " + zeroSuppression);

                // When entry is empty, append entry accordingly
                processFirstEntry(btn_text);
            }

             /*
             If the current entry is length 1, use the following logic
              */
            else if (currentEntry.length() == 1) {
                Log.d("ZERO", "1 entry: 1 \t" + "zero = " + zeroSuppression);

                // Leading zero suppression
                if (StringUtils.isNumeric(currentEntry)) {
                    if (currentEntry.equals("0") && zeroSuppression && StringUtils.isNumeric(btn_text)) {
                        // Replace the leading zero if user entered a non zero number
                        if (!btn_text.equals("0")) {
                            operation_backspace();
                            appendText(btn_text);
                            Log.d("ZERO", "1 entry: 2 \t" + "zero = " + zeroSuppression);

                        }
                    } else
                        appendText(btn_text);
                } else if (currentEntry.equals("-")) {
                    if (!isOperators(btn_text)){
                        Log.d("ZERO", "1 entry: 3 \t" + "zero = " + zeroSuppression);

                        appendText(btn_text);
                    }
                } else if (currentEntry.equals(".")) {
                    if (StringUtils.isNumeric(btn_text)) {
                        Log.d("ZERO", "1 entry: 4 \t" + "zero = " + zeroSuppression);

                        appendText(btn_text);
                    }
                } else if (currentEntry.equals("(")) {
                    if (isOperators(btn_text)) {
                        Log.d("ZERO", "1 entry: 1 \t" + "zero = " + zeroSuppression);

                        processFirstEntry(btn_text);
                    } else {
                        appendText(btn_text);
                    }
                } else {
                    // todo this is added for landscape mode
                    appendText(btn_text);
                }

                // Take care of decimal and decimal dot suppression
                if (!currentEntry.equals(".") && btn_text.equals("."))
                    setDecimal();


                Log.d("ZERO", "onClick: 1 entry \tcurrent = " + currentEntry + "\t previous = " + previousEntry + "\tDecimal = " + openDecimal + "\t Zero = " + zeroSuppression);
            }

             /*
             If the current entry is not empty; larger than 1 and less than 72; use the following logic
              */
            else if (!currentEntry.isEmpty() && currentEntry.length() < expressionMax) {
                Log.d("TAG", "******************* onClick: count char:  " + currentEntry.length());

                 /*
                 When preceding character is + - do the following actions
                  */
                Log.d("TAG", "onClick: dd " + currentEntry.endsWith("-"));
                if (currentEntry.endsWith("+") || currentEntry.endsWith("-")) {
                    Log.d("TAG", "onClick: +- 1 \tcurrent = " + currentEntry + "\t previous = " + previousEntry );

                    if (currentEntry.length() >= 2 && isOperators(previousEntry.substring(previousEntry.length()-1)) && isOperators(btn_text)) {
                        if (btn_text.equals("+") || btn_text.equals("*") || btn_text.equals("/")) {
                            Log.d("TAG", "onClick: +- 2 \tcurrent = " + currentEntry + "\t previous = " + previousEntry);
                            setOldEntry(btn_text);
                        }
                    }

                    // Replace the operator if + or - is followed by an operator
                    // e.g. 8+* becomes 8*
                    // After open bracket only allows negative sign
                    // e.g. 8(-
                    else if (isOperators(btn_text)) {
                        Log.d("TAG", "onClick: +- 3 \tcurrent = " + currentEntry + "\t previous = " + previousEntry );
                        if (!previousEntry.endsWith("(")) {
                            setOldEntry(btn_text);
                            setOperator(btn_text);
                            appendText(btn_text);
                        }
                    } else if (!btn_text.equals(")")) {
                        Log.d("TAG", "onClick: Just append it");
                        if (btn_text.equals("."))
                            setDecimal();
                        appendText(btn_text);
                    }
                }

                /*
                When preceding character is * / do the following actions
                 */
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

                /*
                When preceding character is a dot, do the following actions
                 */
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
//                        appendText("*");
                        appendText(btn_text);
                    } else if (StringUtils.isNumeric(btn_text) && openDecimal) {
//                        setDecimal();
                        appendText(btn_text);
                    } else if (btn_text.equals(".") && openDecimal) {
                        // Suppress dot entry
                    } else
                        appendText(btn_text);
                }

                /*
                When preceding character is numeric number, do the following actions
                 */
                else if (StringUtils.isNumeric(currentEntry.substring(currentEntry.length()-1))) {
                    Log.d("TAG", "onClick: num1 \tcurrent = " + currentEntry + "\t previous = " + previousEntry + "\tDecimal = " + openDecimal);
                    if (btn_text.equals("(")) {
                        setOperator("*");
//                        appendText("*");
                        appendText(btn_text);
                        if (openDecimal)
                            setDecimal();
                    } else if (btn_text.equals(".") && !openDecimal) {
                        setDecimal();
                        appendText(btn_text);
                        Log.d("TAG", "onClick: num2 \tcurrent = " + currentEntry + "\t previous = " + previousEntry + "\tDecimal = " + openDecimal);
                    } else if (btn_text.equals(".") && openDecimal) {
                        // Suppress dot entry
                        Log.d("TAG", "onClick: num3 \tcurrent = " + currentEntry + "\t previous = " + previousEntry + "\tDecimal = " + openDecimal);
                    } else if (!StringUtils.isNumeric(btn_text) && openDecimal){
                        setDecimal();
                        appendText(btn_text);
                    } else
                        appendText(btn_text);
                    Log.d("TAG", "onClick: num4 \tcurrent = " + currentEntry + "\t previous = " + previousEntry + "\tDecimal = " + openDecimal);

                }

                /*
                When preceding character is ( do the following actions
                 */
                else if (currentEntry.endsWith("(")) {
                    if (btn_text.equals("-") || btn_text.equals("(") || btn_text.equals(".") || StringUtils.isNumeric(btn_text))
                        processFirstEntry(btn_text);
//                        appendText(btn_text);
                }

                /*
                When preceding character is ) do the following actions
                 */
                else if (currentEntry.endsWith(")")) {
                    if (StringUtils.isNumeric(btn_text)) {
                        setOperator("*");
                        appendText(btn_text);
                        if (openDecimal)
                            setDecimal();
                    } else
                        appendText(btn_text);
                }

                /*
                Show initial result of the current expression to secondary screen
                 */
                if (currentEntry.length() >= 2 && StringUtils.isNumeric(currentEntry.substring(currentEntry.length()-1))) {
                    secondaryScreen.setTextColor(Color.parseColor("#828282"));
                    fixExpression(currentEntry);
                    secondaryScreen.setText(getResult());

                }

            } else {
                Toast.makeText(MainActivity.this, "You have reach max characters allowed", Toast.LENGTH_SHORT).show();
            }
            Log.d("TAG", "######## onClick: Finish........ btn = " + btn_text + "\tcurrent = " + currentEntry + "\t previous = " + previousEntry + "\t brackets = " + openParentheses + "\tDecimal = " + openDecimal + "\tZero = " + zeroSuppression);

        }
    };

    /*
    Process the expression using Mozilla Rhino JavaScript engine, and get calculated result.
     */
    private String getResult() {
        String result;
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable script = context.initStandardObjects();

            Log.d("exp", "getResult: 1 " + expression);
            result = context.evaluateString(script, expression, "Javascript", 1, null).toString();
            Log.d("exp", "getResult: 1.1 " + result + "\t %1 = " + Double.parseDouble(result) % 1);

            // Original result comes back as double even if it is whole number. e.g. 53.0
            // This will get rid of the .0 at the end
            if (Double.parseDouble(result) % 1 == 0) {
                if (result.endsWith(".0")) {
                    return result.substring(0, result.length() - 2);
                }
            }
            Log.d("exp", "getResult: 1.2 " + result);
            return result;

//            if (result % 1 == 0)
//                return String.valueOf((int)result);
//            else
//                return String.valueOf(result);
//            return result;
        } catch (Exception e) {
            Log.d("exp", "getResult: 2 " + expression);
            Toast.makeText(this, "Please enter correctly", Toast.LENGTH_LONG).show();
            return "Format Error";
        }
    }

     /*
     Fix equation into an expression that can be processed by Javascript.
     In this case, adding * between numbers and brackets.
     This method will also close all opened brackets.

     Note: There is a well documented problem with JavaScript arithmetic calculations.
     E.g. 0.1 + 0.2 will equals to 0.30000000000000004 in JS
     I have used a quick fix by sending the expression to toFixed(10), then multiply it by 1000
     then divide it by 1000. This may not fix all arithmetic error.
      */
    private void fixExpression(String str) {
        expression = "";
        Log.d("exp", "fixExpression: A.... " + expression);

        expression += "(" + str.charAt(0);
        for (int i = 1; i < str.length(); i++) {
            if (StringUtils.isNumeric(String.valueOf(str.charAt(i-1))) && str.charAt(i) == '(') {
                expression += "*" + str.charAt(i);
                Log.d("exp", "fixExpression: B.... 1st = " + str.charAt(i-1) + " 2nd = " + str.charAt(i) + " expression: " + expression);
            } else if (str.charAt(i-1) == ')' && StringUtils.isNumeric(String.valueOf(str.charAt(i)))) {
                expression += "*" + str.charAt(i);
                Log.d("exp", "fixExpression: C.... 1st = " + str.charAt(i-1) + " 2nd = " + str.charAt(i) + " expression: " + expression);
            } else {
                expression += str.charAt(i);
                Log.d("exp", "fixExpression: D.... " + expression);
            }
        }

        // Close all the opened parentheses
        for (int i = 0; i < openParentheses; i++) {
            expression += ")";
        }
        expression += ").toFixed(10)*1000/1000";
        Log.d("exp", "fixExpression: E.... " + expression);
    }


    /*
    This method shifts the equation to one character back,
    then set the primary screen with the updated equation.
     */
    private void setOldEntry(String btn_text) {
        toOldEntry(btn_text);
        setOperator(currentEntry.substring(currentEntry.length()-1));
        primaryScreen.setText(currentEntry);
    }

    /*
    This method is used by setOldEntry() to perform the shift character operation.
    It may also be used by by other methods or conditions to shift one character back.
     */
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

     /*
     This method take cares of the backspace operation
     It will also update the number of opened parentheses if you deleted one
     as well as update zero or dot suppression count
      */
    private void operation_backspace() {

        Log.d("ddd", "operation_backspace: ++ " + currentEntry.length());
        if (currentEntry.length() == 0) {
        } else if (currentEntry.length() == 1)
            operation_AC();
        else {
            String lastChar = currentEntry.substring(currentEntry.length()-1);
            if (lastChar.equals("(")) {
                openParentheses--;
            } else if (lastChar.equals(")")) {
                openParentheses++;
            } else if (lastChar.equals(".")) {
                openDecimal = false;
            } else if (lastChar.equals("0") && zeroSuppression == true) {
                zeroSuppression = false;
            }
            previousEntry = currentEntry.substring(0, currentEntry.length() - 2);
            currentEntry = currentEntry.substring(0, currentEntry.length() - 1);
            primaryScreen.setText(currentEntry);
        }
    }

    /*
    This method determines if the current button input is a operator
     */
    private boolean isOperators(String btn_text) {
        return btn_text.equals("+") || btn_text.equals("-") || btn_text.equals("*") || btn_text.equals("/");
    }

    /*
    This method determines which parentheses to input.
    So it returns either ( or )
    It also updates the counts for opened parentheses.
     */
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

    /*
    This method is a switch for decimal dot count
    It is used for decimal dot suppression
     */
    private void setDecimal () {
        if (openDecimal)
            openDecimal = false;
        else
            openDecimal = true;
    }

    /*
    This method takes care of most of the new button entry, append it to the equation,
    then display it on the screen.
     */
    private void appendText(String btn_text) {
        String lastChar;
        // Assign the last character of the current equation. Used for zero suppression.
        if (currentEntry.length() > 1)
            lastChar = currentEntry.substring(currentEntry.length()-1);
        else
            lastChar = currentEntry;

        Log.d("ZERO", "appendText: 1 \t" + currentEntry + "\tzero = " + zeroSuppression);
        // If last character is not a number, then safely append 0 to the equation.
        // When the current equation is a decimal number, then don't suppress zero
        if (!StringUtils.isNumeric(lastChar) && btn_text.equals("0")) {
            // todo add zero
            previousEntry = currentEntry;
            currentEntry += btn_text;
            Log.d("ZERO", "appendText: 2 \t" + currentEntry + "\tzero = " + zeroSuppression);
            primaryScreen.setText(currentEntry);

            if (openDecimal) {
                zeroSuppression = false;
            } else {
                zeroSuppression = true;
            }
        }

        // If last character is 0, then append non numeric character and reset zero suppression
        else if (lastChar.equals("0") && !StringUtils.isNumeric(btn_text)) {
            // todo add other
            previousEntry = currentEntry;
            currentEntry += btn_text;
            Log.d("ZERO", "appendText: 3 \t" + currentEntry + "\tzero = " + zeroSuppression);
            primaryScreen.setText(currentEntry);

            zeroSuppression = false;
        }

        // For all other entry, append if there is no zero suppression.
        else if (!zeroSuppression) {
            previousEntry = currentEntry;
            currentEntry += btn_text;
            Log.d("ZERO", "appendText: 4 \t" + currentEntry + "\tzero = " + zeroSuppression);
            primaryScreen.setText(currentEntry);
        }
        Log.d("ZERO", "appendText: 5 \t" + currentEntry + "\tzero = " + zeroSuppression);

    }

    /*
    Operation for All Clear (AC) function
     */
    private void operation_AC() {
        currentEntry = "";
        previousEntry = "";
        currentOperator = "";
        openParentheses = 0;
        openDecimal = false;
        primaryScreen.setText("");
        secondaryScreen.setText("");
        zeroSuppression = false;
    }

    /*
    This method take cares of the first entry of the equation,
    as well as first entry after open bracket (
    When new entry, it will only allow dot, numbers, ( and -
    It will not allow * / +
     */
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
        } else if (btn_text.equals("(") || btn_text.equals("π") || btn_text.equals("√")|| btn_text.equals("e")) {
            appendText(btn_text);
        } else if (btn_text.equals("sin") || btn_text.equals("cos") ||btn_text.equals("tan")) {
            appendText(btn_text + "(");
            openParentheses++;
        } else if (StringUtils.isNumeric(btn_text)) {
                appendText(btn_text);
                if (btn_text.equals("0"))
                    zeroSuppression = true;
        }

    }

}