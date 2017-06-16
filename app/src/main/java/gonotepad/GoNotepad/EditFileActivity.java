package gonotepad.GoNotepad;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import static gonotepad.GoNotepad.EditActivity.KEY_NAME;

public class EditFileActivity extends AppCompatActivity {

    String strTxtFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_file);

        Intent intentResp = getIntent();
        Bundle bundle = intentResp.getExtras();
        final String strFileName=bundle.getString(KEY_NAME);

        ((TextView)findViewById(R.id.edtFileTempName)).setText(strFileName);

        try {
        strTxtFile = loadClickedFile(strFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


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

                String afterEdit = ((EditText)findViewById(R.id.edtTempNotepad)).getText().toString();

                if((strTxtFile.equals(afterEdit))){
                    Toast.makeText(EditFileActivity.this,"Nothing edited...!!",Toast.LENGTH_LONG).show();
                }else {
                    //((EditText)findViewById(R.id.edtTempNotepad)).setText(" ");

                    FileOutputStream fos = null;
                    try {
                        fos = openFileOutput(strFileName, MODE_APPEND);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    try {
                        fos.write(afterEdit.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private String loadClickedFile(String strFileName) throws IOException {

        File file = getFilesDir();

        String str = new File(strFileName).getAbsoluteFile().toString();

        FileInputStream fis = openFileInput(strFileName);

        StringBuilder builder = new StringBuilder();

        while (true){
            int ch = fis.read();
            if(ch == -1) break;
            else builder.append((char)ch);
        }

        ((EditText)findViewById(R.id.edtTempNotepad)).setText(builder.toString());

        String beforeEdit = builder.toString();

        return beforeEdit;

    }

    private void gotoMainActivity() {
        Intent intent=new Intent(EditFileActivity.this,EditActivity.class);
        startActivity(intent);
        finish();
    }
}
