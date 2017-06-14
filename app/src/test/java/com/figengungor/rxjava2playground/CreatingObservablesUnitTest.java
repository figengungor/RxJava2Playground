package com.figengungor.rxjava2playground;

import org.junit.Test;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class CreatingObservablesUnitTest {

    int sum;
    int counter;
    int x;

    //Just — convert an object or a set of objects into an Observable that emits that or those objects
    @Test
    public void justOperator() {
        Observable<Integer> integerObservable = Observable.just(1, 2, 3);
        integerObservable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                System.out.println("justOperator : onNext -> " + integer);
                sum += integer;
            }
        });
        assertEquals(6, sum);
    }

    //From — convert some other object or data structure into an Observable
    @Test
    public void fromOperator() {
        Observable<Integer> integerObservable = Observable.fromArray(new Integer[]{1, 2, 3, 4});
        integerObservable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {

            }
        });
    }

    //Range — create an Observable that emits a range of sequential integers
    @Test
    public void rangeOperator() {
        final Integer[] actualList = new Integer[5];
        final Integer[] expectedList = {1, 2, 3, 4, 5};
        Observable<Integer> integerObservable = Observable.range(1, 5);
        integerObservable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                actualList[counter] = integer;
                counter++;
            }
        });

        assertEquals(5, counter);
        assertArrayEquals(expectedList, actualList);
    }

    //Defer — do not create the Observable until the observer subscribes, and create a fresh Observable for each observer
    @Test
    public void deferOperator() {
        x=1;
        Observable<Integer> justObservable = Observable.just(x);
        Observable<Integer> deferObservable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.just(x);
            }
        });

        x=2;

        justObservable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                assertEquals(new Integer(1), integer);
            }
        });

        deferObservable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                assertEquals(new Integer(2), integer);
            }
        });
    }
}