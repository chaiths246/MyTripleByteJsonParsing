package com.example.chaithra.mytriplebyte;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by chaithra on 3/12/18.
 */

public class Utils {
    public static final String LOG_TAG = Utils.class.getSimpleName();

    public static ArrayList<Meow> fetchMeowData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        ArrayList<Meow> newslist = jsonconverter(jsonResponse);
        return newslist;
    }
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the News JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                Log.d("Network", "" + line);
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    private static ArrayList<Meow> jsonconverter(String newsJSON) {
        ArrayList<Meow> newsarraylist = new ArrayList<Meow>();
        String result="";
        try {


            JSONArray json =new  JSONArray(newsJSON);
            for (int i = 0; i < json.length(); i++) {
                JSONObject e = json.getJSONObject(i);
                String jsondate = e.getString("timestamp");
                Date date = DateParser.parse(jsondate);
                String newdate = DateParser.toString(date);
                String title = e.getString("title");
                String discription=e.getString("description");
                String image=e.getString("image_url");
newsarraylist.add(new Meow(newdate, image, title,discription));
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newsarraylist;
    }
}
