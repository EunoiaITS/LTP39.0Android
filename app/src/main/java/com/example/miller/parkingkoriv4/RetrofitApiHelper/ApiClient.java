package com.example.miller.parkingkoriv4.RetrofitApiHelper;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    //public static final String Base_URL = "http://pkcompany.acumenits.com/api/v1/";
    //public static final String Base_URL = "http://pkapi.work/api/";
    //public static final String Base_URL = "http://10.0.2.2/LTP39.0/api/";
    public static final String Base_URL = "https://parkingkori.com/api/v1/";

    public static Retrofit retrofit = null;

    //create and instance of retrofil
    public static Retrofit getApiClient() {

        if (retrofit == null) {


            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptors()).build();
            retrofit = new Retrofit.Builder().baseUrl(Base_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
