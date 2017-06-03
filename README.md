#Play 1 : RetroGitHub

### DisposableActivity

A simple Get request (Networking) - Get user data from GitHub api

Meet with Observable, Schedulers, Observer and Disposable.

1. Make a network request with Retrofit which will return an Observable<T> object as you defined in your method interface.
2. Because you're making an asynchronous call, subscribe to this operation on an IO thread.
3. Because you want to use results on your UI thread, observe the result on UI thread.
4. In order to start observing the Observable, you have options:

    a) use **subscribe** with **Observer<T>** which has 4 methods to implement (*subscribe returns void*)

        * onSubscribe(Disposable d) => Keep a reference to this subscription with Disposable so you can unsubscribe(dispose) when you're done.
        * onNext(T t) => You get your data, use it as you like.
        * onError(Throwable throwable) => You get an error.
        * onComplete() => Operation completed, No data to emit.

    b) use **subscribeWith** with **DisposableObserver<T>** which has 3 methods to implement (*subscribeWith returns the given observer*)

        * onNext(T t)
        * onError(Throwable throwable)
        * onComplete()

### CompositeDisposableActivity

Add all your disposables into CompositeDisposable and dispose them all at once when you're done.






