package com.example.json.place.it.data.base.response;

public class DataResponse<T> {
    public T data;
    public ServerResponse response;

    public DataResponse(T data, ServerResponse response) {
        this.data = data;
        this.response = response;
    }
}
