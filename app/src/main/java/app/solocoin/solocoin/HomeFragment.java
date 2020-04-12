package app.solocoin.solocoin;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonObject;
import app.solocoin.solocoin.api.APIClient;
import app.solocoin.solocoin.api.APIService;
import app.solocoin.solocoin.app.SharedPref;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("LogNotTimber")
public class HomeFragment extends Fragment {

    static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPref sharedPref = SharedPref.getInstance(getContext());
        TextView timerTextView = view.findViewById(R.id.time);

        APIService apiService = APIClient.getRetrofitInstance(getContext()).create(APIService.class);
        apiService.showUserData(sharedPref.getAuthToken()).enqueue(new Callback<JsonObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<JsonObject> call,@NonNull Response<JsonObject> response) {
                JsonObject resp = response.body();
                if (resp != null) {
                    long uptime = resp.get("home_duration_in_seconds").getAsLong();
                    float minutes = (float) ((uptime / (1000*60)) % 60);
                    float hours   = (float) ((uptime / (1000*60*60)) % 24);
                    float days = (float) (uptime / (1000*60*60*24));

                    timerTextView.setText(days + "d " + hours + "h " + minutes + "m ");
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<JsonObject> call,@NonNull Throwable t) {
                timerTextView.setText("0d 0h 0m");
            }
        });
    }
}
