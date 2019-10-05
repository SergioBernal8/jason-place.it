package com.example.json.place.it.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.json.place.it.data.base.BaseRepository;
import com.example.json.place.it.data.base.api.UserApi;
import com.example.json.place.it.data.base.response.DataResponse;
import com.example.json.place.it.data.base.response.ServerResponse;
import com.example.json.place.it.domain.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    private static UserRepository instance;

    private UserApi api;

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private UserRepository() {
        api = BaseRepository.createInstance(UserApi.class);
    }

    public MutableLiveData<DataResponse<User>> getUser(int id) {
        final MutableLiveData<DataResponse<User>> data = new MutableLiveData<>();

        api.getUser(id).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    data.setValue(new DataResponse<>(response.body().get(0), ServerResponse.CASE_SUCCESS));
                } else
                    data.setValue(new DataResponse<>(null, ServerResponse.CASE_EMPTY_RESPONSE));
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });


        return data;
    }
}
