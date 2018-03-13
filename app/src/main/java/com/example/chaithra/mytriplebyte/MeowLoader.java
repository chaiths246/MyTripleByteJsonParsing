package com.example.chaithra.mytriplebyte;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by chaithra on 3/12/18.
 */

public class MeowLoader extends AsyncTaskLoader<ArrayList<Meow>> {
    public static final String LOG_TAG = MeowLoader.class.getSimpleName();
    private String url;

    public MeowLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Meow> loadInBackground() {
        if (url == null) {
            return null;
        }
        ArrayList<Meow> newslist = Utils.fetchMeowData(url);
        if (newslist.size() == 0) {
            Log.d(LOG_TAG, "news list is empty");
        }
        return newslist;
    }
}
