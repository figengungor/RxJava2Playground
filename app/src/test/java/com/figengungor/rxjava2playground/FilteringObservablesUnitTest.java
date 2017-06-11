package com.figengungor.rxjava2playground;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableSingleObserver;

import static org.junit.Assert.assertEquals;

/**
 * Created by figengungor on 6/12/2017.
 */

public class FilteringObservablesUnitTest {

    //Filter — emit only those items from an Observable that pass a predicate test
    @Test
    public void filterOperator() {
        final ArrayList<Integer> actualList = new ArrayList<>();
        final Integer[] expectedList = {2, 4};
        Observable<Integer> integerObservable = Observable.just(1, 2, 3, 4, 5);
        //Odd numbers shall not pass!
        Observable<Integer> filteredIntegerObservable = integerObservable.filter(new Predicate<Integer>() {
            @Override
            public boolean test(@NonNull Integer integer) throws Exception {
                return integer % 2 == 0;
            }
        });
        filteredIntegerObservable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                actualList.add(integer);
            }
        });
        assertEquals(Arrays.asList(expectedList), actualList);
    }

    //Distinct — suppress duplicate items emitted by an Observable
    @Test
    public void distinctOperator() {
        final ArrayList<Integer> actualList = new ArrayList<>();
        final Integer[] expectedList = {1, 2, 3};
        Observable<Integer> integerObservable = Observable.just(1, 2, 2, 3, 3, 2);
        Observable<Integer> distinctIntegerObservable = integerObservable.distinct();
        distinctIntegerObservable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                actualList.add(integer);
            }
        });
        assertEquals(Arrays.asList(expectedList), actualList);
    }

    //ElementAt — emit only item n emitted by an Observable
    @Test
    public void elementAtOperator() {
        final Integer[] list = {1, 2, 3};
        Observable<Integer> integerObservable = Observable.fromArray(list);
        Maybe<Integer> elementAtIntegerObservable = integerObservable.elementAt(1);
        elementAtIntegerObservable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                assertEquals(new Integer(2), integer);
            }
        });

    }

    //First — emit only the first item, or the first item that meets a condition, from an Observable
    @Test
    public void firstOperator() {
        final Integer[] list = {1, 2, 3};
        Observable<Integer> integerObservable = Observable.fromArray(list);
        Single<Integer> firstIntegerObservable = integerObservable.first(-1);
        firstIntegerObservable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                assertEquals(new Integer(1), integer);
            }
        });

        //Empty list
        final Integer[] emptyList = {};
        Observable<Integer> emptyIntegerObservable = Observable.fromArray(emptyList);
        Single<Integer> firstDefaultIntegerObservable = emptyIntegerObservable.first(-1);
        firstDefaultIntegerObservable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                assertEquals(new Integer(-1), integer);
            }
        });

        //firstElement Maybe
        Maybe<Integer> firstElementMaybeIntegerObservable = emptyIntegerObservable.firstElement();
        firstElementMaybeIntegerObservable.subscribe(new DisposableMaybeObserver<Integer>() {
            @Override
            public void onSuccess(@NonNull Integer integer) {
                System.out.println("onSuccess: " + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("onError: firstElement" + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete: firstElement Maybe?");
            }
        });

    }

    //IgnoreElements — do not emit any items from an Observable but mirror its termination notification
    //OnComplete or onError will be called
    @Test
    public void ignoreElementsOperator() {
        Integer[] list = {1, 2, 3};
        Observable<Integer> integerObservable = Observable.fromArray(list);
        Completable ignoreElementsIntegerObservable = integerObservable.ignoreElements();
        ignoreElementsIntegerObservable.subscribe(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                System.out.println("onComplete: ignoreElemenets");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("onError: firstElement" + e.getMessage());
            }
        });
    }

    //Last — emit only the last item emitted by an Observable
    @Test
    public void lastOperator() {
        final Integer[] list = {1, 2, 3};
        Observable<Integer> integerObservable = Observable.fromArray(list);
        Single<Integer> lastIntegerObservable = integerObservable.last(-1);
        lastIntegerObservable.subscribe(new DisposableSingleObserver<Integer>() {
            @Override
            public void onSuccess(@NonNull Integer integer) {
                assertEquals(list[list.length - 1], integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("onError: last" + e.getMessage());
            }
        });
    }
}
