package com.liotaal.mydinero2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

public class MainActivity extends AppCompatActivity {

    private EditText mInput;
    private TextView mTextEuro, mTextDolar, mTextLibra;
    private Button mBotonEuro, mBotonDolar, mBotonLibra;
    private ProgressBar mBar;
    private ImageView mImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInput = (EditText) findViewById(R.id.editDinero);
        mTextEuro = (TextView) findViewById(R.id.textEuro);
        mTextDolar = (TextView) findViewById(R.id.textDolar);
        mTextLibra = (TextView) findViewById(R.id.textLibra);
        mBotonEuro = (Button) findViewById(R.id.botonEuro);
        mBotonDolar = (Button) findViewById(R.id.botonDolar);
        mBotonLibra = (Button) findViewById(R.id.botonLibra);
        mBar = (ProgressBar) findViewById(R.id.barra);
        mImagen = (ImageView) findViewById(R.id.imagen);

        mBotonEuro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (mInput.getText().length()==0) {
                    mostrarBrindis("ERROR Euro: Introduzca pts");
                }
                else{
                    mTextEuro.setText(String.format("%1$,.2f",Double.parseDouble(String.valueOf(mInput.getText())) /
                            166.386) + "€");
                    mBar.setVisibility(View.VISIBLE);
                    ocultarTeclado();
                }
            }
        });

        mBotonDolar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (mInput.getText().length()==0) {
                    mostrarBrindis("ERROR Dolar: Introduzca pts");
                }
                else{
                    mTextDolar.setText(String.format("%1$,.2f",Double.parseDouble(String.valueOf(mInput.getText()))
                            / 166.386 / 0.93) + "$");
                    mBar.setVisibility(View.VISIBLE);
                    ocultarTeclado();
                }
            }
        });

        mBotonLibra.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (mInput.getText().length()==0) {
                    mostrarBrindis("ERROR Libra: Introduzca pts");
                }
                else{
                    mTextLibra.setText(String.format("%1$,.2f",Double.parseDouble(String.valueOf(mInput.getText())) /
                            166.386 / 1.14) + "£");
                    mBar.setVisibility(View.VISIBLE);
                    ocultarTeclado();
                }
            }
        });

        mImagen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                mInput.setText("");
                mTextEuro.setText("");
                mTextDolar.setText("");
                mTextLibra.setText("");
                mBar.setVisibility(View.INVISIBLE);
                ocultarTeclado();
            }
        });
    }

    private void mostrarBrindis(String msg) {
        ocultarTeclado();
        Toast toast = Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    private void ocultarTeclado() {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mInput.getWindowToken(), 0);
    }
}
