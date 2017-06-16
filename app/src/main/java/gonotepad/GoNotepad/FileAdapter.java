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
 * Created by rtb on 14/6/17.
 */

public class FileAdapter extends BaseAdapter {
    private final Context context;
    private final List<FileItem> fileDataset;
    private LayoutInflater inflater;

    public FileAdapter(Context context, List<FileItem> fileDataset) {
        this.context = context;
        this.fileDataset = fileDataset;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return fileDataset.size();
    }

    @Override
    public FileItem getItem(int position) {
        return fileDataset.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View root = null;
        if(convertView == null){
            root = inflater.inflate(R.layout.file_item,parent,false);
        }else{
            root = convertView;
        }

        ((ImageView)root.findViewById(R.id.imgIcon)).setImageResource(fileDataset.get(position).imgId);
        ((TextView)root.findViewById(R.id.txtFileName)).setText(fileDataset.get(position).txtFlName);
        ((TextView)root.findViewById(R.id.txtFileLastModified)).setText(fileDataset.get(position).txtFlMdfd);
        ((TextView)root.findViewById(R.id.txtFileSize)).setText(fileDataset.get(position).txtFsz);

        return root;
    }
}
