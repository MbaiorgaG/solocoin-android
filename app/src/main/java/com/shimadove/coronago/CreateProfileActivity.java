package com.shimadove.coronago;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import timber.log.Timber;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.shimadove.coronago.api.APIClient;
import com.shimadove.coronago.api.APIService;
import com.shimadove.coronago.databinding.ActivityCreateProfileBinding;
import com.shimadove.coronago.viewmodel.CreateProfileViewModel;

import org.apache.commons.lang3.RandomStringUtils;

public class CreateProfileActivity extends AppCompatActivity implements CreateProfileViewModel.CreateProfileInterface {

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor preferencesEditor;
    public final String PREFERENCES_FILE = "information";

    private String phoneNumber, firebaseUid;

    ActivityCreateProfileBinding binding;
    CreateProfileViewModel viewModel;

    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        apiService = APIClient.getRetrofitInstance(this).create(APIService.class);

         binding = DataBindingUtil.setContentView(this,R.layout.activity_create_profile);
         viewModel = new ViewModelProvider(this).get(CreateProfileViewModel.class);
         binding.getRoot();
         binding.setCreateProfileViewModel(viewModel);

         viewModel.setCreateProfileInterface(this);

        sharedPreferences = getApplication().getSharedPreferences("information", Context.MODE_PRIVATE);
        preferencesEditor = sharedPreferences.edit();

        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseUid = currentUser.getUid();
    }

    private void createProfile(String username, String phoneNumber, String uid){
        JsonObject body = new JsonObject();
        body.addProperty("mobile", phoneNumber);
        body.addProperty("uid", uid);
        body.addProperty("name", username);

        apiService.doMobileSignup(body);
    }

    @Override
    public void onCreateProfileSuccess() {

    }

    @Override
    public void onCreateProfileFailed() {

    }

    @Override
    public void onContinueClicked() {
        preferencesEditor.putString("email", viewModel.email);
        preferencesEditor.apply();

        // create profile for server.
        createProfile(viewModel.username, phoneNumber, firebaseUid);
    }

    @Override
    public void onMaleClicked() {
        preferencesEditor.putString("gender", "M");
        preferencesEditor.apply();
    }

    @Override
    public void onFemaleClicked() {
        preferencesEditor.putString("gender", "F");
        preferencesEditor.apply();
    }

    @Override
    public void onSkip() {
        String username = RandomStringUtils.randomAlphanumeric(20).toUpperCase();
        createProfile(username, phoneNumber, firebaseUid);
    }
}
