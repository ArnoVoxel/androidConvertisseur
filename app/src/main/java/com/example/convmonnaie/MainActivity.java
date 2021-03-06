package com.example.convmonnaie;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Button;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    //Données membres
    public static final String DEVISE_DEPART = "deviseDepart";
    public static final String DEVISE_ARRIVEE = "deviseArrivee";
    public static final String MONTANT = "montant";

    private static final String DB_NAME = "devises";
    private static final int DB_VERSION = 1;

    String deviseDepart = null;
    String deviseArrivee = null;
    Double montant = 0.0;

    public ArrayList<String> chargeDevises(){
        ArrayList<String> liste_tableau_devises = new ArrayList<String>(Convert.getConversionTable().keySet());
        liste_tableau_devises.add("");
        Collections.sort(liste_tableau_devises);

        return liste_tableau_devises;
    }

    public Spinner chargerSpinner(int idView, String value){
        final Spinner spinner = (Spinner)findViewById(idView);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, chargeDevises());
        spinner.setAdapter(adapter);
        // Récupérer la position correspondant à la valeur, dans l’adapter
        int pos = adapter.getPosition(value);
        // puis positionner le spinner
        spinner.setSelection(pos);
        return spinner;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Connexion connexion = new Connexion(this, DB_NAME, null,DB_VERSION);
        connexion.getReadableDatabase();

        Log.d("monnaiemanager",MonnaieManager.listeMonnaies().toString());

        SharedPreferences mesPrefs = getSharedPreferences("ficherPrefs",MODE_PRIVATE);

        String valeurSpinnerDepart = mesPrefs.getString("valeurDepart", "");
        String valeurSpinnerArrivee = mesPrefs.getString("valeurArrivee", "");

        chargerSpinner(R.id.spinnerDepart, valeurSpinnerDepart);
        chargerSpinner(R.id.spinnerArrivee, valeurSpinnerArrivee);

        registerForContextMenu((ImageView)findViewById(R.id.gifChat));

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

                if(controleChamps()){
                    montant = Double.valueOf(montant_convert.getText().toString());
                    //animer le symbole €
                    ImageView euro = (ImageView) findViewById(R.id.symbolEuro);
                    euro.animate().setDuration(500).rotationXBy(360);
                    sendElementToConvert();

                } else {
                    Toast.makeText(getApplicationContext(),
                                    R.string.erreur_champs,
                                    Toast.LENGTH_SHORT)
                            .show();
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

        Button btn_ar = (Button) findViewById(R.id.convertAR);

        btn_ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(controleChamps()){
                    montant = Double.valueOf(montant_convert.getText().toString());
                    //animer le symbole €
                    ImageView euro = (ImageView) findViewById(R.id.symbolEuro);
                    euro.animate().setDuration(500).rotationXBy(360);
                    sendElementtoconvertBack();
                } else {
                    Toast.makeText(getApplicationContext(),
                                    R.string.erreur_champs,
                                    Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });
    }

    private boolean controleChamps()
    {
        // récupération des valeurs contenues dans les spinners
        Spinner spinnerDepart = (Spinner)findViewById(R.id.spinnerDepart);
        Spinner spinnerArrivee = (Spinner)findViewById(R.id.spinnerArrivee);
        EditText montant_convert = (EditText) findViewById(R.id.montant);

        // récupération de la valeur du spinner
        deviseDepart = spinnerDepart.getSelectedItem().toString();
        deviseArrivee = spinnerArrivee.getSelectedItem().toString();


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
        } else {
            spinnerPref(deviseDepart, deviseArrivee, montant);
            return true;
        }
        return false;
    }

    public void sendElementToConvert()
    {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(DEVISE_DEPART, this.deviseDepart);
        intent.putExtra(DEVISE_ARRIVEE, this.deviseArrivee);
        intent.putExtra(MONTANT, this.montant);
        startActivity(intent);
    }

    public void sendElementtoconvertBack()
    {
        Intent intentConvert = new Intent(this, TraitementActivity.class);
        intentConvert.putExtra(DEVISE_DEPART, this.deviseDepart);
        intentConvert.putExtra(DEVISE_ARRIVEE, this.deviseArrivee);
        intentConvert.putExtra(MONTANT, this.montant);
        startActivityForResult(intentConvert,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
        {
            String message = data.getStringExtra("retour");
            Toast.makeText(getApplicationContext(),
                            message,
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
    // Instanciation du menu XML spécifier en un objet Menu
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // On teste l’Id de l’item cliqué et on déclenche une action
        switch (item.getItemId()) {
            case R.id.convertirmenu:
                if(controleChamps()){
                    EditText montant_convert = (EditText) findViewById(R.id.montant);
                    montant = Double.valueOf(montant_convert.getText().toString());
                    //animer le symbole €
                    ImageView euro = (ImageView) findViewById(R.id.symbolEuro);
                    euro.animate().setDuration(500).rotationXBy(360);
                    sendElementToConvert();
                } else {
                    Toast.makeText(getApplicationContext(),
                                    R.string.erreur_champs,
                                    Toast.LENGTH_SHORT)
                            .show();
                }
                return true;
            case R.id.quittermenu:
                finish();
                return true;
            case R.id.parametres:
                Intent changerLangue = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(changerLangue);
                return true;
        }
        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        // XML décrivant les options du menu contextuel
        inflater.inflate(R.menu.menu, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        // On teste l’Id de l’item cliqué et on déclenche une action
        switch (item.getItemId()) {
            case R.id.convertirmenu:
                if(controleChamps()){
                    EditText montant_convert = (EditText) findViewById(R.id.montant);
                    montant = Double.valueOf(montant_convert.getText().toString());
                    //animer le symbole €
                    ImageView euro = (ImageView) findViewById(R.id.symbolEuro);
                    euro.animate().setDuration(500).rotationXBy(360);
                    sendElementToConvert();
                } else {
                    Toast.makeText(getApplicationContext(),
                                    R.string.erreur_champs,
                                    Toast.LENGTH_SHORT)
                            .show();
                }
                return true;
            case R.id.quittermenu:
                finish();
                return true;
            case R.id.parametres:
                Intent changerLangue = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(changerLangue);
                return true;
        }
        return false;
    }

    public void spinnerPref(String deviseDepart, String deviseArrivee, Double montant){

        SharedPreferences mesPrefs = getSharedPreferences("ficherPrefs", MODE_PRIVATE);

        SharedPreferences.Editor editor = mesPrefs.edit();

        editor.putString("valeurDepart", deviseDepart);
        editor.putString("valeurArrivee", deviseArrivee);
        editor.putFloat("montant", new Float(montant));

        editor.commit();
    }
}