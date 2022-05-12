package com.example.convmonnaie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String deviseDepart = intent.getStringExtra("deviseDepart");
        String deviseArrivee = intent.getStringExtra("deviseArrivee");
        Double montant = intent.getDoubleExtra("montant", 0.0);

        try{
        Double result = (Double)Convert.convertir(deviseDepart, deviseArrivee, montant);

        String message = getString(R.string.ok_conv_deb) + " "
                + montant + " "
                + deviseDepart+ " "
                + getString(R.string.ok_conv_mil) + " "
                + deviseArrivee + " result : " + result;

        //affichage du Log
        Log.d("ResultActivity", message);

        // affichage du toast
        /*Toast.makeText(getApplicationContext(),
                        message,
                        Toast.LENGTH_LONG)
                .show();*/

        // affichage dans le champ de texte
            TextView resultat = (TextView) findViewById(R.id.messageResult);
            resultat.append(message);


        } catch (NumberFormatException e){
            Toast.makeText(getApplicationContext(),
                        R.string.erreur_montant,
                        Toast.LENGTH_LONG)
                .show();
        }

        Button btnRetour = (Button) findViewById(R.id.retourResult);

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}