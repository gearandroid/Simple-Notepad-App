package gonotepad.GoNotepad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rtb on 13/6/17.
 */

public class MyAdapter extends BaseAdapter {

    private final Context context;
    private final List<MyItem> dataset;

    private LayoutInflater inflater;

    public MyAdapter( Context context,List<MyItem> dataset) {

        this.context = context;
        this.dataset = dataset;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataset.size();
    }

    @Override
    public Object getItem(int position) {
        return dataset.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rootView = null;
        if(convertView == null){
            rootView = inflater.inflate(R.layout.grid_view,parent,false);
        }else{
            rootView=convertView;
        }

        ((ImageView)rootView.findViewById(R.id.imgIcon)).setImageResource(dataset.get(position).imgId);
        ((TextView)rootView.findViewById(R.id.txtIcon)).setText(dataset.get(position).txtId);

        return rootView;
    }
}
