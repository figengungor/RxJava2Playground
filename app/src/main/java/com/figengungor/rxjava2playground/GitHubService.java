package com.figengungor.rxjava2playground;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by figengungor on 5/31/2017.
 */

public interface GitHubService {
    @GET("/users/{username}")
    Observable<User> getUser(@Path("username") String username);
}
