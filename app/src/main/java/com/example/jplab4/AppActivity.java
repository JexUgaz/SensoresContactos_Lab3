package com.example.jplab4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.util.Log;

import com.example.jplab4.databinding.ActivityAppBinding;
import com.example.jplab4.retrofit.entities.randomuser.RandomUser;
import com.example.jplab4.retrofit.entities.randomuser.ResultMain;
import com.example.jplab4.retrofit.services.RandomUserService;
import com.example.jplab4.view_models.AppViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppActivity extends AppCompatActivity {
    private static String TAG="msg-test";
    ActivityAppBinding binding;
    Boolean inProxFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppViewModel appViewModel= new ViewModelProvider(AppActivity.this).get(AppViewModel.class);
        appViewModel.getLastShakeTime().setValue(0L);

        RandomUserService randomUserService= new Retrofit.Builder()
                .baseUrl("https://randomuser.me")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RandomUserService.class);

        binding.btnEye.setOnClickListener(v->{
            inProxFragment= appViewModel.getInProxFragment().getValue();
            if(inProxFragment==null){
                inProxFragment=true;
            }
            if(inProxFragment){
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Detalles - Magnetómetro")
                        .setMessage("Haga CLICK en 'Añadir' para agregar contactos a su lista."+
                                "Esta aplicación está utilizando el MAGNETÓMETRO de su dispositivo.\n\nDe esta forma, " +
                                "la lista se mostrará al 100% cuando se apunte al NORTE. Caso contrario, se desvanecerá...")
                        .setPositiveButton("Aceptar", (dialog, which) -> {
                        })
                        .show();
            }else{
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Detalles - Acelerómetro")
                        .setMessage("Haga CLICK en 'Añadir' para agregar contactos a su lista."+
                                "Esta aplicación está utilizando el ACELERÓMETRO de su dispositivo.\n\nDe esta forma, " +
                                "la lista hará scrooll hacia abajo, cuando agite su dispositivo.")
                        .setPositiveButton("Aceptar", (dialog, which) -> {
                        })
                        .show();
            }
        });

        binding.btnCambiar.setOnClickListener(v->{
            inProxFragment= appViewModel.getInProxFragment().getValue();
            if(inProxFragment==null){
                inProxFragment=true;
            }
            NavHostFragment navHostFragment= (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navhHostFragment);
            NavController navController= navHostFragment.getNavController();
            if(inProxFragment){
                navController.navigate(R.id.lightFragment);
                binding.btnCambiar.setText("Ir a Magnetómetro");
            }else{
                navController.navigate(R.id.proxFragment);
                navController.popBackStack(R.id.lightFragment,true);
                binding.btnCambiar.setText("Ir a Acelerómetro");
            }
        });

        binding.btnAddContact.setOnClickListener(v->{
            randomUserService.getRandomUser().enqueue(new Callback<ResultMain>() {
                @Override
                public void onResponse(Call<ResultMain> call, Response<ResultMain> response) {
                    if(response.isSuccessful()){
                        ResultMain resultMain= response.body();
                        RandomUser randomUser =resultMain.getResults().get(0);
                        inProxFragment= appViewModel.getInProxFragment().getValue();
                        if(inProxFragment==null){
                            inProxFragment=true;
                        }
                        List<RandomUser> contacts;
                        if(inProxFragment){
                            contacts=appViewModel.getContactsGlobal().getValue();
                            contacts=contacts==null?new ArrayList<>():contacts;
                            contacts.add(randomUser);
                            appViewModel.getContactsGlobal().postValue(contacts);
                        }else{
                            contacts=appViewModel.getContactsGlobal2().getValue();
                            contacts=contacts==null?new ArrayList<>():contacts;
                            contacts.add(randomUser);
                            appViewModel.getContactsGlobal2().postValue(contacts);
                        }
                    }else{
                        Log.d(TAG,"No fue exitoso!");
                    }
                }

                @Override
                public void onFailure(Call<ResultMain> call, Throwable t) {
                    Log.d(TAG,"Ups! Ocurrió un error");
                    Log.d(TAG,t.getMessage());
                }
            });
        });
    }
}