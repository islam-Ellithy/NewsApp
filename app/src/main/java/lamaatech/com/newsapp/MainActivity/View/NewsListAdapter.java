package lamaatech.com.newsapp.MainActivity.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lamaatech.com.newsapp.News;
import lamaatech.com.newsapp.R;


/**
 * Created by MrHacker on 9/19/2017.
 */

public class NewsListAdapter extends ArrayAdapter<News> {
    private List<News> mItems;
    private final Context mContext;
    private final LayoutInflater inflater;

    public NewsListAdapter(Context context, ArrayList<News> items) {
        super(context, R.layout.list_item_layout, items);

        mContext = context;
        mItems = items;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        News item = getItem(position);
        if (item != null) {
            holder.title.setText(item.getTitle());
            holder.author.setText(item.getAuthor());
            holder.date.setText(item.getDate());
            holder.section.setText(item.getSection());
        }

        return convertView;
    }

    public void updateList(List<News> newList) {
        mItems = newList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mItems != null)
            return mItems.size();
        return 0;
    }

    @Override
    public News getItem(int position) {
        return mItems.get(position);
    }

    private class ViewHolder {
        TextView title;
        TextView author;
        TextView date;
        TextView section;

        ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.title);
            author = (TextView) view.findViewById(R.id.author);
            date = (TextView) view.findViewById(R.id.date);
            section = (TextView) view.findViewById(R.id.sectionName);
        }
    }
}

