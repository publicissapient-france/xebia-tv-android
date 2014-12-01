package fr.xebia.tv.presenter;

import android.os.Bundle;

public abstract class Presenter<T> {

    protected T view;
    protected Bundle previousState;

    public void takeView(T view) {
        this.view = view;
        onViewTaken();
    }

    public Presenter(){
    }

    public Presenter(Bundle previousState) {
        this.previousState = previousState;
    }

    public abstract void onViewTaken();

    public void dropView() {
        this.view = null;
    }
}
