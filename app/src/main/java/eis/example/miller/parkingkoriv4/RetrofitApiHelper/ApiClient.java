package eis.example.miller.parkingkoriv4.RetrofitApiHelper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    //public static final String Base_URL = "http://parkingkori.acumenits.com/api/v1/";
    //public static final String Base_URL = "http://pkapi.work/api/";
    //public static final String Base_URL = "http://10.0.2.2/LTP39.0/api/";
    public static final String Base_URL = "https://parkingkoribeta.eisserver2.com/api/v1/";

    public static Retrofit retrofit = null;

    //create and instance of retrofil
    public static Retrofit getApiClient() {

        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(new LoggingInterceptors())
                    .build();

            retrofit = new Retrofit.Builder().baseUrl(Base_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
