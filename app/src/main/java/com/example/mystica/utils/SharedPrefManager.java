package com.example.mystica.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mystica.models.Thought;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SharedPrefManager {

    private static final String PREF_NAME = "MysticaPrefs";
    private static final String KEY_THOUGHTS = "thoughtList";
    private static final String KEY_USERNAME = "username";

    // Save user's full name
    public static void saveUsername(Context context, String username) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_USERNAME, username).apply();
    }

    // Get user's name or fallback to "User"
    public static String getUsername(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String username = prefs.getString(KEY_USERNAME, "");
        return username.isEmpty() ? "User" : username;
    }

    // Add a new thought
    public static void addThought(Context context, String message) {
        List<Thought> currentList = getThoughts(context);
        String date = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
        currentList.add(new Thought(message, date));

        String json = new Gson().toJson(currentList);
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_THOUGHTS, json).apply();
    }

    // Get all thoughts
    public static List<Thought> getThoughts(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_THOUGHTS, null);

        if (json == null) return new ArrayList<>();

        Type type = new TypeToken<List<Thought>>() {}.getType();
        return new Gson().fromJson(json, type);
    }

    // Get today's thoughts
    public static List<Thought> getTodayThoughts(Context context) {
        List<Thought> allThoughts = getThoughts(context);
        String today = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());

        List<Thought> todayList = new ArrayList<>();
        for (Thought t : allThoughts) {
            if (t.getDate().equals(today)) {
                todayList.add(t);
            }
        }
        return todayList;
    }

    // Remove a specific thought
    public static void removeThought(Context context, Thought thoughtToRemove) {
        List<Thought> currentList = getThoughts(context);
        currentList.removeIf(t ->
                t.getMessage().equals(thoughtToRemove.getMessage()) &&
                        t.getDate().equals(thoughtToRemove.getDate())
        );

        String json = new Gson().toJson(currentList);
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_THOUGHTS, json).apply();
    }

    // Clear all thoughts
    public static void clearAllThoughts(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(KEY_THOUGHTS).apply();
    }
}
