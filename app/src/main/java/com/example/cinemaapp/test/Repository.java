package com.example.cinemaapp.test;

import com.example.cinemaapp.models.Film;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    static List<Film> filmList = new ArrayList<>();


    public static List<Film> getHardcodedList() {
        return filmList;
    }
}
