package lamaatech.com.newsapp.MainActivity.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lamaatech.com.newsapp.Networking.CheckNetwork;
import lamaatech.com.newsapp.Networking.NewsLoader;
import lamaatech.com.newsapp.News;
import lamaatech.com.newsapp.R;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private final static String TAG = MainActivity.class.getSimpleName();
    private NewsListAdapter adapter;
    @BindView(R.id.listview)
    protected ListView listViewNews;
    private ArrayList<News> newsArrayList = null;
    private Integer position;
    private CheckNetwork checkNetwork;

    @BindView(R.id.empty_list_item)
    protected TextView emptyListItem;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("list", newsArrayList);

        int position = listViewNews.getVerticalScrollbarPosition();

        outState.putInt("position", position);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        checkNetwork = new CheckNetwork(this);

        boolean flag = false;

        if (savedInstanceState != null) {

            newsArrayList = (ArrayList<News>) savedInstanceState.getSerializable("list");

            position = savedInstanceState.getInt("position");

            flag = true;

        }

        adapter = new NewsListAdapter(this, newsArrayList);

        listViewNews.setEmptyView(emptyListItem);

        listViewNews.setAdapter(adapter);

        if (flag) {
            listViewNews.smoothScrollToPosition(position);
        }

        listViewNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = adapter.getItem(position);
                String url = news.getUrl();

                if (url != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    }
                }
            }
        });


        if (checkNetwork.isNetworkAvailable())
            getSupportLoaderManager().initLoader(1, null, this).forceLoad();
        else
            showToast("No internet connection");

    }


    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(MainActivity.this, "Android");
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        Log.d(TAG, "size = " + data.size());
        adapter.updateList(data);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        Log.d(TAG, "OnReset");
        adapter.updateList(new ArrayList<News>());
    }
}
