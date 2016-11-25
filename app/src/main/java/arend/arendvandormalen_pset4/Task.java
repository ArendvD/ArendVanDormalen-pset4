package arend.arendvandormalen_pset4;

import java.util.HashMap;

/**
 * Created by Arend on 2016-11-22.
 * Object for single task in to-do list.
 */

public class Task {

    public int id;
    public String task;
    public String checked;

    public Task(HashMap<String, String> taskData){
        this.task = taskData.get("task");
        id = 0;
        this.checked = taskData.get("checked");
    }
}
