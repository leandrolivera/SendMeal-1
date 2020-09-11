package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.example.lab1.model.CuentaBancaria;
import com.example.lab1.model.Tarjeta;
import com.example.lab1.model.Usuario;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private SeekBar seekBar;
    private EditText nombre,contrasenia1,contrasenia2,correo,numeroTarjeta,numeroCCV,cbu,alias;
    private RadioButton tarjetaDebito,tarjetaCredito;
    private Spinner mesVencimiento,anioVencimiento;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switchCargaInicial;
    @SuppressLint("SetTextI18n")
    //se agregaron las annotation para que deje de mostrar warnings
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button boton = findViewById(R.id.botonregistrarme);

        nombre = (EditText) findViewById(R.id.nombre);
        contrasenia1 = (EditText) findViewById(R.id.contrasenia1);
        contrasenia2 = (EditText) findViewById(R.id.contrasenia2);
        correo = (EditText) findViewById(R.id.correo);
        numeroTarjeta = (EditText) findViewById(R.id.numeroTarjeta);
        numeroCCV = (EditText) findViewById(R.id.codigoCCV);
        tarjetaDebito = (RadioButton) findViewById(R.id.botonDebito);
        tarjetaCredito = (RadioButton) findViewById(R.id.botonCredito);
        cbu = (EditText) findViewById(R.id.numeroCBU);
        alias = (EditText) findViewById(R.id.alias);
        mesVencimiento = (Spinner) findViewById(R.id.mesVencimiento);
        anioVencimiento = (Spinner) findViewById(R.id.anioVencimiento);
        CheckBox terminosycondiciones = (CheckBox) findViewById(R.id.terminosycondiciones);
        switchCargaInicial = (Switch) findViewById(R.id.switchCargaInicial);
        textView = (TextView) findViewById(R.id.textoCargaInicial);
        seekBar = (SeekBar) findViewById(R.id.seekBarCargaInicial);
        textView.setText("Credito Inicial: " + seekBar.getProgress() + "/" + seekBar.getMax());
        mesVencimiento.setEnabled(false);
        anioVencimiento.setEnabled(false);

        numeroTarjeta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!(numeroTarjeta.getText().toString().isEmpty())) {
                    mesVencimiento.setEnabled(true);
                    anioVencimiento.setEnabled(true);
                    numeroCCV.setEnabled(true);
                }
                if (numeroTarjeta.getText().toString().isEmpty()) {
                    mesVencimiento.setEnabled(false);
                    anioVencimiento.setEnabled(false);
                    numeroCCV.setEnabled(false);
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText("Credito Inicial: " + progress + "/" + seekBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        switchCargaInicial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switchCargaInicial.isChecked()) {
                    seekBar.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                } else {
                    seekBar.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                }
            }
        });

        terminosycondiciones.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    boton.setEnabled(true);
                } else {
                    boton.setEnabled(false);
                }

            }
        });

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailPattern = getString(R.string.mailCorrecto);
                //ver ejemplo email@utn.frsf.edu.ar, se cambió el texto de lugar a values/strings.xml
                Calendar calendar = Calendar.getInstance();
                int mesAhora = calendar.get(Calendar.MONTH)+1;
                int anioAhora = calendar.get(Calendar.YEAR);
                int mesVencTarj = 0;
                int anioVencTarj = 0;
                int value = seekBar.getProgress();

                if(nombre.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "El campo nombre esta vacio.", Toast.LENGTH_SHORT).show();
                }
                else if(contrasenia1.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "El campo contraseña esta vacio.", Toast.LENGTH_SHORT).show();
                }
                else if((!(contrasenia1.getText().toString().equals(contrasenia2.getText().toString()))) ||
                        contrasenia2.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
                }
                else if(correo.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "El campo correo esta vacio.", Toast.LENGTH_SHORT).show();
                }
                else if (!(correo.getText().toString().trim().matches(emailPattern))) {
                    Toast.makeText(getApplicationContext(),"Ingrese un correo valido",Toast.LENGTH_SHORT).show();
                }
                else if(!(tarjetaDebito.isChecked() || tarjetaCredito.isChecked())){
                    Toast.makeText(getApplicationContext(), "Seleccione el tipo de tarjeta.", Toast.LENGTH_SHORT).show();
                }
                else if(numeroTarjeta.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "El campo de numero de tarjeta esta vacio.", Toast.LENGTH_SHORT).show();
                }
                else if(numeroCCV.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "El campo CCV esta vacio.", Toast.LENGTH_SHORT).show();
                }
                else if(mesVencimiento.getSelectedItem().toString().equals(":: Seleccione ::")){
                    Toast.makeText(getApplicationContext(), "Seleccione el mes de vencimiento.", Toast.LENGTH_SHORT).show();
                }
                else if(anioVencimiento.getSelectedItem().toString().equals(":: Seleccione ::")){
                    Toast.makeText(getApplicationContext(), "Seleccione el año de vencimiento.", Toast.LENGTH_SHORT).show();
                }

                else if(switchCargaInicial.isChecked() && value == 0){
                    Toast.makeText(getApplicationContext(), "Su crédito inicial debe ser mayor que $0.", Toast.LENGTH_SHORT).show();
                    }

                else if(!(mesVencimiento.getSelectedItem().toString().equals(":: Seleccione ::"))
                        && !(anioVencimiento.getSelectedItem().toString().equals(":: Seleccione ::"))){
                    mesVencTarj = Integer.parseInt(mesVencimiento.getSelectedItem().toString());
                    anioVencTarj = Integer.parseInt(anioVencimiento.getSelectedItem().toString());
                    if(anioVencTarj<anioAhora){
                        Toast.makeText(getApplicationContext(), "El vencimiento debe ser superior a los próximos 3 meses", Toast.LENGTH_SHORT).show();
                    }
                    else if(anioVencTarj == anioAhora){
                        if (mesVencTarj < (mesAhora + 3)){
                            Toast.makeText(getApplicationContext(), "El vencimiento debe ser superior a los próximos 3 meses", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if(anioVencTarj > anioAhora) {
                        if ((mesAhora + 3) > 12) {
                            mesAhora = 0;
                            if (mesVencTarj < (mesAhora + 3)) {
                                Toast.makeText(getApplicationContext(), "El vencimiento debe ser superior a los próximos 3 meses", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Registro Exitoso!", Toast.LENGTH_SHORT).show();
                            boolean esCredito = tarjetaCredito.isChecked();
                            Date fechaVencimiento = new Date(anioVencTarj - 1900, mesVencTarj, 1);
                            CuentaBancaria cuentaUsuario = new CuentaBancaria(cbu.getText().toString(), alias.getText().toString());
                            Tarjeta tarjetaUsuario = new Tarjeta(numeroTarjeta.getText().toString(), numeroCCV.getText().toString(), fechaVencimiento, esCredito);
                            Usuario nuevoUsuario = new Usuario(1, nombre.getText().toString(), contrasenia1.getText().toString(), correo.getText().toString(), (double) value, tarjetaUsuario, cuentaUsuario);
                        }
                    }
                }
            }
        });
    }
}