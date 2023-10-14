package com.example.jplab4.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jplab4.retrofit.entities.randomuser.RandomUser;

import java.util.List;

public class AppViewModel extends ViewModel {
    private final MutableLiveData<Boolean> shakeAct= new MutableLiveData<>();
    private final MutableLiveData<Long> lastShakeTime= new MutableLiveData<>();
    private final MutableLiveData<List<RandomUser>> contactsGlobal= new MutableLiveData<>();
    private final MutableLiveData<List<RandomUser>> contactsGlobal2= new MutableLiveData<>();
    private final MutableLiveData<Boolean> inProxFragment= new MutableLiveData<>();
    private final MutableLiveData<Integer> paramMagnet= new MutableLiveData<>();

    public MutableLiveData<Integer> getParamMagnet() {
        return paramMagnet;
    }

    public MutableLiveData<List<RandomUser>> getContactsGlobal2() {
        return contactsGlobal2;
    }

    public MutableLiveData<Boolean> getInProxFragment() {
        return inProxFragment;
    }

    public MutableLiveData<List<RandomUser>> getContactsGlobal() {
        return contactsGlobal;
    }

    public MutableLiveData<Long> getLastShakeTime() {
        return lastShakeTime;
    }

    public MutableLiveData<Boolean> getShakeAct() {
        return shakeAct;
    }
}
