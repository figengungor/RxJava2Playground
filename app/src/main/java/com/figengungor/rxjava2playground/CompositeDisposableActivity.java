package com.figengungor.rxjava2playground;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by figengungor on 6/3/2017.
 */

public class CompositeDisposableActivity extends AppCompatActivity {

    private static final String TAG = CompositeDisposableActivity.class.getSimpleName();
    CompositeDisposable compositeDisposable;
    GitHubService gitHubService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composite_disposable);
        gitHubService = ServiceGenerator.createService(GitHubService.class);

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(getUser("udacity"));
        compositeDisposable.add(getUser("figengungor"));
    }

    Disposable getUser(String username) {
      return gitHubService.getUser(username)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribeWith(new DisposableObserver<User>() {
                  @Override
                  public void onNext(@NonNull User user) {
                      Log.d(TAG, "onNext -> " + user.toString());
                  }

                  @Override
                  public void onError(@NonNull Throwable e) {
                      Log.e(TAG, "onError -> getUser: " + e.getMessage() , e);
                  }

                  @Override
                  public void onComplete() {
                      Log.d(TAG, "onComplete");
                  }
              });
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
