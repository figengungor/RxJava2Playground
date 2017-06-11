# Play 2: Operators

**Creating Observables**
------
Operators that originate new Observables.

**Playroom**: CreatingObservablesUnitTest

- [just](http://reactivex.io/documentation/operators/just.html)

- [from](http://reactivex.io/documentation/operators/from.html)

- [range](http://reactivex.io/documentation/operators/range.html)

*TO BE CONTINUED*

*Gotcha:*

    Observable.just(someList) will give you 1 emission - a List.

    Observable.from(someList) will give you N emissions - each item in the list.


**Filtering Observables**
------
Operators that selectively emit items from a source Observable.

**Playrooms**: FilteringObservablesUnitTest, FilteringObservablesActivity

- Debounce — only emit an item from an Observable if a particular timespan has passed without it emitting another item
- Distinct — suppress duplicate items emitted by an Observable
- ElementAt — emit only item n emitted by an Observable
- Filter — emit only those items from an Observable that pass a predicate test
- First — emit only the first item, or the first item that meets a condition, from an Observable
- IgnoreElements — do not emit any items from an Observable but mirror its termination notification
- Last — emit only the last item emitted by an Observable
- Sample — emit the most recent item emitted by an Observable within periodic time intervals
- Skip — suppress the first n items emitted by an Observable
- SkipLast — suppress the last n items emitted by an Observable
- Take — emit only the first n items emitted by an Observable
- TakeLast — emit only the last n items emitted by an Observable

