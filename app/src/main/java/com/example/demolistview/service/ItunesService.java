package com.example.demolistview.service;

import android.util.Log;

//import com.google.gson.FieldNamingPolicy;
import com.example.demolistview.service.model.Root;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import cafsoft.foundation.HTTPURLResponse;
import cafsoft.foundation.URLRequest;
import cafsoft.foundation.URLSession;

public class ItunesService {
    private final String URL_SERVICE = "https://itunes.apple.com/search?media=music&entity=song&";

    public void requestItunesData(String artista, OnResponse delegate){
        final String format = "%sterm=%s";
        URL url = null;
        String strURL = "";

        strURL = String.format(format, URL_SERVICE,  artista);

        System.out.println("El titulo es: "+strURL);

        try {
            url = new URL(strURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        URLRequest request = new URLRequest(url);

        URLSession.getShared().dataTask(request, (data, response, error) -> {
            HTTPURLResponse resp = (HTTPURLResponse) response;
            Root root = null;

            if(resp.getStatusCode() == 200){
                String text = data.toText();

                GsonBuilder gsonBuilder = new GsonBuilder();
                //gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
                Gson json = gsonBuilder.create();
                root = json.fromJson(text, Root.class);



            }

            if(delegate != null){
                delegate.run(resp.getStatusCode(), root);
            }

        }).resume();


    }
    public static void  downloadFile(String destFilename ,String previewUrl)
    {
        URL url = null;


        try {
            url = new URL(previewUrl);
        } catch (MalformedURLException e) {
            //e.printStackTrace();
        }

        // new File(destFilename).delete(); // borrar para descargar nuevamente

        if (!new File(destFilename).exists()) {
            downloadFile(url, destFilename);
        }else{
            // Ya fue descargado anteriormente y se encuentra en cache
        }
    }

    private static void downloadFile(URL audioURL, String destFilename){
        URLSession.getShared().downloadTask(audioURL, (localAudioUrl, response, error) -> {

            if (error == null) {
                int respCode = ((HTTPURLResponse) response).getStatusCode();

                if (respCode == 200) {
                    File file = new File(localAudioUrl.getFile());
                    if (file.renameTo(new File(destFilename))) {
                        // OK
                    }
                }
                else{
                    // Error (respCode)
                }
            }else {
                // Connection error
            }
        }).resume();
    }
    public interface OnResponse{
        public abstract  void run(int statusCode, Root root);
    }
}
