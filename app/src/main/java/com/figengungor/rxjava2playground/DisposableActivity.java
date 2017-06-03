package com.figengungor.rxjava2playground;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class DisposableActivity extends AppCompatActivity {

    private static final String TAG = DisposableActivity.class.getSimpleName();
    Disposable disposableSubscribe,disposableSubscribeWith;
    GitHubService gitHubService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disposable);

        gitHubService = ServiceGenerator.createService(GitHubService.class);
        getUser_subscribe("udacity");
        getUser_subscribeWith("figengungor");

    }

    void getUser_subscribe(String username){
        gitHubService.getUser(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //subscribe returns void, you can access disposable in onSubscribe method of Observer
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposableSubscribe = d;
                    }

                    @Override
                    public void onNext(@NonNull User user) {
                        Log.d(TAG, "onNext -> " + user.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError -> Beep bop beep: " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: subscribe method used");
                    }
                });
    }


    void getUser_subscribeWith(String username){
        disposableSubscribeWith = gitHubService.getUser(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //subscribeWith(E observer)
                // =>Subscribes a given Observer (subclass) to this Observable and returns the given Observer as is.
                .subscribeWith(new DisposableObserver<User>() {
                    @Override
                    public void onNext(@NonNull User user) {
                        Log.d(TAG, "onNext -> " + user.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError -> Beep bop beep: " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: subscribeWith method used");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        disposableSubscribe.dispose();
        disposableSubscribeWith.dispose();
        super.onDestroy();
    }
}
