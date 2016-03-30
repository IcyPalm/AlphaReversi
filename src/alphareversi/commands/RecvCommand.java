package alphareversi.commands;

/**
 * Created by timmein on 24/03/16.
 */
public class RecvCommand {
    private String type;
    private String method;
    private String action;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
