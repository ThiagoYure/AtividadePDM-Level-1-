package br.com.ifpb.atividade1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver receiver;
    private EditText bairro;
    private EditText estado;
    private EditText cidade;
    private EditText complemento;
    private EditText logradouro;
    private EditText cepText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cidade = findViewById(R.id.cidade);
        logradouro = findViewById(R.id.logradouro);
        estado = findViewById(R.id.estado);
        complemento = findViewById(R.id.complemento);
        bairro = findViewById(R.id.bairro);
    }

    public void pesquisarCep(View view){
        Intent intent = new Intent(this, ServiceCep.class);
        cepText = findViewById(R.id.CepText);
        intent.putExtra("cep",cepText.getText());
        startService(intent);
    }

    @Override
    public void onStart() {
        receiver = new ReceiverBeauty();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ServiceCep.action);
        registerReceiver(receiver, intentFilter);
        super.onStart();
    }

    public class ReceiverBeauty extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                JSONObject object = new JSONObject(intent.getExtras().getString("json"));
                stopService(new Intent(context, ServiceCep.class));
                complemento.setText(object.getString("complemento"));
                logradouro.setText(object.getString("logradouro"));
                estado.setText(object.getString("uf"));
                cidade.setText(object.getString("localidade"));
                bairro.setText(object.getString("bairro"));
            } catch (JSONException e) {
                new RuntimeException(e.getMessage());
            }
        }
    }
}
