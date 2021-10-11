package com.example.myapplication;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.myapplication.API.Service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static Retrofit getRetrofit(){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ZonedDateTime.class, new JsonDeserializer<ZonedDateTime>() {
            @Override
            public ZonedDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                LocalDateTime ldt = ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime();
                ZoneId zoneId = ZoneId.of("Europe/Stockholm");
                ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC")).withZoneSameInstant(zoneId);
                return zdt;
            }
        })
                .registerTypeAdapter(ZonedDateTime.class, new JsonSerializer<ZonedDateTime>(){
                    @Override
                    public JsonElement serialize(ZonedDateTime src, Type typeOfSrc, JsonSerializationContext context) throws JsonParseException {
                        return new JsonPrimitive(DateTimeFormatter.ISO_DATE_TIME.format(src));
                    }
                }).create();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://group5api.xyz/")
                .client(okHttpClient)
                .build();

        return retrofit;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static UserService getUserService(){
        UserService userService = getRetrofit().create(UserService.class);

        return userService;
    }
}
