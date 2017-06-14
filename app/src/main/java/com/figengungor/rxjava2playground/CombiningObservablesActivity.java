package com.figengungor.rxjava2playground;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by figengungor on 6/13/2017.
 */

public class CombiningObservablesActivity extends AppCompatActivity {

    private static final String TAG = CombiningObservablesActivity.class.getSimpleName();
    CompositeDisposable compositeDisposable;
    Observable<Long> longObservableEMITSEVERYFIVESECONDS, longObservableEMITSEVERYONESECOND;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combining_observables);
        compositeDisposable = new CompositeDisposable();

        longObservableEMITSEVERYFIVESECONDS = Observable.interval(5, TimeUnit.SECONDS);
        longObservableEMITSEVERYONESECOND = Observable.interval(1, TimeUnit.SECONDS);

        combineLatest();
    }

    /* CombineLatest — when an item is emitted by either of two Observables, combine the latest item emitted by each Observable
     * via a specified function and emit items based on the results of this function
     * My comment: whenever there is a new emission from any of the observables,
     * it is combined with the latest emission of other observables
     */
    public void combineLatest() {
        compositeDisposable.clear();
        Observable<String> stringObservable = Observable.combineLatest(longObservableEMITSEVERYFIVESECONDS,
                longObservableEMITSEVERYONESECOND,
                new BiFunction<Long, Long, String>() {
                    @Override
                    public String apply(@NonNull Long aLong, @NonNull Long aLong2) throws Exception {
                        return aLong + " " + aLong2;
                    }
                });

        Disposable disposable = stringObservable.subscribeWith(giveMyObserver("combineLatest"));
        compositeDisposable.add(disposable);
        setSubtitle(R.string.combine_latest);
    }

    /* Zip — combine the emissions of multiple Observables together via a specified function
     * and emit single items for each combination based on the results of this function
     * My comment: Zip combine according to order, first emitted items combined together, then it goes second, third...
     * In our particular example, one second observable waits for five seconds observable
     */
    public void zip() {
        compositeDisposable.clear();
        Observable<String> stringObservable = Observable.zip(longObservableEMITSEVERYFIVESECONDS,
                longObservableEMITSEVERYONESECOND,
                new BiFunction<Long, Long, String>() {
                    @Override
                    public String apply(@NonNull Long aLong, @NonNull Long aLong2) throws Exception {
                        return aLong + " " + aLong2;
                    }
                });

        Disposable disposable = stringObservable.subscribeWith(giveMyObserver("zip"));
        compositeDisposable.add(disposable);
        setSubtitle(R.string.zip);
    }

    //Merge — combine multiple Observables into one by merging their emissions
    public void merge() {
        compositeDisposable.clear();
        Observable<Long> longObservable = Observable.merge(longObservableEMITSEVERYONESECOND,
                longObservableEMITSEVERYFIVESECONDS);
        Observable<String> stringObservable = longObservable.map(new Function<Long, String>() {
            @Override
            public String apply(@NonNull Long aLong) throws Exception {
                return ""+aLong;
            }
        });

        Disposable disposable = stringObservable.subscribeWith(giveMyObserver("merge"));
        compositeDisposable.add(disposable);
        setSubtitle(R.string.merge);
    }

    //StartWith — emit a specified sequence of items before beginning to emit the items from the source Observable
    public void startWith() {
        compositeDisposable.clear();
        Observable<Long> longObservable = longObservableEMITSEVERYONESECOND.startWith(Arrays.asList(-3L, -2L, -1L));
        Observable<String> stringObservable = longObservable.map(new Function<Long, String>() {
            @Override
            public String apply(@NonNull Long aLong) throws Exception {
                return ""+aLong;
            }
        });

        Disposable disposable = stringObservable.subscribeWith(giveMyObserver("startWith"));
        compositeDisposable.add(disposable);
        setSubtitle(R.string.start_with);
    }

    public DisposableObserver<String> giveMyObserver(final String operatorName) {
        return new DisposableObserver<String>() {
            @Override
            public void onNext(@NonNull String s) {
                Log.d(TAG, operatorName + " onNext: " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, operatorName + " onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, operatorName + " onComplete");
            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_combining_operators, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_combineLatest:
                combineLatest();
                break;
            case R.id.item_zip:
                zip();
                break;
            case R.id.item_merge:
                merge();
                break;
            case R.id.item_startWith:
                startWith();
                break;
        }

        return true;
    }

    public void setSubtitle(int id) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setSubtitle(getString(id));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
