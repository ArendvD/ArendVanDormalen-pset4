package arend.arendvandormalen_pset4;

/**
 * Created by Arend on 2016-11-22.
 * Object for single task in to-do list.
 */

public class Task {

    public int id;
    public String task;
    public String checked;

    public Task(String task, String checked){
        this.task = task;
        id = 0;
        this.checked = checked;
    }
}
