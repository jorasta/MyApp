package by.a1.popov.weatherforecast.Repo;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GetWeatherApiCallback<T> extends CompletableFuture<T> implements Callback {

    private Deserializer<T> deserializer;

    public GetWeatherApiCallback(Deserializer<T> deserializer) {
        this.deserializer = deserializer;
    }

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        super.completeExceptionally(e);
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        try {
            super.complete(deserializer.getData(response.body().string()));
        } catch (JSONException e) {
            e.printStackTrace();
            super.completeExceptionally(e);
        }
    }

}
