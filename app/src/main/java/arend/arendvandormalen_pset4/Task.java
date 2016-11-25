package arend.arendvandormalen_pset4;

import java.util.HashMap;

/**
 * Created by Arend on 2016-11-22.
 * Object for single task in to-do list.
 */

public class Task {

    public String id;
    public String task;
    public String checked;

    public Task(){
        task = "";
        id = "";
        checked = "";
    }

    // Set-methods
    public void setChecked(String checked) {
        this.checked = checked;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTask(String task) {
        this.task = task;
    }

    // Get-methods
    public String getChecked() {
        return checked;
    }

    public String getId() {
        return id;
    }

    public String getTask() {
        return task;
    }
}
