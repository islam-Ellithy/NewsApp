package lamaatech.com.newsapp.Parsing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import lamaatech.com.newsapp.News;


public class NewsParser {
    public static ArrayList<News> parseJsonToListOfNews(String jsonStr) {

        ArrayList<News> newsArrayList = new ArrayList<>();
        try {

            JSONObject jsonResponse = new JSONObject(jsonStr);
            JSONObject jsonResults = jsonResponse.getJSONObject("response");
            JSONArray arrayResults;

            if (jsonResults.has("results")) {
                arrayResults = jsonResults.getJSONArray("results");

                for (int i = 0; i < arrayResults.length(); i++) {
                    String author = "";
                    JSONObject oneResult = arrayResults.getJSONObject(i);
                    String webTitle = oneResult.getString("webTitle");
                    String webUrl = oneResult.getString("webUrl");
                    String date = oneResult.getString("webPublicationDate");
                    String sectionName = oneResult.getString("sectionName");
                    JSONArray tags = oneResult.getJSONArray("tags");

                    if (tags.length() == 0) {
                        author = null;
                    } else {
                        for (int j = 0; j < tags.length(); j++) {
                            JSONObject object = tags.getJSONObject(j);
                            author += object.getString("webTitle");
                        }
                    }

                    newsArrayList.add(new News(webTitle, author, webUrl, date, sectionName));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsArrayList;
    }
}
