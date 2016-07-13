package tukang.id.cancelbooking3updated;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

/**
 * Created by irwan on 6/24/16.
 */
public class CustomListAdapter extends BaseAdapter {
    String[] names;
    Context context;
    LayoutInflater inflter;
    AppCompatActivity appCompatActivity;

    public CustomListAdapter(Context context, String[] names, AppCompatActivity appCompatActivity) {
        this.context = context;
        this.names = names;
        inflter = (LayoutInflater.from(context));
        this.appCompatActivity = appCompatActivity;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
        view = inflter.inflate(R.layout.item_list, null);

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.item_list, null);
        }

        final CheckedTextView simpleCheckedTextView = (CheckedTextView) view.findViewById(R.id.simpleCheckedTextView);
        simpleCheckedTextView.setText(names[position]);

        return view;
    }
}