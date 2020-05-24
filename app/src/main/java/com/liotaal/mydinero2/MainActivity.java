package com.liotaal.mydinero2;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
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
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private EditText mInput;
    private TextView mTextEuro, mTextDolar, mTextLibra;
    private Button mBotonEuro, mBotonDolar, mBotonLibra;
    private Button mBotonPort;
    private ProgressBar mBar;
    private ImageView mImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Automatic orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

        // Visual components of the interface
        mInput = (EditText) findViewById(R.id.editDinero);
        mTextEuro = (TextView) findViewById(R.id.textEuro);
        mTextDolar = (TextView) findViewById(R.id.textDolar);
        mTextLibra = (TextView) findViewById(R.id.textLibra);
        mBotonEuro = (Button) findViewById(R.id.botonEuro);
        mBotonDolar = (Button) findViewById(R.id.botonDolar);
        mBotonLibra = (Button) findViewById(R.id.botonLibra);
        mBotonPort = (Button) findViewById(R.id.botonPort);
        mBar = (ProgressBar) findViewById(R.id.barra);
        mImagen = (ImageView) findViewById(R.id.imagen);

        // Listeners and events

        mBotonEuro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (mInput.getText().length()==0) {
                    //mostrarBrindis("ERROR Euro: Introduzca pts");
                    mostrarSnack(arg0, "ERROR Euro: Introduzca pts");
                }
                else{
                    mTextEuro.setText(String.format("%1$,.2f",Double.parseDouble(String.valueOf(mInput.getText())) /
                            166.386) + "€");
                    mBar.setVisibility(View.VISIBLE);
                    hideKeyboard();
                }
            }
        });

        mBotonDolar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (mInput.getText().length()==0) {
                    //mostrarBrindis("ERROR Dolar: Introduzca pts");
                    mostrarSnack(arg0, "ERROR Dolar: Introduzca pts");
                }
                else{
                    mTextDolar.setText(String.format("%1$,.2f",Double.parseDouble(String.valueOf(mInput.getText()))
                            / 166.386 / 0.93) + "$");
                    mBar.setVisibility(View.VISIBLE);
                    hideKeyboard();
                }
            }
        });

        mBotonLibra.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (mInput.getText().length()==0) {
                    //mostrarBrindis("ERROR Libra: Introduzca pts");
                    mostrarSnack(arg0, "ERROR Libra: Introduzca pts");
                }
                else{
                    mTextLibra.setText(String.format("%1$,.2f",Double.parseDouble(String.valueOf(mInput.getText())) /
                            166.386 / 1.14) + "£");
                    mBar.setVisibility(View.VISIBLE);
                    hideKeyboard();
                }
            }
        });

        mBotonPort.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SourceLockedOrientationActivity")
            public void onClick(View arg0) {
                // Portrait orientation
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        });


        mImagen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                mInput.setText("");
                mTextEuro.setText("");
                mTextDolar.setText("");
                mTextLibra.setText("");
                mBar.setVisibility(View.INVISIBLE);
                hideKeyboard();

                // Automatic orientation
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("INPUT", mInput.getText().toString());
        savedInstanceState.putString("EURO", mTextEuro.getText().toString());
        savedInstanceState.putString("DOLAR", mTextDolar.getText().toString());
        savedInstanceState.putString("LIBRA", mTextLibra.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mInput.setText(savedInstanceState.getString("INPUT"));
        mTextEuro.setText(savedInstanceState.getString("EURO"));
        mTextDolar.setText(savedInstanceState.getString("DOLAR"));
        mTextLibra.setText(savedInstanceState.getString("LIBRA"));
    }

    private void mostrarSnack(View v, String msg) {
        hideKeyboard();
        Snackbar.make(v, msg, Snackbar.LENGTH_LONG).show();
    }

    private void mostrarBrindis(String msg) {
        hideKeyboard();
        Toast toast = Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mInput.getWindowToken(), 0);
    }
}
