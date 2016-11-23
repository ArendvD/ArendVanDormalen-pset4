package arend.arendvandormalen_pset4;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ArrayList<Task> tasks = null;
    ListView listView;
    DBHelper dbHelper;
    int[] colorList = new int[]{Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.to_do_list);


/*
        listView.setOnItemClickListener();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

        });
*/
        showAll();
    }

    @Override
    protected void onResume(){
        super.onResume();
        showAll();
    }

    public void addTask(View view){
        dbHelper = new DBHelper(this);
        Toast.makeText(this, "Adding Task", Toast.LENGTH_SHORT).show();
        EditText newTaskView = (EditText)findViewById(R.id.add_bar);
        String newTaskString = newTaskView.getText().toString();
        Log.d("Adding", newTaskString);
        Task newTask = new Task(newTaskString, "no");
        if (newTask.id == 0){
            dbHelper.create(newTask);
        } else {
            dbHelper.update(newTask);
        }
        dbHelper.close();
        newTaskView.setText("");
        showAll();
    }



    public void showAll(){

        final DBHelper dbHelper = new DBHelper(this);
        ArrayList<HashMap<String,String>> toDoList = dbHelper.read();
        SimpleAdapter taskAdapter = new SimpleAdapter(this, toDoList,
                R.layout.single_task, new String[]{"id", "task"},
                new int[]{R.id.task_id, R.id.task_text});

        listView.setAdapter(taskAdapter);
        listView.setBackgroundColor(colorList[2]);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Item Clicked " + position, Toast.LENGTH_SHORT).show();

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id){
                Toast.makeText(MainActivity.this, "Item to Delete " + position, Toast.LENGTH_SHORT).show();

                Task deleteTask = (Task) adapterView.getItemAtPosition(position);
                dbHelper.delete(deleteTask);

                return true;
            }
        });

    }
}