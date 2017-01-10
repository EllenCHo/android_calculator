package com.ellen_esc.quiz_calculation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView top1;              //계산식이 나올 창
    TextView top2;              //결과값이 나올 창

    double result = 0;             //결과값
    //double dbresult = 0;        //결과값(double)
    String  operatorFlag = "0";       //연산자
    boolean flag = false;       //소수점 플래그
    String x = "";              //연산할 숫자


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        top1 = (TextView) findViewById(R.id.top1);
        top2 = (TextView) findViewById(R.id.top2);
    }

    public void acClicked(View view) {                  //초기화
        result = 0;
        operatorFlag = "0";
        flag = false;
        x = "";
        top1.setText("");
        top2.setText("");
    }

    public void delClicked(View view) {
        String s = top1.getText().toString();

        char oper = s.charAt(s.length()-1);

        if(oper == '/' || oper == 'X' || oper == '%' || oper == '-' || oper == '+'){        //연산자이면 연산자플래그 지우고 x값이 없으면 그 전 결과값 저장
            operatorFlag = "0";

            s = s.substring(0, s.length()-1);
            top1.setText(s);

            if(x.equals("")) {                          //그 전 결과값 저장
                x = result + "";
                Toast.makeText(this, "이 이상 지우면 제대로 된 계산값이 나오지 않습니다.", Toast.LENGTH_SHORT).show();
            }

        } else if(oper == '.'){                     //소수점일 때 flag를 false로 바꿈
            flag = false;
            x = x.substring(0, x.length()-1);

            s = s.substring(0, s.length()-1);
            top1.setText(s);
        } else{                                     //연산자나 소수점이 아닐 경우 x값 지우기
            try{
                x = x.substring(0, x.length()-1);

                s = s.substring(0, s.length()-1);
                top1.setText(s);
            } catch (Exception e){
                Toast.makeText(this, "x값이 비었습니다.", Toast.LENGTH_SHORT).show();
            }


        }
    }

    // 연산자를 불러오기 전에 x값을 연산해서 result에 저장함. 그 다음에 operatorFlag 저장
    // =일 경우에 연산값 저장
    public void operatorClicked(View view) {
        double temp = 0;
        try {                                           //x 값이 없을 경우 에러.
            temp = Double.parseDouble(x);
        } catch (Exception e){
            Toast.makeText(this, "숫자를 먼저 써주세요.", Toast.LENGTH_SHORT).show();
        }

        switch (operatorFlag){
            case "X":   result *= temp;
                Log.d("operator", "*");
                break;
            case "/":   result /= temp;
                Log.d("operator", "/");
                break;
            case "%":   result %= temp;
                Log.d("operator", "%");
                break;
            case "+":   result += temp;
                Log.d("operator", "+");
                break;
            case "-":   result -= temp;
                Log.d("operator", "-");
                break;
            case "0":   result = temp;
                Log.d("operator", "num");
                break;
        }

        x = "";                             //연산 후 x 값 초기화

        operatorFlag = view.getTag().toString();
        if(operatorFlag.equals("=")) {
            if(flag) {                      //소수점 일 경우 바로 출력
                top2.setText(result + "");

                Log.d("operator", "result");
            } else {                        //소수점이 아닐 경우 형변환 후에 출력
                top2.setText((int)result + "");

                Log.d("operator", "result(int)");
            }
            x = "";                         //결과 출력 후 초기화
            operatorFlag = "0";
            flag = false;

        } else {
            top1.append(operatorFlag);
        }

    }

    public void dotClicked(View view) {         //소수점을 눌렀을 시에 flag = true.
        top1.append(".");
        x += ".";
        flag = true;
    }

    public void numClicked(View view) {
        String num = view.getTag().toString();
        x += num;
        if(operatorFlag.equals("0")) {              //연산자가 없을 경우 결과값 창 지우기
            top1.setText(x);
            top2.setText("");

        }else {
            top1.append(num);
        }
    }
}
