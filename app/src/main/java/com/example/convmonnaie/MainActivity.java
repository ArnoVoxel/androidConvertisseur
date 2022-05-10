package com.example.convmonnaie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // récupération des valeurs contenues dans les spinners
        Spinner spinnerDepart = (Spinner)findViewById(R.id.spinnerArrivee);


        Spinner spinnerArrivee = (Spinner)findViewById(R.id.spinnerArrivee);


        // Button btn = (Button)findViewById(R.id.convertir);

        /*spinnerDepart.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View spinner, int position, long spinnerDepart) {
                        Object objectDeviseDepart = parent.getItemAtPosition(position);
                        String deviseDepart = objectDeviseDepart.toString();
                        Toast.makeText(parent.getContext(), deviseDepart+" devise de départ", Toast.LENGTH_SHORT).show();
                        // action du bouton convertir

                        btn.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v)
                            {
                                // affichage du toast
                                Toast.makeText(getApplicationContext(),
                                                deviseDepart,
                                                Toast.LENGTH_LONG)
                                        .show();


                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );*/



        // action du bouton convertir
        // à chaque clic récupérer le contenu du spinner pour l'envoyer
        // dans le Toast
        Button btn = (Button)findViewById(R.id.convertir);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                // récupération de la valeur du spinner
                String deviseDepart = spinnerDepart.getSelectedItem().toString();
                String deviseArrivee = spinnerArrivee.getSelectedItem().toString();

                String message = "Vous voulez convertir des " + deviseDepart + " en " + deviseArrivee;

                // affichage du toast
                Toast.makeText(getApplicationContext(),
                                message,
                                Toast.LENGTH_LONG)
                        .show();


            }
        });
    }

}
/*
* 1 dollar US = 0.95 euro
* 1 euro = 1.05 dollars US
*
* 1 livre = 1.17 euros
* 1 euro = 0.85 livre
*
* 1 livre = 1.23 dollars US
* 1 dollar US = 0.81 livre
*
* git test
* */