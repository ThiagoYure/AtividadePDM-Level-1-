package br.com.ifpb.atividade1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class HandlerJson {
    public String getStringJson(URL url){
        String retorno = "";
        try {
            int resposta;
            HttpURLConnection conection = (HttpURLConnection) url.openConnection();
            conection.setRequestMethod("GET");
            conection.connect();
            resposta = conection.getResponseCode();
            InputStream inputStream;
            if(resposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = conection.getInputStream();
                retorno = inputStreamToString(inputStream);
            }else {
                inputStream = conection.getErrorStream();
            }
            inputStream.close();
            conection.disconnect();
        }catch(IOException ex){
            new RuntimeException(ex.getMessage());
        }
        return retorno;
    }

    //método que converte os dados de um stream em uma string
    private String inputStreamToString(InputStream inputStream) {
        StringBuffer stringBuffer = new StringBuffer();
        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String linhaJson;
            linhaJson = bufferedReader.readLine();
            while(linhaJson != null) {
                stringBuffer.append(linhaJson);
            }
            bufferedReader.close();
        }catch(IOException ex){
            new RuntimeException(ex.getMessage());
        }
        return stringBuffer.toString();
    }
}
