package lamaatech.com.newsapp.Networking;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lamaatech.com.newsapp.News;
import lamaatech.com.newsapp.Parsing.NewsParser;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private static final String TAG = NewsLoader.class.getSimpleName();
    private String topic;

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public NewsLoader(Context context, String newTopic) {
        super(context);
        topic = newTopic;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {

        ArrayList<News> newsList = null;
        try {
            if (topic != null) {
                String jsonResponse = NetworkUtils.doHttpRequest(topic);
                newsList = NewsParser.parseJsonToListOfNews(jsonResponse);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error Loader LoadInBackground: ", e);
        }
        return newsList;
    }
}