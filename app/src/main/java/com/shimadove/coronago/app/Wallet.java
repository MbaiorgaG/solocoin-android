package com.shimadove.coronago.app;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.shimadove.coronago.api.APIClient;
import com.shimadove.coronago.api.APIService;
import com.shimadove.coronago.app.SharedPref;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Wallet {
    public Wallet() { }

    private int wallet_balance;
    private SharedPref sharedPref;

    public void Updatebalance(Context context) {
        APIService service = APIClient.getRetrofitInstance(context).create(APIService.class);
        sharedPref = SharedPref.getInstance(context);
        JSONObject userbody = new JSONObject();
        service.showUserData(userbody).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject userdata = response.body();
                if (userdata==null){
                    Toast.makeText(context,"Some error occured. Unable to get balance.",Toast.LENGTH_SHORT).show();
                }
                else {
                    wallet_balance = userdata.get("wallet_balance").getAsInt();
                    sharedPref.setWallet_balance(wallet_balance);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(context,"Some error occured. Unable to get balance.",Toast.LENGTH_SHORT).show();
            }
        });

    }
}