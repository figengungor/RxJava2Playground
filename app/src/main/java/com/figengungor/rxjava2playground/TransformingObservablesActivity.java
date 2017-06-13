package com.figengungor.rxjava2playground;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;

import static com.figengungor.rxjava2playground.R.string.groupBy;

/**
 * Created by figengungor on 6/12/2017.
 */

public class TransformingObservablesActivity extends AppCompatActivity {

    private static final String TAG = TransformingObservablesActivity.class.getSimpleName();
    CompositeDisposable compositeDisposable;
    InitialValueObservable<TextViewTextChangeEvent> textViewTextChangeEventInitialValueObservable;
    EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transforming_observables);
        editText = (EditText) findViewById(R.id.editText);
        compositeDisposable = new CompositeDisposable();

        textViewTextChangeEventInitialValueObservable = RxTextView.textChangeEvents(editText);
        buffer(3);

    }

    /*
    * buffer(count) emits non-overlapping buffers in the form of Lists, each of which contains at most count items
    * from the source Observable (the final emitted List may have fewer than count items).
    * */
    public void buffer(int count) {
        clearArea();
        getStringObservableWithMap().buffer(count).subscribe(
                new Observer<List<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<String> strings) {
                        Log.d(TAG, "onNext: bufferCount " + strings.toString());
                        Log.d(TAG, "thread: bufferCount => " + Thread.currentThread().getName());
                        // Toast.makeText(TransformingObservablesActivity.this, strings.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: bufferCount " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: onError: bufferCount ");
                    }
                }
        );
        setSubtitle(R.string.buffer_count);
    }

    /*
    * buffer(timespan, unit) emits a new List of items periodically, every timespan amount of time,
    * containing all items emitted by the source Observable since the previous bundle emission or,
    * in the case of the first bundle, since the subscription to the source Observable.
    * There is also a version of this variant of the operator that takes a Scheduler as a parameter
    * and uses it to govern the timespan; by default this variant uses the !!!computation scheduler!!!.*/

    public void buffer(long timespan, TimeUnit timeUnit) {
        clearArea();
        getStringObservableWithMap().buffer(timespan, timeUnit).subscribe(
                new Observer<List<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<String> strings) {
                        if (strings.size() > 0) {
                            Log.d(TAG, "onNext: bufferTimespan" + strings.toString());
                        } else {
                            Log.d(TAG, "onNext: No emission");
                        }
                        Log.d(TAG, "thread: bufferTimespan => " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: bufferTimespan" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: bufferTimespan");
                    }
                }
        );
        setSubtitle(R.string.buffer_timespan);
    }

    //GroupBy — divide an Observable into a set of Observables
    //that each emit a different group of items from the original Observable, organized by key
    public void groupBy() {
        clearArea();
        Observable<GroupedObservable<String, String>> groupedObservableObservable = getStringObservableWithMap().groupBy(new Function<String, String>() {
            @Override
            public String apply(@NonNull String s) throws Exception {
                return s.length() % 2 == 0 ? "evenSizeChar" : "oddSizeChar";
            }
        });

        groupedObservableObservable.subscribe(new Observer<GroupedObservable<String, String>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(@NonNull final GroupedObservable<String, String> stringStringGroupedObservable) {
                Log.d(TAG, "onNext: GroupedObservable<String, String>");
                Log.d(TAG, "Group key: " + stringStringGroupedObservable.getKey());
                stringStringGroupedObservable.subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Log.d(TAG, "onNext groupBy value " + stringStringGroupedObservable.getKey() + "=> value: " + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: groupBy Value " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: groupBy Value");
                    }
                });
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "onError: groupBy " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: groupBy");
            }
        });
        setSubtitle(groupBy);
    }

    //Scan — apply a function to each item emitted by an Observable, sequentially, and emit each successive value
    //It's like accumulator
    public void scan() {
        clearArea();
        getStringObservableWithMap().scan(new BiFunction<String, String, String>() {
            @Override
            public String apply(@NonNull String s, @NonNull String s2) throws Exception {
                return s + s2;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.d(TAG, "onNext: scan " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "onError: scan " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: scan ");
            }
        });
        setSubtitle(R.string.scan);
    }

    //Window — periodically subdivide items from an Observable into Observable windows
    //and emit these windows rather than emitting the items one at a time
    public void window(int count) {
        clearArea();
        getStringObservableWithMap().window(count)
                .subscribe(new Observer<Observable<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Observable<String> stringObservable) {
                        Log.d(TAG, "onNext: window => Observable<String> stringObservable ");
                        stringObservable.subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull String s) {
                                Log.d(TAG, "onNext: string " + s);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e(TAG, "onError: string " + e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "onComplete: string");
                            }
                        });
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: window " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: window");
                    }
                });
        setSubtitle(R.string.window);
    }


    public Observable<String> getStringObservableWithMap() {
        return textViewTextChangeEventInitialValueObservable.map(new Function<TextViewTextChangeEvent, String>() {
            @Override
            public String apply(@NonNull TextViewTextChangeEvent textViewTextChangeEvent) throws Exception {
                return textViewTextChangeEvent.text().toString();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_transforming_operators, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_buffer_count:
                buffer(3);
                break;

            case R.id.item_buffer_timespan:
                buffer(2, TimeUnit.SECONDS);
                break;

            case R.id.item_groupBy:
                groupBy();
                break;

            case R.id.item_scan:
                scan();
                break;

            case R.id.item_window:
                window(3);
                break;
        }

        return true;
    }

    public void clearArea() {
        compositeDisposable.clear();
        editText.setText("");
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
