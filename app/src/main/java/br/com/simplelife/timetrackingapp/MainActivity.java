package br.com.simplelife.timetrackingapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private String horaEntradaTrabalho;
    private String horaAlmoco;
    private TextView somatorioTempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EditText horarioTrabalhoEntrada = (EditText) findViewById(R.id.horarioTrabalhoEntrada);
        EditText horarioTrabalhoSaida = (EditText) findViewById(R.id.horarioTrabalhoSaida);
        EditText horarioAlmoco = (EditText) findViewById(R.id.horarioAlmoco);
        EditText horarioAlmocoSaida = (EditText) findViewById(R.id.horarioAlmocoSaida);

        somatorioTempo = (TextView) findViewById(R.id.txt_somatorioTempo);


        horarioTrabalhoEntrada.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {
                horaEntradaTrabalho = String.valueOf(s.toString());
            }



        });

        horarioAlmoco.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {
                horaAlmoco = String.valueOf(s.toString());

                if(!horaEntradaTrabalho.isEmpty() && horaEntradaTrabalho.length() == 8 && horaAlmoco.length() == 8){



                    SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");

                    Date d1 = null;
                    Date d2 = null;
                    long diffSeconds;
                    long diffMinutes;
                    long diffHours;

                    try {
                        d1 = sdf.parse(horaEntradaTrabalho);
                        d2 = sdf.parse(horaAlmoco);

                        long diff = Math.abs(d1.getTime() - d2.getTime());
                        diffSeconds = diff / 1000 % 60;

                        diffMinutes = diff / (60 * 1000) % 60;
                        diffHours = diff / (60 * 60 * 1000) % 24;
                        somatorioTempo.setText(diffHours + ":" + diffMinutes + ":" + diffSeconds);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }






                }
            }



        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
