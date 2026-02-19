import java.util.EventListener;

//The DetailListener interface is used to listen for events in the *Page files. It has one method, detailEventOccurred, that is called when the event is triggered.
public interface DetailListener extends EventListener {
    public void detailEventOccurred(DetailEvent event);
}
