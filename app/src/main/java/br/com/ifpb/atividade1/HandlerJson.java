package br.com.ifpb.atividade1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class HandlerJson {
    public String getStringJson(String url){
        String retorno = "";
        try {
            int resposta;
            URL urlNova = new URL(url);
            HttpURLConnection conection = (HttpURLConnection) urlNova.openConnection();
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
            while( (linhaJson = bufferedReader.readLine()) != null) {
                stringBuffer.append(linhaJson);
            }
            bufferedReader.close();
        }catch(IOException ex){
            new RuntimeException(ex.getMessage());
        }
        return stringBuffer.toString();
    }
}
