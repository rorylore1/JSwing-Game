import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.EventListenerList;

//The LoginPage class creates the login page.
public class LoginPage extends JPanel{

    private EventListenerList listenerList = new EventListenerList();   //list of listeners for the login event

    //Constructor for the LoginPage class. It creates the Swing components and sets up the layout.
    public LoginPage() {

        //create Swing components
        JLabel welcome = new JLabel("Welcome to my JSwing Farming Game!");
        JLabel usernamelabel = new JLabel("Player Name: ");
        final JTextField usernametext = new JTextField(10);
        JRadioButton freePlayMode = new JRadioButton("Free Play Mode");
        JRadioButton survivalMode = new JRadioButton("Survival Mode");
        freePlayMode.setActionCommand("freePlayMode");
        survivalMode.setActionCommand("survivalMode");
        //add radio buttons to group so only one can be selected at a time
        ButtonGroup modes = new ButtonGroup();
        modes.add(freePlayMode);
        modes.add(survivalMode);
        JButton submitbutton = new JButton("Begin");

        //Listeners section/////////////////////////
        submitbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            try {
                 modes.getSelection().getActionCommand();
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(null, "Please select a game mode.");
                return;
            }
                fireDetailEvent(new DetailEvent(this, usernametext.getText(), modes.getSelection().getActionCommand()));
            }
        });

        //set page container layout and constraints
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        //Add compoennts to the page using the constraints
        //First Row/////////////////////////
        gc.anchor=GridBagConstraints.CENTER;
        gc.gridwidth=2;
        gc.ipady=40;
        gc.weighty=0.5;

        gc.gridx=0;
        gc.gridy=0;
        add(welcome, gc);

        //Second Row/////////////////////////        
        gc.anchor = GridBagConstraints.LINE_END;
        gc.weightx = 1;
        gc.gridwidth=1;
        gc.ipady=0;

        gc.gridx = 0;
        gc.gridy = 2;
        add(usernamelabel, gc);

        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx=1;
        gc.gridy=2;
        add(usernametext, gc);

        //Next Rows/////////////////////////
        gc.anchor = GridBagConstraints.CENTER;
        gc.gridwidth=2;
        gc.ipady=0;
        gc.weighty=0;

        gc.gridx = 0;
        gc.gridy = 3;
        add(freePlayMode, gc);

        gc.gridx = 0;
        gc.gridy = 4;
        gc.ipady=20;
        add(survivalMode, gc);

        gc.ipady=0;
        gc.weighty=0.5;
        gc.gridx = 0;
        gc.gridy = 5;
        add(submitbutton, gc);
    }

    //Event firing and listener methods.
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