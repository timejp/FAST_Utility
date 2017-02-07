package com.timejh.myutility;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends Fragment implements View.OnClickListener {

    private String formula = "";
    private boolean done = true;

    private TextView textView;

    private View view;

    public OneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            return view;
        }

        view = inflater.inflate(R.layout.fragment_one, container, false);

        textView = (TextView) view.findViewById(R.id.textView);
        Button buttons[] = new Button[16];
        buttons[0] = (Button) view.findViewById(R.id.bt0);
        buttons[1] = (Button) view.findViewById(R.id.bt1);
        buttons[2] = (Button) view.findViewById(R.id.bt2);
        buttons[3] = (Button) view.findViewById(R.id.bt3);
        buttons[4] = (Button) view.findViewById(R.id.bt4);
        buttons[5] = (Button) view.findViewById(R.id.bt5);
        buttons[6] = (Button) view.findViewById(R.id.bt6);
        buttons[7] = (Button) view.findViewById(R.id.bt7);
        buttons[8] = (Button) view.findViewById(R.id.bt8);
        buttons[9] = (Button) view.findViewById(R.id.bt9);
        buttons[10] = (Button) view.findViewById(R.id.bt_cancel);
        buttons[11] = (Button) view.findViewById(R.id.bt_run);
        buttons[12] = (Button) view.findViewById(R.id.bt_dvide);
        buttons[13] = (Button) view.findViewById(R.id.bt_mul);
        buttons[14] = (Button) view.findViewById(R.id.bt_min);
        buttons[15] = (Button) view.findViewById(R.id.bt_plus);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setOnClickListener(this);
        }

        return view;
    }

    private void calcurate() {
        String buf1 = "";
        char form = 0;
        String buf2 = "";
        for (int i = 0; i < formula.length(); i++) {
            if (formula.charAt(i) != '+' && formula.charAt(i) != '-' && formula.charAt(i) != '*' && formula.charAt(i) != '/') {
                if (form == 0) buf1 += formula.charAt(i);
                else buf2 += formula.charAt(i);
            } else form = formula.charAt(i);
        }

        int num = 0;
        switch (form) {
            case '+':
                num = Integer.parseInt(buf1) + Integer.parseInt(buf2);
                break;
            case '-':
                num = Integer.parseInt(buf1) - Integer.parseInt(buf2);
                break;
            case '*':
                num = Integer.parseInt(buf1) * Integer.parseInt(buf2);
                break;
            case '/':
                num = Integer.parseInt(buf1) / Integer.parseInt(buf2);
                break;
        }
        setText(num + "");
        formula = num + "";
    }

    private void setText(String text) {
        textView.setText(text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_plus:
                if ((formula.contains("+") || formula.contains("-") || formula.contains("*") || formula.contains("/")) && formula.charAt(formula.length() - 1) != '+' && formula.charAt(formula.length() - 1) != '-' && formula.charAt(formula.length() - 1) != '*' && formula.charAt(formula.length() - 1) != '/') {
                    calcurate();
                }
                if (formula.charAt(formula.length() - 1) != '+' && formula.charAt(formula.length() - 1) != '-' && formula.charAt(formula.length() - 1) != '*' && formula.charAt(formula.length() - 1) != '/') {
                    formula += "+";
                } else {
                    formula = formula.substring(0, formula.length() - 1) + "+";
                }
                done = false;
                break;
            case R.id.bt_min:
                if ((formula.contains("+") || formula.contains("-") || formula.contains("*") || formula.contains("/")) && formula.charAt(formula.length() - 1) != '+' && formula.charAt(formula.length() - 1) != '-' && formula.charAt(formula.length() - 1) != '*' && formula.charAt(formula.length() - 1) != '/') {
                    calcurate();
                }
                if (formula != "" && formula.charAt(formula.length() - 1) != '+' && formula.charAt(formula.length() - 1) != '-' && formula.charAt(formula.length() - 1) != '*' && formula.charAt(formula.length() - 1) != '/') {
                    formula += "-";
                } else {
                    formula = formula.substring(0, formula.length() - 1) + "-";
                }
                done = false;
                break;
            case R.id.bt_dvide:
                if ((formula.contains("+") || formula.contains("-") || formula.contains("*") || formula.contains("/")) && formula.charAt(formula.length() - 1) != '+' && formula.charAt(formula.length() - 1) != '-' && formula.charAt(formula.length() - 1) != '*' && formula.charAt(formula.length() - 1) != '/') {
                    calcurate();
                }
                if (formula != "" && formula.charAt(formula.length() - 1) != '+' && formula.charAt(formula.length() - 1) != '-' && formula.charAt(formula.length() - 1) != '*' && formula.charAt(formula.length() - 1) != '/') {
                    formula += "/";
                } else {
                    formula = formula.substring(0, formula.length() - 1) + "/";
                }
                done = false;
                break;
            case R.id.bt_mul:
                if ((formula.contains("+") || formula.contains("-") || formula.contains("*") || formula.contains("/")) && formula.charAt(formula.length() - 1) != '+' && formula.charAt(formula.length() - 1) != '-' && formula.charAt(formula.length() - 1) != '*' && formula.charAt(formula.length() - 1) != '/') {
                    calcurate();
                }
                if (formula != "" && formula.charAt(formula.length() - 1) != '+' && formula.charAt(formula.length() - 1) != '-' && formula.charAt(formula.length() - 1) != '*' && formula.charAt(formula.length() - 1) != '/') {
                    formula += "*";
                } else {
                    formula = formula.substring(0, formula.length() - 1) + "*";
                }
                done = false;
                break;
            case R.id.bt_run:
                if (formula.equals(""))
                    break;
                if (formula.charAt(formula.length() - 1) == '+' || formula.charAt(formula.length() - 1) == '-' || formula.charAt(formula.length() - 1) == '*' || formula.charAt(formula.length() - 1) == '/') {
                    formula = formula.substring(0, formula.length() - 1);
                }
                if (formula.contains("+") || formula.contains("-") || formula.contains("*") || formula.contains("/"))
                    calcurate();
                done = true;
                break;
            case R.id.bt_cancel:
                formula = "";
                setText("0");
                break;
            default:
                if (done) {
                    formula = "";
                    done = false;
                }
                String num = ((TextView) v).getText().toString();
                if (!num.equals(0)) formula += num;
        }
        setText(formula);
    }
}