package alphareversi.commands;

/**
 * Created by Joost van Berkel on 3/24/2016.
 */
public abstract class SendCommand {
    private String method;
    private String action;

    //OK Message response
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public abstract String toString();
}
