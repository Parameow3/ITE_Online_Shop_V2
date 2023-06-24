package kh.edu.rupp.ite.onlineshop.ui.activity;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

import kh.edu.rupp.ite.onlineshop.R;
import kh.edu.rupp.ite.onlineshop.api.model.Profile;
import kh.edu.rupp.ite.onlineshop.api.service.ApiService;
import kh.edu.rupp.ite.onlineshop.databinding.ActivityProfileEditBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileEditActivity extends AppCompatActivity {

    private ActivityProfileEditBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfileEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadProfileFromServer();

        binding.backButton.setOnClickListener(v -> startLandingActivity());
    }

    private void startLandingActivity() {
        Intent intent = new Intent(this, LandingActivity.class);
        startActivity(intent);
    }

    private void loadProfileFromServer() {

        // create retrofit client
        Retrofit httpClient = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // create service object
        ApiService apiService = httpClient.create(ApiService.class);

        // load profile from server
        Call<Profile> task = apiService.loadProfile();

        task.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProfileEditActivity.this, "Load Profile Successful!", Toast.LENGTH_LONG).show();
                    try {
                        showProfile(response.body());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    Toast.makeText(ProfileEditActivity.this, "Load Profile failed!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Toast.makeText(ProfileEditActivity.this, "Load Profile failed!", Toast.LENGTH_LONG).show();
                Log.e("[ProfileFragment]", "Load Profile failed: " + t.getMessage());
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void showProfile(Profile profile) throws ParseException {

        // set full name to Imageview
        binding.fullName.setText(profile.getFirst_name() + " " + profile.getLast_name());

        // set email to EditText
        binding.txtEditEmail.setText(profile.getEmail());


        // set gender to edittext
        binding.txtEditGender.setText(profile.getGender());

        // set birthday to edittext
        @SuppressLint("SimpleDateFormat") DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        binding.txtEditBoD.setText(formatter.format(Objects.requireNonNull(profile.getBirthday())));

        // set address to edittext
        binding.txtEditAddress.setText(profile.getAddress());
    }
}
