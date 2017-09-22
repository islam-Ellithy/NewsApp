package lamaatech.com.newsapp.Networking;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by MrHacker on 9/21/2017.
 */

public class NetworkUtils {
    final static String baseUrl = "http://content.guardianapis.com/search?order-by=newest&show-references=author&show-tags=contributor&api-key=test&q=";
    final static String TAG = NetworkUtils.class.getSimpleName();

    static URL createUrl(String topic) {
        //http://content.guardianapis.com/search?q=debates&api-key=test
        String url = baseUrl + topic;
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error URL: ", e);
            return null;
        }
    }


    static String doHttpRequest(String topic) throws IOException {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String newsJsonStr = null;

        URL url = createUrl(topic);

        try {
            if (url != null) {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(String.valueOf(line + "\n"));
                }

                if (buffer.length() == 0) {
                    return null;
                }
                newsJsonStr = buffer.toString();
            }
        } catch (IOException e) {
            Log.e(TAG, "Error ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }
        return newsJsonStr;
    }

}
