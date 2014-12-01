package fr.xebia.tv.provider;

public interface Callback<T> {
    void onSuccess(T data);

    void onError(Exception e);
}