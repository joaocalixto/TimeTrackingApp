package br.com.simplelife.timetrackingapp;

import android.app.Notification;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.com.goncalves.pugnotification.notification.PugNotification;
import butterknife.Bind;
import butterknife.ButterKnife;
import nordpol.android.OnDiscoveredTagListener;
import nordpol.android.TagDispatcher;

public class MainActivity extends AppCompatActivity implements OnDiscoveredTagListener {

    private String horaEntradaTrabalho;
    private String horaAlmoco;
    private String horatioVoltaAlmoco;
    private String horarioSaidaTrabalho;

    private TagDispatcher tagDispatcher;

    private long diff;

    @Bind(R.id.txt_somatorioTempo) TextView somatorioTempo;

    @Bind(R.id.horarioTrabalhoEntrada) EditText horarioTrabalhoEntrada;
    @Bind(R.id.horarioTrabalhoSaida)   EditText horarioTrabalhoSaida;
    @Bind(R.id.horarioAlmoco)   EditText horarioAlmoco;
    @Bind(R.id.horarioAlmocoSaida)   EditText horarioVoltaAlmoco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        setNotifications();


        horarioVoltaAlmoco.addTextChangedListener(new HorarioTextWatcher(){

            public void afterTextChanged(Editable s) { horatioVoltaAlmoco = String.valueOf(s.toString());}
        });

        horarioTrabalhoEntrada.addTextChangedListener(new HorarioTextWatcher(){

            public void afterTextChanged(Editable s) { horaEntradaTrabalho = String.valueOf(s.toString());}
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

                inserirHorarioTela();

                Snackbar.make(view, "Horário inserido.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setNotifications() {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            java.sql.Date data = new java.sql.Date(format.parse("12:00:00").getTime());
            java.sql.Date data2 = new java.sql.Date(format.parse("18:00:00").getTime());


            PugNotification.with(getApplicationContext())
                .load()
                .title("Hora de ir almoçar")
                .message("Não esqueça de bater seu ponto")
                .bigTextStyle("Hora de almoco")
                .smallIcon(R.drawable.icon_clock)
                .largeIcon(R.drawable.icon_clock)
                .flags(Notification.DEFAULT_ALL).when(data.getTime())
                .simple()
                .build();

            PugNotification.with(getApplicationContext())
                    .load()
                    .title("Hora de ir almoçar")
                    .message("Não esqueça de bater seu ponto")
                    .bigTextStyle("Hora de almoco")
                    .smallIcon(R.drawable.icon_clock)
                    .largeIcon(R.drawable.icon_clock)
                    .flags(Notification.DEFAULT_ALL)
                    .when(data2.getTime())
                    .simple()
                    .build();

        } catch (ParseException e) {
            e.printStackTrace();
        }
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
        inserirHorarioTela();
    }

    public void inserirHorarioTela(){
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
