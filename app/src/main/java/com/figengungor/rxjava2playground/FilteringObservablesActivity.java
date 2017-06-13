package com.figengungor.rxjava2playground;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by figengungor on 6/11/2017.
 */

public class FilteringObservablesActivity extends AppCompatActivity {

    EditText editText;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> items;
    CompositeDisposable compositeDisposable;
    InitialValueObservable<TextViewTextChangeEvent> textViewTextChangeEventInitialValueObservable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtering_observables);

        editText = (EditText) findViewById(R.id.editText);
        listView = (ListView) findViewById(R.id.listView);
        items = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        compositeDisposable = new CompositeDisposable();
        textViewTextChangeEventInitialValueObservable
                = RxTextView.textChangeEvents(editText);

        noFilter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filtering_operators, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_no_filter:
                noFilter();
                break;

            case R.id.item_skip:
                skip();
                break;

            case R.id.item_take:
                take();
                break;

            case R.id.item_filter:
                filter();
                break;

            case R.id.item_debounce:
                debounce();
                break;

            case R.id.item_sample:
                sample();
                break;
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    public void noFilter() {
        clearArea();
        Disposable disposable = textViewTextChangeEventInitialValueObservable.subscribe(giveMeMyConsumer());
        compositeDisposable.add(disposable);
        setSubtitle(R.string.no_filter);
    }

    //Skip — suppress the first n items emitted by an Observable
    //Thanks to RxBinding, you can use skipInitial(), too.
    public void skip() {
        clearArea();
        Observable<TextViewTextChangeEvent> textViewTextChangeEventInitialValueSkippedObservable
                = textViewTextChangeEventInitialValueObservable.skip(1);
        Disposable disposable = textViewTextChangeEventInitialValueSkippedObservable.subscribe(giveMeMyConsumer());
        compositeDisposable.add(disposable);
        setSubtitle(R.string.skip);
    }

    //Take — emit only the first n items emitted by an Observable
    public void take() {
        clearArea();
        Observable<TextViewTextChangeEvent> textViewTextChangeEventInitialValueTakeObservable
                = textViewTextChangeEventInitialValueObservable.take(3);
        Disposable disposable = textViewTextChangeEventInitialValueTakeObservable.subscribe(giveMeMyConsumer());
        compositeDisposable.add(disposable);
        setSubtitle(R.string.take);
    }

    //Filter — emit only those items from an Observable that pass a predicate test
    public void filter() {
        clearArea();
        Observable<TextViewTextChangeEvent> textViewTextChangeEventInitialValueFilter
                = textViewTextChangeEventInitialValueObservable.filter(
                new Predicate<TextViewTextChangeEvent>() {
                    @Override
                    public boolean test(@NonNull TextViewTextChangeEvent textViewTextChangeEvent) throws Exception {
                        return textViewTextChangeEvent.text().toString().length()%2 == 0;
                    }
                }
        );
        Disposable disposable = textViewTextChangeEventInitialValueFilter.subscribe(giveMeMyConsumer());
        compositeDisposable.add(disposable);
        setSubtitle(R.string.filter);
    }

    //Debounce — only emit an item from an Observable if a particular timespan has passed without it emitting another item
    public void debounce(){
        clearArea();
        Observable<TextViewTextChangeEvent> textViewTextChangeEventDebounceObservable =
                textViewTextChangeEventInitialValueObservable.debounce(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread());
        Disposable disposable = textViewTextChangeEventDebounceObservable.subscribe(giveMeMyConsumer());
        compositeDisposable.add(disposable);
        setSubtitle(R.string.debounce);
    }

    //Sample — emit the most recent item emitted by an Observable within periodic time intervals
    public void sample(){
        clearArea();
        Observable<TextViewTextChangeEvent> textViewTextChangeEventSampleObservable =
                textViewTextChangeEventInitialValueObservable.sample(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread());
        textViewTextChangeEventSampleObservable.subscribe(new Observer<TextViewTextChangeEvent>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(@NonNull TextViewTextChangeEvent textViewTextChangeEvent) {
                items.add(0, textViewTextChangeEvent.text().toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                items.add(0, "OnError: sample" + e.toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onComplete() {
                items.add(0, "OnEComplete: sample");
                adapter.notifyDataSetChanged();
            }
        });
        setSubtitle(R.string.debounce);
    }

    public void clearArea() {
        compositeDisposable.clear();
        editText.setText("");
        items.clear();
        adapter.notifyDataSetChanged();
    }

    public Consumer<TextViewTextChangeEvent> giveMeMyConsumer() {
        return new Consumer<TextViewTextChangeEvent>() {
            @Override
            public void accept(@NonNull TextViewTextChangeEvent textViewTextChangeEvent) throws Exception {
                items.add(0, textViewTextChangeEvent.text().toString());
                adapter.notifyDataSetChanged();
            }
        };
    }

    public void setSubtitle(int id){
        if(getSupportActionBar()!=null)
            getSupportActionBar().setSubtitle(getString(id));
    }

}
