package gonotepad.GoNotepad;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditFileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_file);

        Intent intentResp = getIntent();

        Bundle bundle = intentResp.getExtras();

        final String strFileName=bundle.getString(EditActivity.KEY_NAME);



        ((TextView)findViewById(R.id.edtFileTempName)).setText(strFileName);

        //going back to main activity
        findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(EditFileActivity.this,R.anim.bubble);
                findViewById(R.id.imgBack).startAnimation(animation);

                gotoMainActivity();
            }
        });


        //deleting item from the list
        findViewById(R.id.imgDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(EditFileActivity.this,R.anim.bubble);
                findViewById(R.id.imgDelete).startAnimation(animation);

                String filePath = getFilesDir().getAbsolutePath();
                File file = new File(filePath,strFileName);
                Boolean deletedFileStatus = file.delete();
                Toast.makeText(EditFileActivity.this,"Note Deleted",Toast.LENGTH_SHORT).show();
                gotoMainActivity();

            }
        });

        findViewById(R.id.imgSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(EditFileActivity.this,R.anim.bubble);
                findViewById(R.id.imgSave).startAnimation(animation);

                FileInputStream fis= null;
                try {
                    fis = openFileInput(strFileName);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                StringBuilder builder = new StringBuilder();
                while (true){
                    int ch = 0;
                    try {
                        ch = fis.read();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(ch == -1) break;
                    else builder.append((char)ch);
                }

                ((EditText)findViewById(R.id.edtTempNotepad)).setText(builder);
            }
        });

    }

    private void gotoMainActivity() {
        Intent intent=new Intent(EditFileActivity.this,EditActivity.class);
        startActivity(intent);
        finish();
    }
}