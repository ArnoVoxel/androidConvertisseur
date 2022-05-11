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
        Spinner spinnerDepart = (Spinner)findViewById(R.id.spinnerDepart);
        Spinner spinnerArrivee = (Spinner)findViewById(R.id.spinnerArrivee);

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
/*
                Toast.makeText(getApplicationContext(),
                                "devise de départ : "+deviseDepart,
                                Toast.LENGTH_SHORT)
                        .show();
                Toast.makeText(getApplicationContext(),
                                "devide d'arrivée : "+deviseArrivee,
                                Toast.LENGTH_SHORT)
                        .show();*/

                if (deviseDepart.equals("")){
                    Toast.makeText(getApplicationContext(),
                                    R.string.erreur_devise_depart,
                                    Toast.LENGTH_SHORT)
                            .show();
                } else if(deviseArrivee.equals("")){
                    Toast.makeText(getApplicationContext(),
                                R.string.erreur_devise_arrivee,
                                Toast.LENGTH_SHORT)
                        .show();
                }else if(deviseDepart.equals(deviseArrivee)){
                    Toast.makeText(getApplicationContext(),
                                    R.string.erreur_devise_identique,
                                    Toast.LENGTH_SHORT)
                            .show();
                } else {
                    String message = R.string.ok_conv1+ " " + deviseDepart+ " " + R.string.ok_conv2 + " " + deviseArrivee;

                    // affichage du toast
                    Toast.makeText(getApplicationContext(),
                                    message,
                                    Toast.LENGTH_LONG)
                            .show();
                }
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