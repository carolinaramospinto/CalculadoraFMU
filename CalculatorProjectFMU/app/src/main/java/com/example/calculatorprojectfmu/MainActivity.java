package com.example.calculatorprojectfmu;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView resultado, displayConta;
    MaterialButton ButtonC, Button1, Button2, Button3, Button4, Button5,
            Button6, Button7, Button8, Button9, Button0, ButtonDot, ButtonEqual, ButtonAC,
            ButtonBracketOpen, ButtonBracketClosed, ButtonSum, ButtonDivide,
            ButtonMultiply, ButtonSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        resultado = findViewById(R.id.resultado);
        displayConta = findViewById(R.id.display_conta);

        assignId(ButtonC, R.id.button_c);
        assignId(Button1, R.id.button_1);
        assignId(Button2, R.id.button_2);
        assignId(Button3, R.id.button_3);
        assignId(Button4, R.id.button_4);
        assignId(Button5, R.id.button_5);
        assignId(Button6, R.id.button_6);
        assignId(Button7, R.id.button_7);
        assignId(Button8, R.id.button_8);
        assignId(Button9, R.id.button_9);
        assignId(Button0, R.id.button_0);
        assignId(ButtonDot, R.id.button_ponto);
        assignId(ButtonEqual, R.id.button_igual);
        assignId(ButtonAC, R.id.button_clean);
        assignId(ButtonBracketOpen, R.id.button_parent_aberto);
        assignId(ButtonBracketClosed, R.id.button_parent_fechado);
        assignId(ButtonSum, R.id.button_adicao);
        assignId(ButtonSub, R.id.button_subtrai);
        assignId(ButtonMultiply, R.id.button_multipli);
        assignId(ButtonDivide, R.id.button_dividir);


    }

    void assignId(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MaterialButton button = (MaterialButton) v;
        String buttonText = button.getText().toString();
        String calculando = displayConta.getText().toString();

        if(buttonText.equals("AC")){
            displayConta.setText("");
            resultado.setText("0");
            return;
        }
        if(buttonText.equals("=")){
            displayConta.setText(resultado.getText());
            return;
        }
        if(buttonText.equals("C")){
            calculando = calculando.substring(0, calculando.length()-1);
        }else{
            calculando = calculando + buttonText;
        }
        displayConta.setText(calculando);

        String finalresultado = mostrarResultado(calculando);

        if(!finalresultado.equals("Err")){
            resultado.setText(finalresultado);
        }
    }
    String mostrarResultado(String data){
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String resultadoFinal = context.evaluateString(scriptable, data, "JavaScript", 1, null).toString();
            if(resultadoFinal.endsWith(".0")){
                resultadoFinal = resultadoFinal.replace(".0", "");
            }
            return resultadoFinal;
        }catch(Exception e){
            return "Err";
        }
    }
}