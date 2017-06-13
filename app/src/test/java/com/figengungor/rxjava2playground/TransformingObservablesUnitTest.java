package com.figengungor.rxjava2playground;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static org.junit.Assert.assertEquals;

/**
 * Created by figengungor on 6/12/2017.
 */

public class TransformingObservablesUnitTest {

    class User {
        String name;
        List<Movie> movieCollection;

        public User(String name, List<Movie> movieCollection) {
            this.name = name;
            this.movieCollection = movieCollection;
        }
    }

    class Movie {
        String name;
        Integer year;
        List<Player> playerList;

        public Movie(String name, Integer year, List<Player> playerList) {
            this.name = name;
            this.year = year;
            this.playerList = playerList;
        }
    }

    class Player {
        String name;

        public Player(String name) {
            this.name = name;
        }
    }

    User[] userList;
    Player leo, katewinslet, edwardnorton, bradpitt;
    Movie titanic, fightclub, inception, benjaminbutton;
    Observable<User> userObservable;

    @Before
    public void setup() {
        leo = new Player("Leonardo Di Caprio");
        katewinslet = new Player("Kate Winslet");
        edwardnorton = new Player("Edward Norton");
        bradpitt = new Player("Brad Pitt");
        titanic = new Movie("Titanic", 1997, Arrays.asList(leo, katewinslet));
        fightclub = new Movie("Fight Club", 1999, Arrays.asList(edwardnorton, bradpitt));
        inception = new Movie("Inception", 2010, Arrays.asList(leo));
        benjaminbutton = new Movie("Benjamin Button", 2008, Arrays.asList(bradpitt));
        User mahmut = new User("Mahmut", Arrays.asList(titanic, fightclub));
        User ece = new User("Ece", Arrays.asList(titanic, inception));
        User kamil = new User("Kamil", Arrays.asList(fightclub, benjaminbutton, inception));
        userList = new User[]{mahmut, ece, kamil};
        userObservable = Observable.fromArray(userList);
    }

    @Test
    public void mapOperator(){
        String[] expectedList = {"Mahmut","Ece","Kamil"};
        final ArrayList<String> actualList = new ArrayList<>();

        Observable<String> stringObservable = userObservable.map(new Function<User, String>() {
            @Override
            public String apply(@NonNull User user) throws Exception {
                return user.name;
            }
        });

        stringObservable.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                actualList.add(s);
            }
        });

        assertEquals(Arrays.asList(expectedList), actualList);
    }


    @Test
    public void flatMapOperator() {
        Movie[] expectedList = {titanic, fightclub, titanic, inception, fightclub, benjaminbutton, inception};
        final ArrayList<Movie> actualList = new ArrayList<>();
        Observable<Movie> movieObservable = userObservable.flatMap(new Function<User, ObservableSource<Movie>>() {
            @Override
            public ObservableSource<Movie> apply(@NonNull User user) throws Exception {
                return Observable.fromIterable(user.movieCollection);
            }
        });
        movieObservable.subscribe(new Consumer<Movie>() {
            @Override
            public void accept(@NonNull Movie movie) throws Exception {
                actualList.add(movie);
            }
        });

        assertEquals(Arrays.asList(expectedList), actualList);

    }

}
