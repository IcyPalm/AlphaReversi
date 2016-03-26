package alphareversi.commands;

/**
 * Created by timmein on 24/03/16.
 */
public class RecvCommand {
    private String Type;
    private String Method;
    private String Action;

    public String getType() {
        return  Type;
    }

    public void setType(String type) {
        this. Type = type;
    }

    public String getMethod() {
        return Method;
    }

    public void setMethod(String method) {
        this.Method = method;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        this.Action = action;
    }
}
