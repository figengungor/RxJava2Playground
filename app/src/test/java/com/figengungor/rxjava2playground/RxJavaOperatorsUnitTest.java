package com.figengungor.rxjava2playground;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class RxJavaOperatorsUnitTest {

    int sum;
    int counter;

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
        final Integer[] expectedList = {1,2,3,4,5};
        Observable<Integer> integerObservable = Observable.range(1, 5);
        integerObservable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                actualList[counter] = integer;
                counter++;
            }
        });

        assertEquals(5, counter);
        assertArrayEquals(actualList, expectedList);
    }
}