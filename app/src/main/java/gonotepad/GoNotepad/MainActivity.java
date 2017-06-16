package gonotepad.GoNotepad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<MyItem> dataset = new ArrayList<>();
        dataset.add(new MyItem(R.drawable.ic_add,"ADD"));
        dataset.add(new MyItem(R.drawable.ic_edit,"EDIT"));

        MyAdapter myAdapter = new MyAdapter(this,dataset);
        ((GridView)findViewById(R.id.grdLayout)).setAdapter(myAdapter);

        ((GridView) findViewById(R.id.grdLayout)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if(dataset.get(position).equals(dataset.get(0))){
                    Intent intent = new Intent(MainActivity.this,AddActivity.class);
                    startActivity(intent);
                }else if (dataset.get(position).equals(dataset.get(1))){
                    Intent intent = new Intent(MainActivity.this,EditActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
