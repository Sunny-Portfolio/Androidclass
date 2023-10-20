package com.example.simplecalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;


import net.objecthunter.exp4j.ExpressionBuilder;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mariuszgromada.math.mxparser.*;


import android.content.DialogInterface;



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

    private String currentEntry = "";
    private String previousEntry = "";
    private String currentOperator = "";
//    String lastOperator = "";
//    String secondLastOperator = "";
    private int openParentheses = 0;
    private boolean openDecimal = false;
    private String expression = "";
    private String memoryStored = "";
    private boolean zeroSuppression = false;
    private final int expressionMax = 72;
    // is first run is no use
    private boolean isFirstRun = true;
    private String result = "";

    AlertDialog.Builder builder;

    // These are the keys for the state change saved variables
    private static final String STATE_currentEntry = "STATE_currentEntry_key";
    private static final String STATE_previousEntry = "STATE_previousEntry_key";
    private static final String STATE_openParentheses = "STATE_openParentheses_key";
    private static final String STATE_openDecimal = "STATE_openDecimal_key";
    private static final String STATE_memoryStored = "STATE_memoryStored_key";
    private static final String STATE_zeroSuppression = "STATE_zeroSuppression_key";
    private static final String STATE_primaryScreen = "STATE_primaryScreen_key";
    private static final String STATE_secondaryScreen ="STATE_secondaryScreen_key";
    private static final String STATE_isFirstRun ="STATE_isFirstRun_key";
    private static final String STATE_result ="STATE_result_key";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup the two display areas, and all buttons
        primaryScreen = (TextView) findViewById(R.id.textView_primary);
        secondaryScreen = (TextView) findViewById(R.id.textView_secondary);

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

        setup_mXparser();

        // This shows the welcome popup message
        if (savedInstanceState == null){
            setPopup();
        }

    }



    /*
    Save Instance State and variables for orientation change
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(STATE_primaryScreen, String.valueOf(primaryScreen.getText()));
        outState.putString(STATE_secondaryScreen, String.valueOf(secondaryScreen.getText()));
        outState.putString(STATE_currentEntry, currentEntry);
        outState.putString(STATE_previousEntry, previousEntry);
        outState.putString(STATE_memoryStored, memoryStored);

        outState.putInt(STATE_openParentheses, openParentheses);
        outState.putBoolean(STATE_openDecimal, openDecimal);
        outState.putBoolean(STATE_zeroSuppression, zeroSuppression);
        outState.putBoolean(STATE_isFirstRun, isFirstRun);
        outState.putString(STATE_result, result);


    }

    /*
    Restore Instance State and variables for orientation change
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Recover the instance state.
        if (savedInstanceState != null) {

            primaryScreen.setText(savedInstanceState.getString(STATE_primaryScreen));
            secondaryScreen.setText(savedInstanceState.getString(STATE_secondaryScreen));

            currentEntry = savedInstanceState.getString(STATE_currentEntry);
            previousEntry = savedInstanceState.getString(STATE_previousEntry);
            memoryStored = savedInstanceState.getString(STATE_memoryStored);

            openParentheses = savedInstanceState.getInt(STATE_openParentheses);
            openDecimal = savedInstanceState.getBoolean(STATE_openDecimal);
            zeroSuppression = savedInstanceState.getBoolean(STATE_zeroSuppression);
            isFirstRun = savedInstanceState.getBoolean((STATE_isFirstRun));
            result = savedInstanceState.getString((STATE_result));

        }
    }

    /*
    This calls the dialog box
     */
    private void setPopup() {
        builder = new AlertDialog.Builder(this);
        //Setting message manually and performing action on button click
        builder.setMessage("Enjoy using this calculator : )")
                .setCancelable(false)
                .setNegativeButton("OK!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Simple Calculator");
        alert.show();
    }


    /*
    Initialize all buttons with listener
     */
    private void setupButton(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(button_Listener);
    }

    /*
    Setup mXparser settings
     */
    private void setup_mXparser() {
        // Setup the correct mode for the calculator
        if (!mXparser.checkIfImpliedMultiplicationMode()) {
            mXparser.enableImpliedMultiplicationMode();
        } else if (!mXparser.checkIfUnicodeBuiltinKeyWordsMode()) {
            mXparser.enableUnicodeBuiltinKeyWordsMode();
        } else if (!mXparser.checkIfDegreesMode()) {
            mXparser.setDegreesMode();
        }

        /* Non-Commercial Use Confirmation */
        boolean isCallSuccessful = License.iConfirmNonCommercialUse("TRU TRU");

        /* Verification if use type has been already confirmed */
        boolean isConfirmed = License.checkIfUseTypeConfirmed();

        /* Checking use type confirmation message */
        String message = License.getUseTypeConfirmationMessage();

        /* ----------- */
        mXparser.consolePrintln("isCallSuccessful = " + isCallSuccessful);
        mXparser.consolePrintln("isConfirmed = " + isConfirmed);
        mXparser.consolePrintln("message = " + message);

        mXparser.consolePrintln("checkIfAlmostIntRounding = " + mXparser.checkIfAlmostIntRounding());
        mXparser.consolePrintln("checkIfUlpRounding = " + mXparser.checkIfUlpRounding());
        mXparser.consolePrintln("checkIfCanonicalRounding = " + mXparser.checkIfCanonicalRounding());
    }

    /*
    mXparser: set degree or radian fucntion
     */
    private void setDegRadMode_mXparser() {
        if (mXparser.checkIfDegreesMode())
            mXparser.setRadiansMode();
        else if (mXparser.checkIfRadiansMode()) {
            mXparser.setDegreesMode();
        }
    }

     /*
     OnClickListener for all the buttons, and override onClick() to determine actions for each press
      */
    View.OnClickListener button_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MaterialButton btn = (MaterialButton) view;
            String btn_text = btn.getText().toString();

            Log.d("TAG", "onClick: start**** \tbtn = " + btn_text + "\tResult = " + result + "\tCurrent = " + currentEntry + "\tPrevious = " + previousEntry + "\tBracket count = " + openParentheses);

            // Change special characters to programming operators
            if (btn_text.equals("÷"))
                btn_text = "/";
            else if (btn_text.equals("×"))
                btn_text = "*";

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
                        memoryStored = getResult_V2();
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
                        memoryStored = getResult_V2();
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
                    fixExpression_V2(currentEntry);
                    result = getResult_V2();

                    // Using Apache Commons Lang 3 to figure out what the result is
                    // The use here is to filter error msg from showing on primary screen
                    if (!NumberUtils.isCreatable(result)) {
                        secondaryScreen.setTextColor(Color.parseColor("#A31621"));
                        secondaryScreen.setText(result);
                    } else {
                        previousEntry = currentEntry;
                        currentEntry = result;
                        primaryScreen.setText(result);
                        secondaryScreen.setText("");
                    }
                }
            }

            /*
            This condition take cares of the Degree / Radian button
             */
            else if (btn.getId() == R.id.oper_btn15) {
                if (btn.getText().equals("DEG")) {
                    btn.setTextColor(Color.parseColor("#A31621"));
                    btn.setText("RAD");
                    setDegRadMode_mXparser();
                } else if (btn.getText().equals("RAD")) {
                    btn.setTextColor(Color.parseColor("#214A59"));
                    btn.setText("DEG");
                    setDegRadMode_mXparser();
                }
            }

            /*
            This condition take cares of the backspace operation
             */
            else if (btn.getId() == R.id.oper_btn13) {
                if (currentEntry.equals(result)) {
                    operation_backspace();
                }
                operation_backspace();
                // Show initial result of the current expression
                showInitialResult();
            }

             /*
             If the current entry is empty, use the following logic
              */
            else if (currentEntry.isEmpty()) {
                // When entry is empty, append entry accordingly
                processFirstEntry(btn_text);
            }

             /*
             If the current entry is length 1, use the following logic
              */
            else if (currentEntry.length() == 1) {

                // Reset the suppression counters if continue input after equal sign
                if (currentEntry.equals(result)) {
                    openParentheses = 0;
                    openDecimal = false;
                    zeroSuppression = false;
                }


                // Leading zero suppression
                if (StringUtils.isNumeric(currentEntry)) {
                    if (currentEntry.equals("0") && zeroSuppression && StringUtils.isNumeric(btn_text)) {
                        // Replace the leading zero if user entered a non zero number
                        if (!btn_text.equals("0")) {
                            operation_backspace();
                            appendText(btn_text);
                        }
                    } else
                        appendText(btn_text);
                } else if (currentEntry.equals("-")) {
                    if (!isOperators(btn_text) && !btn_text.equals("^")){
                        appendText(btn_text);
                    }
                } else if (currentEntry.equals(".")) {
                    if (StringUtils.isNumeric(btn_text)) {
                        appendText(btn_text);
                    } else if (btn_text.equals("√") || btn_text.equals("π") || btn_text.equals("e") ||
                            btn_text.equals("sin") || btn_text.equals("cos") || btn_text.equals("tan") ||
                            btn_text.equals("(") || btn_text.equals("-")) {
                        operation_backspace();
                        appendText(btn_text);
                    }

                } else if (currentEntry.equals("(")) {
                    if (isOperators(btn_text) || btn_text.equals("^")) {
                        processFirstEntry(btn_text);
                    } else {
                        appendText(btn_text);
                    }
                } else if (currentEntry.endsWith("√")) {
                    // TODO: 10/18/23 due to the decimal supression in the bottom of length 1 entry
                    // TODO: added another setDecimal() becuase it double sets in processfirstentry()
                    // TODO: and at the bottom here
                    if (btn_text.equals("-") || btn_text.equals("(") || btn_text.equals(".") ||
                            StringUtils.isNumeric(btn_text) || btn_text.equals("e") || btn_text.equals("π") ||
                            btn_text.equals("√") || btn_text.equals("sin") || btn_text.equals("cos") ||
                            btn_text.equals("tan")) {
                        processFirstEntry(btn_text);
                    }
                    setDecimal();
                } else if (currentEntry.endsWith("e") || currentEntry.endsWith("π")) {
                     appendText(btn_text);
                }

                // Todo maybe move this to appendText() instead
                // Take care of decimal and decimal dot suppression
                if (!currentEntry.equals(".") && btn_text.equals("."))
                    setDecimal();
            }

            /*
            If the current entry is not empty; larger than 1 and less than 72; use the following logic
             */
            else if (!currentEntry.isEmpty() && currentEntry.length() < expressionMax) {

                // Reset the suppression counters if continue input after equal sign
                if (currentEntry.equals(result)) {
                    openParentheses = 0;
                    openDecimal = false;
                    zeroSuppression = false;
                }

                 /*
                 When preceding character is + - do the following actions
                  */
                if (currentEntry.endsWith("+") || currentEntry.endsWith("-")) {
                    // Replace the operator if + or - is followed by an operator
                    // e.g. 8+* becomes 8*
                    if (currentEntry.length() >= 2 && isOperators(previousEntry.substring(previousEntry.length()-1)) && isOperators(btn_text)) {
                        if (btn_text.equals("+") || btn_text.equals("*") || btn_text.equals("/")) {
                            setOldEntry(btn_text);
                        }
                    }

                    // After ( √ ^ only allows negative sign
                    // e.g. 8(-     8^-     8√-
                    else if (isOperators(btn_text) || btn_text.equals("^")) {
                        if (!previousEntry.endsWith("(") && !previousEntry.endsWith("√") && !previousEntry.endsWith("^")) {
                            setOldEntry(btn_text);
                            setOperator(btn_text);
                            appendText(btn_text);
                        }
                    } else if (!btn_text.equals(")")) {
                        if (btn_text.equals("."))
                            setDecimal();
                        appendText(btn_text);
                    }
                }

                /*
                When preceding character is * / do the following actions
                 */
                else if (currentEntry.endsWith("*") || currentEntry.endsWith("/")) {
                    if ((isOperators(btn_text) && !btn_text.equals("-")) || btn_text.equals("^")) {
                            toOldEntry(btn_text);
                            setOperator(btn_text);
                            appendText(btn_text);

                    } else if (!btn_text.equals(")")) {
                        if (btn_text.equals("."))
                            setDecimal();
                        appendText(btn_text);
                    }
                }

                /*
                When preceding character is a dot, do the following actions
                 */
                else if (currentEntry.endsWith(".")) {
                    if (btn_text.equals(")") || isOperators(btn_text) || btn_text.equals("^")) {
                        setOldEntry(btn_text);
                        if (openDecimal)
                            setDecimal();
                    } else if (btn_text.equals("(") || btn_text.equals("√") || btn_text.equals("π") || btn_text.equals("e") ||
                            btn_text.equals("sin") || btn_text.equals("cos") || btn_text.equals("tan") ||
                            btn_text.equals("-")) {
                        toOldEntry(btn_text);
                        // TODO: 10/19/23 setOperator will be depreciated
//                        setOperator("*");
                        if (openDecimal)
                            setDecimal();
                        appendText(btn_text);
                    } else if (StringUtils.isNumeric(btn_text) && openDecimal) {
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
                    if (btn_text.equals("(")) {
                        setOperator("*");
                        appendText(btn_text);
                        if (openDecimal)
                            setDecimal();
                    } else if (btn_text.equals(".") && !openDecimal) {
                        setDecimal();
                        appendText(btn_text);
                    } else if (btn_text.equals(".") && openDecimal) {
                        // Suppress dot entry
                    } else if (!StringUtils.isNumeric(btn_text) && openDecimal){
                        setDecimal();
                        appendText(btn_text);
                    } else
                        appendText(btn_text);
                }

                /*
                When preceding character is ( do the following actions
                 */
                else if (currentEntry.endsWith("(")) {
                    if (btn_text.equals("-") || btn_text.equals("(") || btn_text.equals(".") || StringUtils.isNumeric(btn_text)
                        || btn_text.equals("√") || btn_text.equals("π") || btn_text.equals("e")
                            || btn_text.equals("sin") || btn_text.equals("cos") || btn_text.equals("tan")) {
                        processFirstEntry(btn_text);
                    }
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
                When preceding character is π or e do the following actions
                 */
                else if (currentEntry.endsWith("π") || currentEntry.endsWith("e")) {
                    appendText(btn_text);
                    if (btn_text.equals("."))
                        setDecimal();
                }

                /*
                When preceding character is ^ do the following actions
                 */
                else if (currentEntry.endsWith("^")) {
                    if (isOperators(btn_text)) {
                        // replace previous except -
                        // don't append if ^ followed by ^
                        if (btn_text.equals("-")) {
                            appendText(btn_text);
                        } else if (!btn_text.equals("^")) {
                            operation_backspace();
                            appendText(btn_text);
                        }
                    } else if (btn_text.equals(")")) {
                        appendText(btn_text);
                    } else if (!btn_text.equals("^"))
                        appendText(btn_text);

                    if (btn_text.equals("."))
                        setDecimal();
                }

                /*
                When preceding character is √ do the following actions
                 */
                else if (currentEntry.endsWith("√")) {
                    if (btn_text.equals("-") || btn_text.equals("(") || btn_text.equals(".") ||
                            StringUtils.isNumeric(btn_text) || btn_text.equals("e") || btn_text.equals("π") ||
                            btn_text.equals("√") || btn_text.equals("sin") || btn_text.equals("cos") ||
                            btn_text.equals("tan")) {
                        processFirstEntry(btn_text);
                    }
                }

                showInitialResult();

            } else {
                Toast.makeText(MainActivity.this, "You have reach max characters allowed", Toast.LENGTH_SHORT).show();
            }
            Log.d("TAG", "onClick: end**** \tbtn = " + btn_text + "\tResult = " + result + "\tCurrent = " + currentEntry + "\tPrevious = " + previousEntry + "\tBracket count = " + openParentheses);
        }
    };

    /*
    Show initial result of the current expression to secondary screen
     */
    private void showInitialResult() {
        fixExpression_V2(currentEntry);
        String initResult = getResult_V2();

        // Calculate initial result when it is a number or constant
        if (!currentEntry.equals(initResult) && currentEntry.length() > 1 &&
                (StringUtils.isNumeric(currentEntry.substring(currentEntry.length()-1)) ||
                currentEntry.endsWith("e") || currentEntry.endsWith("π"))) {

            // Using Apache Commons Lang 3 to figure out what the result is
            // The use here is to filter error msg from showing on primary screen
            if (!NumberUtils.isCreatable(initResult)) {
                secondaryScreen.setTextColor(Color.parseColor("#A31621"));
                secondaryScreen.setText(initResult);
            } else {
                secondaryScreen.setTextColor(Color.parseColor("#828282"));
                secondaryScreen.setText(initResult);
            }
        }
    }

    /*
    getResult_V2 uses mXparser for expression evaluation to get results
    This is the library currently being used.
     */
    private String getResult_V2() {
        double result = 0;
        String resultString;

        Expression e = new Expression(expression);
        mXparser.consolePrintln("Result is: " + e.getExpressionString() + " = " + e.calculate());

        result = e.calculate();
        resultString = String.valueOf(result);

        // Removing .0 from the double whole number
        if (result % 1 == 0) {
            if (resultString.endsWith(".0")) {
                return resultString.substring(0, resultString.length() - 2);
            }
        }
        return resultString;
    }

    /*
    getResult_V3 uses exp4j for expression evaluation to get results
    Currently not used
    */
    private String getResult_V3() {
        net.objecthunter.exp4j.Expression e = new ExpressionBuilder(expression).build();
        return String.valueOf(e.evaluate());
    }


    /*
    Process the expression using Mozilla Rhino JavaScript engine, and get calculated result.
    ***Depreciated
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

     Use together with getResult(), both currently not used!
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
    Simplified version of fixExpression()
    Currently being used with getResult_V2()
     */
    private void fixExpression_V2(String str) {
        expression = str;

        for (int i = 0; i < openParentheses; i++) {
            expression += ")";
        }
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
        if (currentEntry.length() >= 2) {
            currentEntry = previousEntry;
            previousEntry = currentEntry.substring(0,currentEntry.length()-1);
        } else if (currentEntry.length() == 1) {
            currentEntry = btn_text;
            previousEntry = "";
        } else {
            currentEntry = previousEntry;
            previousEntry = currentEntry.substring(0,currentEntry.length()-1);
        }
    }

     /*
     This method take cares of the backspace operation
     It will also update the number of opened parentheses if you deleted one
     as well as update zero or dot suppression count
      */
    private void operation_backspace() {

        if (currentEntry.length() == 0) {
        }
        else if (currentEntry.length() == 1 || previousEntry.isEmpty()) {
                operation_AC();
        } else {

            if (currentEntry.endsWith("(")) {
                openParentheses--;
            } else if (currentEntry.endsWith(")")) {
                openParentheses++;
            } else if (currentEntry.endsWith(".")) {
                openDecimal = false;
            } else if (currentEntry.endsWith("0") && zeroSuppression) {
                zeroSuppression = false;
            }

            // Depreciated
//            previousEntry = currentEntry.substring(0, currentEntry.length() - 2);
//            currentEntry = currentEntry.substring(0, currentEntry.length() -
            Log.d("fix back", "operation_backspace 1: result = " + result + "\tcurrent = " + currentEntry + "\tprevious = " + previousEntry);

            Log.d("fix back", "operation_backspace 2: result = " + result + "\tcurrent = " + currentEntry + "\tprevious = " + previousEntry);


            currentEntry = previousEntry;
            if (previousEntry.endsWith("sin(") || previousEntry.endsWith("cos(") || previousEntry.endsWith("tan(")) {
                previousEntry = previousEntry.substring(0,previousEntry.length()-4);
            } else {
                previousEntry = previousEntry.substring(0, previousEntry.length() - 1);
            }
            Log.d("fix back", "operation_backspace 3: result = " + result + "\tcurrent = " + currentEntry + "\tprevious = " + previousEntry);

            primaryScreen.setText(currentEntry);
            result = "";
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
        if (openParentheses > 0) {
            if (currentEntry.endsWith("(") || isOperators(currentEntry.substring(currentEntry.length()-1))) {
                openParentheses++;
                return "(";
            } else if (StringUtils.isNumeric(currentEntry.substring(currentEntry.length()-1)) ||
                    currentEntry.endsWith(")") || currentEntry.endsWith("π") || currentEntry.endsWith("e")) {
                openParentheses--;
                return ")";
            }
        } else {
            openParentheses++;
            return "(";
        }
        return ")";
    }

    /*
    Track the previous entered operator
    Not used in any cases. Going to depreciate
     */
    private void setOperator(String btn_text) {
        currentOperator = btn_text;
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

        // If the button is sin cos tan, then add a open bracket
        if (btn_text.equals("sin") || btn_text.equals("cos") || btn_text.equals("tan")) {
            btn_text += "(";
            openParentheses++;
        }

        // If last character is not a number, then safely append 0 to the equation.
        // When the current equation is a decimal number, then don't suppress zero
        if (!StringUtils.isNumeric(lastChar) && btn_text.equals("0")) {
            // todo add zero
            previousEntry = currentEntry;
            currentEntry += btn_text;
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
            primaryScreen.setText(currentEntry);

            zeroSuppression = false;
        }

        // For all other entry, append if there is no zero suppression.
        else if (!zeroSuppression) {
            previousEntry = currentEntry;
            currentEntry += btn_text;
            primaryScreen.setText(currentEntry);
        }
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
        result = "";
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
        } else if (btn_text.equals(".") && !openDecimal) {
            appendText(btn_text);
            openDecimal = true;
        } else if (btn_text.equals("(") || btn_text.equals("π") || btn_text.equals("√")|| btn_text.equals("e")) {
            appendText(btn_text);
        } else if (btn_text.equals("sin") || btn_text.equals("cos") ||btn_text.equals("tan")) {
            appendText(btn_text);
        } else if (StringUtils.isNumeric(btn_text)) {
            appendText(btn_text);
            if (btn_text.equals("0"))
                zeroSuppression = true;
        }
    }

}