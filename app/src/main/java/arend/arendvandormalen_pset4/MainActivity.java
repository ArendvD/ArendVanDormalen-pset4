package arend.arendvandormalen_pset4;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    DBHelper dbHelper;
    int[] colorList = new int[]{Color.WHITE, Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE};
    int colorListPosition = 0;
    boolean editingMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.to_do_list);
        listView.setBackgroundColor(colorList[colorListPosition]);
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

        // Create temporary Task object to pass to helper class
        Task newTask = new Task();
        newTask.setTask(newTaskString);
        newTask.setChecked("no");

        // Add task to database
        dbHelper.create(newTask);
        dbHelper.close();

        // Empty input section for user after adding task
        newTaskView.setText("");
        showAll();
    }

    public void showAll(){

        final DBHelper dbHelper = new DBHelper(this);
        ArrayList<Task> toDoList = dbHelper.read();

        TaskAdapter taskAdapter = new TaskAdapter(this, toDoList);

        listView.setAdapter(taskAdapter);
/*
        // Changes color of items
        for (int i = 0; i < listView.getCount(); i++) {

            // iterate through color list
            if (colorListPosition < colorList.length-1)
                colorListPosition++;
            else
                colorListPosition = 0;

            View ll = listView.getChildAt(i);
            ll.setBackgroundColor(colorList[colorListPosition]);
        }
*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Task selectedTask = (Task)adapterView.getItemAtPosition(position);
                String selectedText = selectedTask.getTask();

                //if(!editingMode) {
                editTask(selectedTask, view);
                //}


            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id){

                Task deleteTask = (Task) adapterView.getItemAtPosition(position);
                String deleteID = deleteTask.getId();

                dbHelper.delete(deleteID);
                showAll();

                return true;
            }
        });

    }

    public void editTask(final Task task, final View view){

        editingMode = true;

        final ViewSwitcher viewSwitcher = (ViewSwitcher)view.findViewById(R.id.switcher);
        viewSwitcher.showNext();

        // Hide Add-button when editing
        final LinearLayout bottomBar = (LinearLayout)findViewById(R.id.bottom_bar);
        bottomBar.setVisibility(View.GONE);

        final EditText editText = (EditText)view.findViewById(R.id.task_text_edit);
        editText.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(event.getAction() == KeyEvent.ACTION_DOWN &&
                        keyCode == KeyEvent.KEYCODE_ENTER){
                    String newTaskText = editText.getText().toString();
                    task.setTask(newTaskText);

                    final DBHelper dbHelper = new DBHelper(MainActivity.this);
                    dbHelper.update(task);

                    // Resetting
                    viewSwitcher.showNext();
                    bottomBar.setVisibility(View.VISIBLE);
                    editingMode = false;

                    showAll();

                    return true;
                }
                return false;
            }
        });


    }

}