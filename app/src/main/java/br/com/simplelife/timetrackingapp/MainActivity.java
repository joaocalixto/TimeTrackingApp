package br.com.simplelife.timetrackingapp;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import nordpol.android.OnDiscoveredTagListener;
import nordpol.android.TagDispatcher;

public class MainActivity extends AppCompatActivity implements OnDiscoveredTagListener {

    private String horaEntradaTrabalho;
    private String horaAlmoco;
    private String horatioVoltaAlmoco;
    private String horarioSaidaTrabalho;

    private TagDispatcher tagDispatcher;

    private long diff;

    private TextView somatorioTempo;

    private EditText horarioTrabalhoEntrada;
    private EditText horarioTrabalhoSaida;
    private EditText horarioAlmoco;
    private EditText horarioVoltaAlmoco;

    public MainActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        horarioTrabalhoEntrada = (EditText) findViewById(R.id.horarioTrabalhoEntrada);
        horarioTrabalhoSaida = (EditText) findViewById(R.id.horarioTrabalhoSaida);
        horarioAlmoco = (EditText) findViewById(R.id.horarioAlmoco);
        horarioVoltaAlmoco = (EditText) findViewById(R.id.horarioAlmocoSaida);

        somatorioTempo = (TextView) findViewById(R.id.txt_somatorioTempo);

        horarioVoltaAlmoco.addTextChangedListener(new HorarioTextWatcher(){

            public void afterTextChanged(Editable s) {
                horatioVoltaAlmoco = String.valueOf(s.toString());
            }
        });



        horarioTrabalhoEntrada.addTextChangedListener(new HorarioTextWatcher(){

            public void afterTextChanged(Editable s) {
                horaEntradaTrabalho = String.valueOf(s.toString());
            }



        });

        horarioTrabalhoSaida.addTextChangedListener(new HorarioTextWatcher() {
            public void afterTextChanged(Editable s) {

                horarioSaidaTrabalho = String.valueOf(s.toString());

                if(!horatioVoltaAlmoco.isEmpty() && horatioVoltaAlmoco.length() == 8 && horarioSaidaTrabalho.length() == 8){

                    long diffTemp = DateTimeUtil.calculateDateDiff(horatioVoltaAlmoco, horarioSaidaTrabalho);

                    if(somatorioTempo.getText().toString().isEmpty()){
                        somatorioTempo.setText(DateTimeUtil.longToStringDate(diffTemp));
                    }else{
                        long somatorio = diff + diffTemp;

                        somatorioTempo.setText(DateTimeUtil.longToStringDate(somatorio));
                    }
                }
            }

        });

        horarioAlmoco.addTextChangedListener(new HorarioTextWatcher() {
            public void afterTextChanged(Editable s) {
                horaAlmoco = String.valueOf(s.toString());

                if (!horaEntradaTrabalho.isEmpty() && horaEntradaTrabalho.length() == 8 && horaAlmoco.length() == 8) {

                    diff = DateTimeUtil.calculateDateDiff(horaEntradaTrabalho, horaAlmoco);
                    somatorioTempo.setText(DateTimeUtil.longToStringDate(diff));
            }
        }


    });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(horarioTrabalhoEntrada.hasFocus()){
                    horarioTrabalhoEntrada.setText(DateTimeUtil.getTimeNow());

                }else if(horarioTrabalhoSaida.hasFocus()){
                    horarioTrabalhoSaida.setText(DateTimeUtil.getTimeNow());

                }else if(horarioAlmoco.hasFocus()){
                    horarioAlmoco.setText(DateTimeUtil.getTimeNow());

                }else if(horarioVoltaAlmoco.hasFocus()){
                    horarioVoltaAlmoco.setText(DateTimeUtil.getTimeNow());

                }
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

    @Override
    protected void onResume() {
        super.onResume();
        tagDispatcher = TagDispatcher.get(this, this);
        tagDispatcher.enableExclusiveNfc();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void tagDiscovered(Tag tag) {
        Log.i("TIMETRACKING", "Reading card...");

        if(horarioTrabalhoEntrada.hasFocus()){
            horarioTrabalhoEntrada.setText(DateTimeUtil.getTimeNow());

        }else if(horarioTrabalhoSaida.hasFocus()){
            horarioTrabalhoSaida.setText(DateTimeUtil.getTimeNow());

        }else if(horarioAlmoco.hasFocus()){
            horarioAlmoco.setText(DateTimeUtil.getTimeNow());

        }else if(horarioVoltaAlmoco.hasFocus()){
            horarioVoltaAlmoco.setText(DateTimeUtil.getTimeNow());

        }else{
            horarioTrabalhoEntrada.setText(DateTimeUtil.getTimeNow());
        }

    }
}
