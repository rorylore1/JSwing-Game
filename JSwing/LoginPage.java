import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.EventListenerList;

public class LoginPage extends JPanel{

    private EventListenerList listenerList = new EventListenerList();

    public LoginPage() {

        //create Swing components
        JLabel welcome = new JLabel("Welcome");
        JLabel w1 = new JLabel("Shall we play a game?");
        JLabel usernamelabel = new JLabel("Username: ");
        JLabel passwordlabel = new JLabel("Password: ");

        final JTextField usernametext = new JTextField(10);
        final JPasswordField passwordtext = new JPasswordField(10);

        JButton submitbutton = new JButton("Begin");
        submitbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernametext.getText();
                fireDetailEvent(new DetailEvent(this, username));
            }
        });

        //set page container
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        //first column/////////////////////////

        //cordinates
        gc.anchor=GridBagConstraints.CENTER;
        gc.gridx=0;
        gc.gridy=0;
        gc.gridwidth=2;
        add(welcome, gc);

        gc.gridx=0;
        gc.gridy=1;
        gc.ipady=40;
        add(w1, gc);

        gc.anchor = GridBagConstraints.LINE_END;
        //ratio of the space taken
        gc.weightx = .5;
        gc.weighty = .5;
        gc.gridwidth=1;
        gc.ipady=0;

        //cordinates
        gc.gridx = 0;
        gc.gridy = 2;
        add(usernamelabel, gc);

        gc.gridx = 0;
        gc.gridy = 3;
        add(passwordlabel, gc);

        //second column/////////////////////////
        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx=1;
        gc.gridy=2;
        add(usernametext, gc);

        gc.gridx=1;
        gc.gridy=3;
        add(passwordtext, gc);

        //final row//////////////////////////////
        gc.weighty = 10;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.gridx = 1;
        gc.gridy = 4;
        add(submitbutton, gc);
    }
    public void fireDetailEvent(DetailEvent event) {
        Object[] listeners = listenerList.getListenerList();
        for(int a=0; a < listeners.length; a += 2) {
            //a = class name, a+1 = the actual event
            if(listeners[a] == DetailListener.class) {
                ((DetailListener)listeners[a+1]).detailEventOccurred(event);
            }
        }
    }
    public void addDetailListener(DetailListener listener) {
        listenerList.add(DetailListener.class, listener);
    }
    public void removeDetailListener(DetailListener listener) {
        listenerList.remove(DetailListener.class, listener);
    }

}