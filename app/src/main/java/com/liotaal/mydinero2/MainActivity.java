package com.liotaal.mydinero2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
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
import android.view.Menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText mInput;
    private TextView mTextEuro, mTextDolar, mTextLibra;
    private Button mBotonEuro, mBotonDolar, mBotonLibra;
    private Button mBotonPort;
    private Button mBotonChdolar, mBotonChlibra;
    private EditText mInputChdolar, mInputChlibra;
    //private ProgressBar mBar;
    private ImageView mImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Automatic orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

        ////////////////////////
        // INTERFACE COMPONENTS

        mInput = (EditText) findViewById(R.id.editDinero);
        mTextEuro = (TextView) findViewById(R.id.textEuro);
        mTextDolar = (TextView) findViewById(R.id.textDolar);
        mTextLibra = (TextView) findViewById(R.id.textLibra);
        mBotonEuro = (Button) findViewById(R.id.botonEuro);
        mBotonDolar = (Button) findViewById(R.id.botonDolar);
        mBotonLibra = (Button) findViewById(R.id.botonLibra);
        mBotonPort = (Button) findViewById(R.id.botonPort);
        mBotonChdolar = (Button) findViewById(R.id.botonChdolar);
        mBotonChlibra = (Button) findViewById(R.id.botonChlibra);
        mInputChdolar = (EditText) findViewById(R.id.editChdolar);
        mInputChlibra = (EditText) findViewById(R.id.editChlibra);
        //mBar = (ProgressBar) findViewById(R.id.barra);
        mImagen = (ImageView) findViewById(R.id.imagen);

        ////////////////////////
        // LISTENERS AND EVENTS

        mBotonEuro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (mInput.getText().length()==0) {
                    //mostrarBrindis("ERROR Euro: Introduzca pts");
                    mostrarSnack(arg0, "ERROR Euro: Introduzca pts");
                }
                else{
                    mTextEuro.setText(String.format("%1$,.2f",Double.parseDouble(String.valueOf(mInput.getText())) /
                            166.386) + "€");
                    //mBar.setVisibility(View.VISIBLE);
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
                    double dolar = Double.valueOf(mInputChdolar.getText().toString()); //0.93
                    mTextDolar.setText(String.format("%1$,.2f",Double.parseDouble(String.valueOf(mInput.getText()))
                            / 166.386 / dolar) + "$");
                    //mBar.setVisibility(View.VISIBLE);
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
                    double libra = Double.valueOf(mInputChlibra.getText().toString()); //1.14
                    mTextLibra.setText(String.format("%1$,.2f",Double.parseDouble(String.valueOf(mInput.getText())) /
                            166.386 / libra) + "£");
                    //mBar.setVisibility(View.VISIBLE);
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

        mBotonChdolar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Save Euro-Dolar change to file dolar.daT
                guardar_dolar();
            }
        });

        mBotonChlibra.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Save Euro-Libra change to file libra.dat
                guardar_libra();
            }
        });

        mImagen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                mInput.setText("");
                mTextEuro.setText("");
                mTextDolar.setText("");
                mTextLibra.setText("");
                //mBar.setVisibility(View.INVISIBLE);
                hideKeyboard();

                // Automatic orientation
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
            }
        });

        ////////////////////////
        // LOAD CHDOLAR & CHLIBRA FROM FILES
        carga_dolar();
        carga_libra();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("INPUT", mInput.getText().toString());
        savedInstanceState.putString("EURO", mTextEuro.getText().toString());
        savedInstanceState.putString("DOLAR", mTextDolar.getText().toString());
        savedInstanceState.putString("LIBRA", mTextLibra.getText().toString());
        savedInstanceState.putString("CHDOLAR", mInputChdolar.getText().toString());
        savedInstanceState.putString("CHLIBRA", mInputChlibra.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mInput.setText(savedInstanceState.getString("INPUT"));
        mTextEuro.setText(savedInstanceState.getString("EURO"));
        mTextDolar.setText(savedInstanceState.getString("DOLAR"));
        mTextLibra.setText(savedInstanceState.getString("LIBRA"));
        mInputChdolar.setText(savedInstanceState.getString("CHDOLAR"));
        mInputChlibra.setText(savedInstanceState.getString("CHLIBRA"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mimenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Toast.makeText(getApplicationContext(), "Botón X pulsado", Toast.LENGTH_SHORT).show();
        finishAffinity();
        return true;
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

    private boolean existe_archivo(String[] archivos, String archbusca) {
        for (int f = 0; f < archivos.length; f++)
            if (archbusca.equals(archivos[f]))
                return true;
        return false;
    }

    private void guardar_dolar() {
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("dolar.dat",
                    Activity.MODE_PRIVATE));
            archivo.write(mInputChdolar.getText().toString());
            archivo.flush();
            archivo.close();
        } catch (IOException e) {
            Toast.makeText(this, "Error guardando dólar", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "Dólar guardado", Toast.LENGTH_SHORT).show();
    }

    private void guardar_libra() {
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("libra.dat",
                    Activity.MODE_PRIVATE));
            archivo.write(mInputChlibra.getText().toString());
            archivo.flush();
            archivo.close();
        } catch (IOException e) {
            Toast.makeText(this, "Error guardando libra", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "Libra guardado", Toast.LENGTH_SHORT).show();
    }

    private void carga_dolar() {
        String[] archivos = fileList();
        if (existe_archivo(archivos, "dolar.dat")) {
            try {
                InputStreamReader archivo = new InputStreamReader(openFileInput("dolar.dat"));
                BufferedReader br = new BufferedReader(archivo);
                String linea = br.readLine();
                String todas = "";
                while (linea != null) {
                    todas = todas + linea + "\n";
                    linea = br.readLine();
                }
                br.close();
                archivo.close();
                mInputChdolar.setText(todas);
            } catch (IOException e) {
                Toast.makeText(this, "Error leyendo dólar", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void carga_libra() {
        String[] archivos = fileList();
        if (existe_archivo(archivos, "libra.dat")) {
            try {
                InputStreamReader archivo = new InputStreamReader(openFileInput("libra.dat"));
                BufferedReader br = new BufferedReader(archivo);
                String linea = br.readLine();
                String todas = "";
                while (linea != null) {
                    todas = todas + linea + "\n";
                    linea = br.readLine();
                }
                br.close();
                archivo.close();
                mInputChlibra.setText(todas);
            } catch (IOException e) {
                Toast.makeText(this, "Error leyendo libra", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
