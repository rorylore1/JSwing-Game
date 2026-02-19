import java.util.EventObject;

//The DetailEvent class is used to create events in the *Page files.
public class DetailEvent extends EventObject {
    private final String key;
    private final String mode;
    public DetailEvent(Object source, String key, String mode) {
        super(source);
        this.key = key;
        this.mode = mode;
    }
    public String getKey() {
        return key;
    }
    public String getMode() {
        return mode;
    }
}