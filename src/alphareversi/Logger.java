package alphareversi;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private String name;
    private SimpleDateFormat format = new SimpleDateFormat("H:m:s.S");

    public Logger(String name) {
        this.name = name;
    }

    private String time() {
        return this.format.format(new Date());
    }

    private String message(String text) {
        String prefix = "[" + this.time() + "] [" + this.name + "] ";
        return prefix + text.replace("\n", "\n" + prefix.replaceAll(".", " "));
    }

    public Logger err(String text) {
        System.out.println(this.message(text));
        return this;
    }

    public Logger log(String text) {
        System.out.println(this.message(text));
        return this;
    }
}
