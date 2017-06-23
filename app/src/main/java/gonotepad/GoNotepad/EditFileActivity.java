package gonotepad.GoNotepad;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
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

    String strTxtBeforeEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_file);

        Intent intentResp = getIntent();
        Bundle bundle = intentResp.getExtras();
        final String strFileName=bundle.getString(KEY_NAME);

        ((TextView)findViewById(R.id.edtFileTempName)).setText(strFileName);

        try {
        strTxtBeforeEdit = loadClickedFile(strFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //going back to main activity
        findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Animation animation = AnimationUtils.loadAnimation(EditFileActivity.this,R.anim.bubble);
//                findViewById(R.id.imgBack).startAnimation(animation);

                gotoMainActivity();
            }
        });


        //deleting item from the list
        findViewById(R.id.imgDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Animation animation = AnimationUtils.loadAnimation(EditFileActivity.this,R.anim.bubble);
//                findViewById(R.id.imgDelete).startAnimation(animation);

                AlertDialog.Builder builder = new AlertDialog.Builder((EditFileActivity.this));
                builder.setIcon(R.drawable.ic_error_48px);
                builder.setTitle("Warning: Delete");
                builder.setMessage("    Delete the file permanently ?");

                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        String filePath = getFilesDir().getAbsolutePath();
                        File file = new File(filePath,strFileName);
                        Boolean deletedFileStatus = file.delete();
                        Toast.makeText(EditFileActivity.this,"Note Deleted",Toast.LENGTH_SHORT).show();
                        gotoMainActivity();

                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


                builder.create().show();

            }
        });

        //saving-writing to the file
        findViewById(R.id.imgSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(EditFileActivity.this,R.anim.bubble);
                findViewById(R.id.imgSave).startAnimation(animation);

                String strTxtafterEdit = ((EditText)findViewById(R.id.edtTempNotepad)).getText().toString();

                if((strTxtBeforeEdit.equals(strTxtafterEdit))){
                    Toast.makeText(EditFileActivity.this,"No changes made",Toast.LENGTH_SHORT).show();
                }else {
                    ((EditText)findViewById(R.id.edtTempNotepad)).setText(" ");
                    ((EditText)findViewById(R.id.edtTempNotepad)).setText(strTxtafterEdit);
                    //If we don't do above,clicking on save will make notepad empty, so we have
                    //to show the entire text on notepad and hence the above line


                    FileOutputStream fos = null;
                    try {
                        //open file output takes 2nd parameter as MODE_ in int and because we need to
                        // make that other than MODE_APPEND, we gave a random int number....11
                        fos = openFileOutput(strFileName,11);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    try {
                        fos.write(strTxtafterEdit.getBytes());
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(EditFileActivity.this,"Changes saved",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private String loadClickedFile(String strFileName) throws IOException {

       // File file = getFilesDir();

        //String str = new File(strFileName).getAbsoluteFile().toString();

        FileInputStream fis = openFileInput(strFileName);

        StringBuilder builder = new StringBuilder();

        while (true){
            int ch = fis.read();
            if(ch == -1) break;
            else builder.append((char)ch);
        }

        ((EditText)findViewById(R.id.edtTempNotepad)).setText(builder.toString());

        return builder.toString();

    }

    private void gotoMainActivity() {
        Intent intent=new Intent(EditFileActivity.this,EditActivity.class);
        startActivity(intent);
        finish();
    }
}
