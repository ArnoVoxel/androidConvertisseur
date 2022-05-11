package com.example.convmonnaie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
        EditText montant_convert = (EditText) findViewById(R.id.montant);

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

                //montant_convert.getText().toString();

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
                } else if(montant_convert.getText().toString().equals("") ){
                    Toast.makeText(getApplicationContext(),
                                    R.string.erreur_montant,
                                    Toast.LENGTH_SHORT)
                            .show();
                }else {
                    try{
                        Double result = (Double)Convert.convertir(deviseDepart, deviseArrivee,Double.valueOf(montant_convert.getText().toString()));

                        String message = getString(R.string.ok_conv_deb) + " "
                                + montant_convert.getText().toString() + " "
                                + deviseDepart+ " "
                                + getString(R.string.ok_conv_mil) + " "
                                + deviseArrivee + " result : " + result;
                        Log.d("MainActivity", message);
                        // affichage du toast
                        Toast.makeText(getApplicationContext(),
                                        message,
                                        Toast.LENGTH_LONG)
                                .show();
                    } catch (NumberFormatException e){
                        Toast.makeText(getApplicationContext(),
                                        R.string.erreur_montant,
                                        Toast.LENGTH_LONG)
                                .show();
                    }



                }
            }
        });

        Button btn_quitter = (Button)findViewById(R.id.quitter);

        btn_quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
    }

}