package br.com.example.fecapayplus.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


//Cliente para gerenciar a comunicação com a API de forma centralizada
public class RetrofitClient {

    //Local host do backend
    private static final String BASE_URL = "http://10.0.2.2:3001/";
    private static Retrofit retrofit;

    //Função para lidar com JSON
    public static Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
}
