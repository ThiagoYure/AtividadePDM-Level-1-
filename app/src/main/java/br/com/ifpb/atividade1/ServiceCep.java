package br.com.ifpb.atividade1;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

public class ServiceCep extends Service{

    public final static String action = "CONSULTA_CEP";
    private HandlerJson handlerJson;

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        handlerJson = new HandlerJson();
        Thread thread = new Thread() {
            @Override
            public void run() {
                String url = "https://viacep.com.br/ws/"+intent.getCharSequenceExtra("cep")+"/json";
                String stringJson = handlerJson.getStringJson(url);
                Intent intentEnvio = new Intent();
                intentEnvio.putExtra("json", stringJson);
                intentEnvio.setAction(action);
                sendBroadcast(intentEnvio);
                stopSelf();
            }
        };
        thread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
