# Play 2: Operators

**Creating Observables**
------
Operators that originate new Observables.

**Playroom**: CreatingObservablesUnitTest

- [just](http://reactivex.io/documentation/operators/just.html)

- [from](http://reactivex.io/documentation/operators/from.html)

- [range](http://reactivex.io/documentation/operators/range.html)

- [defer](http://reactivex.io/documentation/operators/defer.html)

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


**Transforming Observables**
------
Operators that transform items that are emitted by an Observable.

**Playrooms**: TransformingObservablesUnitTest, TransformingObservablesActivity

- Buffer — periodically gather items from an Observable into bundles and emit these bundles rather than emitting the items one at a time
- FlatMap — transform the items emitted by an Observable into Observables, then flatten the emissions from those into a single Observable
- GroupBy — divide an Observable into a set of Observables that each emit a different group of items from the original Observable, organized by key
- Map — transform the items emitted by an Observable by applying a function to each item
- Scan — apply a function to each item emitted by an Observable, sequentially, and emit each successive value
- Window — periodically subdivide items from an Observable into Observable windows and emit these windows rather than emitting the items one at a time

**Combining Observables**
------
Operators that work with multiple source Observables to create a single Observable

**Playroom**: CombiningObservablesActivity

- CombineLatest — when an item is emitted by either of two Observables, combine the latest item emitted by each Observable via a specified function and emit items based on the results of this function
- Merge — combine multiple Observables into one by merging their emissions
- StartWith — emit a specified sequence of items before beginning to emit the items from the source Observable
- Zip — combine the emissions of multiple Observables together via a specified function and emit single items for each combination based on the results of this function