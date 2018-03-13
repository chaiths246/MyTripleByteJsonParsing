package com.example.chaithra.mytriplebyte;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Meow>> {
    private static final String MEO_URL = "https://chex-triplebyte.herokuapp.com/api/cats?page=0";
    private static final int MEO_LOADER_ID = 1;
    ListView listView;
    MeowAdapter meowAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.newslist);
        meowAdapter = new MeowAdapter(this, 0);
        listView.setAdapter(meowAdapter);
        TextView emptystate = (TextView) findViewById(R.id.emptyview);
        emptystate.setText(("Loading"));
        boolean isonline = isOnline();
        if (isonline == false) {
            emptystate.setText("No internet");
//            View loadingIndicator = findViewById(R.id.progressbar);
//            loadingIndicator.setVisibility(View.GONE);
        } else {
            android.app.LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(MEO_LOADER_ID, null, this);

        }
    }
    @Override
    public Loader<ArrayList<Meow>> onCreateLoader(int id, Bundle args) {
        return new MeowLoader(this, MEO_URL);

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Meow>> loader, ArrayList<Meow> data) {


        meowAdapter.clear();
        if (data != null) {
            meowAdapter.addAll(data);
        }
    }
    @Override
    public void onLoaderReset(Loader<ArrayList<Meow>> loader) {
        if (meowAdapter != null) {
            meowAdapter.clear();
        }
    }
}


