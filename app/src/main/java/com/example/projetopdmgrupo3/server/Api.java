package com.example.projetopdmgrupo3.server;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    String BASE_URL = "https://testesandrepinto.outsystemscloud.com/BackofficePDM/rest/PDMAPI/";

    @Headers({
            "Content-type: application/json; charset=utf-8"
    })

    @POST("login")
    Call<JsonObject> login(@Body JsonObject body);

    @GET("Obra")
    Call<JsonObject> getObraById(@Query("Id") int obraId);

    @POST("Obra/Entrada")
    Call<JsonObject> entradaNaObra(@Body JsonObject body);

    @POST("Casos/Criar")
    Call<JsonObject> criarCaso(@Body JsonObject body);

    @DELETE("Caso/Delete")
    Call<JsonObject> deleteCaso(@Query("Id") int casoId);

    @POST("Obra/CancelarInspecao")
    Call<JsonObject> cancelarInspecao(@Body JsonObject body);

    @POST("Obra/Saida")
    Call<JsonObject> finalizarInspecao(@Body JsonObject body);

}
