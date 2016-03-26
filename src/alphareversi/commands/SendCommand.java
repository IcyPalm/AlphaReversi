package alphareversi.commands;

/**
 * Created by Joost van Berkel on 3/24/2016.
 */
public abstract class SendCommand {
    private String Method;
    private String Action;

    //OK Message response
    private String Status;

    public String getMethod() {
        return Method;
    }

    public void setMethod(String method) {
        Method = method;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public abstract String toString();
}
