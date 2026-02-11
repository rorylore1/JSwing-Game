
import java.util.EventObject;

public class DetailEvent extends EventObject {
    private String text, key;
    public DetailEvent(Object source, String key) {
        super(source);
        System.out.println("DetailEvent was triggered");
        this.key = key;
        switch(key) {
            case "GoFarming":
                text = "We went farming.\n";
                break;
            case "GoHunting":
                text = "We went hunting.\n";
                break;
            case "GoForaging":
                text = "We went foraging.\n";
                break;
            case "GoToBed":
                text = nightlyEvents();
                break;
            default:
                //LoginPage, so text = username
                text = key;
                break;
        }
    }
    public String getDisplayText() {
        return text;
    }
    public String getKey() {
        return key;
    }
    private String nightlyEvents() {
        return "Night Falls...\nCritters roam in the night.\nDawn begins, a new day...\n";
    }
}

