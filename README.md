# Play 2: Operators

**Creating Observables**
------
Operators that originate new Observables.

- [just](http://reactivex.io/documentation/operators/just.html)

- [from](http://reactivex.io/documentation/operators/from.html)

- [range](http://reactivex.io/documentation/operators/range.html)

*TO BE CONTINUED*

*Gotcha:*

    Observable.just(someList) will give you 1 emission - a List.

    Observable.from(someList) will give you N emissions - each item in the list.

