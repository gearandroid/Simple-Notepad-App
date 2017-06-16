package gonotepad.GoNotepad;

import android.content.Intent;
import android.os.Parcelable;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    public static final String KEY_NAME = "fileName";
    public static final String KEY_INFO = "noteInfo";
    public static final String KEY_POSITION = "clickedPosition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //loading all the files present in the internal storage directory
        loadFileList();

        //if back id pressed-MainActivity will be shown
        findViewById(R.id.imgListBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(EditActivity.this,R.anim.bubble);
                findViewById(R.id.imgListBack).startAnimation(animation);

                Intent intentList = new Intent(EditActivity.this,MainActivity.class);
                startActivity(intentList);
                finish();
            }
        });
}

    private void loadFileList() {

        final List<FileItem> fileItems = new ArrayList<>();

        File file = getFilesDir();
        File []files = file.listFiles();

        for(File fl:files){
            Date date = new Date(fl.lastModified());

//            int hourOfDay=0;
//            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mmm:ss a");
//            Calendar dateTime = Calendar.getInstance();
//            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);

            fileItems.add(new FileItem(R.drawable.ic_note_black_24dp,fl.getName(),""+(new SimpleDateFormat("dd-MMM-yyyy HH-mm-ss").format(date)),""+fl.length(),""));
        }

        ((ListView)findViewById(R.id.lstListOfFiles)).setAdapter(new FileAdapter(this,fileItems));

        //opening individual file in new activity for editing with delete and back option
        ((ListView) findViewById(R.id.lstListOfFiles)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FileItem clickedItem = fileItems.get(position);

                String strFileName = clickedItem.txtFlName.toString();


                //Reading file here: opening file by the name of the file
                StringBuilder builder = new StringBuilder();
                FileInputStream fis = null;
                try {
                    fis = openFileInput(strFileName);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                while (true){
                    int ch = 0;
                    try {
                        ch = fis.read();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(ch == -1) break;
                    else {
                        builder.append((char)ch);
                    }
                }

                String strNoteInfo = builder.toString();

                //putting file's name and notepad text into bundles
                Bundle bundle=new Bundle();

                //bundle.putInt(KEY_POSITION,position);
                bundle.putString(KEY_NAME,strFileName);
                //bundle.putString(KEY_INFO,strNoteInfo);

                startActivity(new Intent(EditActivity.this,EditFileActivity.class).putExtras(bundle));
            }
        });

    }
}

