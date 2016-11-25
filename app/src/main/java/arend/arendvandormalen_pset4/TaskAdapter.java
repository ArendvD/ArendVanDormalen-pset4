
package arend.arendvandormalen_pset4;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Arend on 2016-11-25.
 * Adapter putting tasks in right place
 */


class TaskAdapter extends ArrayAdapter<Task> {

    ArrayList<Task> taskData;
    Context context;

    public TaskAdapter(Context context, ArrayList<Task> taskData){
        super(context, R.layout.single_task, taskData);
        this.taskData = taskData;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View taskView = inflater.inflate(R.layout.single_task, parent, false);

        String taskText = getItem(position).getTask();
        TextView textView = (TextView)taskView.findViewById(R.id.task_text);
        textView.setText(taskText);

        // Saves checkbox values on closing/rotating
        CheckBox cb = (CheckBox)taskView.findViewById(R.id.task_checked);
        String taskChecked = getItem(position).getChecked();
        if(taskChecked.equals("yes")){
            cb.setChecked(true);
        } else if(taskChecked.equals("no")){
            cb.setChecked(false);
        }

        cb.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Task task = getItem(position);
                if (task != null) {
                    final DBHelper dbHelper = new DBHelper(context);

                    if(task.getChecked().equals("no")) {
                        task.setChecked("yes");
                        dbHelper.update(task);
                    }
                    else if(task.getChecked().equals("yes")){
                        task.setChecked("no");
                        dbHelper.update(task);
                    }
                    else {
                        Log.d("Wrong check", task.getChecked());
                    }
                    dbHelper.close();
                }
            }

        });

        return taskView;

    }

}

