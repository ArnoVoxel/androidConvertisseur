package com.example.convmonnaie;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


public class TraitementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String deviseDepart = intent.getStringExtra("deviseDepart");
        String deviseArrivee = intent.getStringExtra("deviseArrivee");
        Double montant = intent.getDoubleExtra("montant", 0.0);

        try {
            Double result = (Double) Convert.convertir(deviseDepart, deviseArrivee, montant);
            String message = getString(R.string.ok_conv_deb) + " "
                    + montant + " "
                    + deviseDepart+ " "
                    + getString(R.string.ok_conv_mil) + " "
                    + deviseArrivee + " result : " + result;

            Intent intentResult = new Intent();
            intentResult.putExtra("retour", message);
            setResult(1, intentResult);
            finish();
        }catch (NumberFormatException e){
            Toast.makeText(getApplicationContext(),
                            R.string.erreur_montant,
                            Toast.LENGTH_LONG)
                    .show();
        }

    }

}