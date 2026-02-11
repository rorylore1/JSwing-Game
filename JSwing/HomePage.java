import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.EventListenerList;

public class HomePage extends JPanel {

    private EventListenerList listenerList = new EventListenerList();
    private int dayCounter=0;
    private JLabel DisplayDay;
    private JTextArea EventsDisplay;

    public HomePage(Person player, String title) {

        //create vars
        int ToolCount, MedSupplyCount;
        ToolCount = MedSupplyCount = 0;

        //set page container
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        //Top section/////////////////////////
        JPanel Top = new JPanel();
        Top.setLayout(new BoxLayout(Top, BoxLayout.Y_AXIS));
        Top.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel TopTitle = new JLabel(player.GetUsername() + "'s Home");
        TopTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        DisplayDay = new JLabel("Day 0");
        DisplayDay.setAlignmentX(Component.CENTER_ALIGNMENT);
        EventsDisplay = new JTextArea("The sun rises...\nA new day begins.\n");
        EventsDisplay.setEditable(false);
        EventsDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);
        Top.add(TopTitle);
        Top.add(DisplayDay);
        Top.add(EventsDisplay);
        //add to homepage
        gc.fill=GridBagConstraints.BOTH;
        gc.weightx = 1;
        gc.gridwidth=5;
        gc.gridheight=15;
        add(Top, gc);

        //Inventory section/////////////////////////
        JPanel InventoryPanel = new JPanel();
        InventoryPanel.setLayout(new BoxLayout(InventoryPanel, BoxLayout.Y_AXIS));
        InventoryPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel InventoryLabel = new JLabel("Inventory");
        InventoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        InventoryPanel.add(InventoryLabel);
        JLabel FoodLabel = new JLabel("Food: " + player.getFood());
        InventoryPanel.add(FoodLabel);
        JLabel SeedsLabel = new JLabel("Seeds: " + player.getSeeds());
        InventoryPanel.add(SeedsLabel);
        InventoryPanel.add(new JLabel("Tools: " + ToolCount));
        InventoryPanel.add(new JLabel("Medical Supplies: " + MedSupplyCount));
        //Set GridBagConstraints for the second row in the Frame
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = .5;
        gc.weighty = .5;
        gc.gridx=1;
        gc.gridy=1;
        gc.gridwidth=1;
        gc.insets = new Insets(0,10,0,10);
        //add InventoryPanel to homepage
        gc.gridx=2;
        gc.gridy=1;
        add(InventoryPanel, gc);

        //Events section/////////////////////////
        JPanel EventsPanel = new JPanel();
        EventsPanel.setLayout(new BoxLayout(EventsPanel, BoxLayout.Y_AXIS));
        EventsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton GoFarming = new JButton("Go Farming");
        JButton GoHunting = new JButton("Go Hunting");
        JButton GoForaging = new JButton("Go Foraging");
        JButton GoToBed = new JButton("Go to Bed");
        EventsPanel.add(GoFarming);
        EventsPanel.add(GoHunting);
        EventsPanel.add(GoForaging);
        EventsPanel.add(GoToBed);
        //add to EventsPanel homepage
        gc.gridx=1;
        gc.gridy=1;
        add(EventsPanel, gc);

        //Listeners section/////////////////////////
        GoFarming.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                fireDetailEvent(new DetailEvent(this, "GoFarming"));
                GoFarming.setEnabled(false);
                SeedsLabel.setText("Seeds: " + player.getSeeds());
                FoodLabel.setText("Food: " + player.getFood());
            }
        });
        GoHunting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                fireDetailEvent(new DetailEvent(this, "GoHunting"));
                GoHunting.setEnabled(false);
            }
        });
        GoForaging.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                fireDetailEvent(new DetailEvent(this, "GoForaging"));
                GoForaging.setEnabled(false);
            }
        });
        GoToBed.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                fireDetailEvent(new DetailEvent(this, "GoToBed"));
                GoFarming.setEnabled(true);
                GoHunting.setEnabled(true);
                GoForaging.setEnabled(true);
            }
        });
    }

    private void incrementDay() {
        dayCounter++;
        DisplayDay.setText("Day " + dayCounter);
    }
    public void displayNightEventText(String text) {
        EventsDisplay.setText(text);
        incrementDay();
    }
    public void displayEventText(String text) {
        EventsDisplay.append(text);
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
