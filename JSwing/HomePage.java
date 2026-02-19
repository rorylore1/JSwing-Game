import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.EventListenerList;

//The HomePage class creates the home page where the player can see their inventory, the day counter, instructions, and the events of the day.
public class HomePage extends JPanel {

    private EventListenerList listenerList = new EventListenerList();   //list of listeners for the events in the home page
    private JLabel DisplayDay;          //display for the day counter
    private JTextArea EventsDisplay;    //display for the events of the day, updated when the player takes an action
    private JLabel FoodLabel, SeedsLabel, ToolsLabel;   //displays for the player's inventory, updated when the player takes an action
    private JButton goFarming, goForaging, goHunting, goToBed;  //action buttons
    private final ImageIcon house = new ImageIcon(new ImageIcon("Images/house.jpg").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
 
    //Constructor for the HomePage class. It creates the Swing components and sets up the layout.
    public HomePage(Person player, String title) {

        //set page container layout
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        //Top section/////////////////////////
        JPanel Top = new JPanel();
        Top.setLayout(new BoxLayout(Top, BoxLayout.Y_AXIS));

        //swing components
        JLabel TopTitle = new JLabel(player.getUsername() + "'s Home");
        TopTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        DisplayDay = new JLabel("Day " + player.getDayCounter());
        DisplayDay.setAlignmentX(Component.CENTER_ALIGNMENT);
        EventsDisplay = new JTextArea("The sun rises...\nA new day begins.\n");
        EventsDisplay.setEditable(false);
        EventsDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);

        //add to Top section
        Top.add(TopTitle);
        Top.add(DisplayDay);
        Top.add(EventsDisplay);

        //add Top section to homepage frame
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.anchor=GridBagConstraints.PAGE_START;
        gc.gridwidth=GridBagConstraints.REMAINDER;
        gc.gridheight=6;
        gc.weighty=1;
        gc.gridy=0;
        gc.gridx=0;
        add(Top, gc);

        //Actions section/////////////////////////
        JPanel ActionsPanel = new JPanel();
        ActionsPanel.setLayout(new BoxLayout(ActionsPanel, BoxLayout.Y_AXIS));

        //swing components
        goFarming = new JButton("Go Farming");
        goForaging = new JButton("Go Foraging");
        goHunting = new JButton("Go Hunting");
        goToBed = new JButton("Go to Bed");

        //add buttons to ActionsPanel
        ActionsPanel.add(goFarming);
        ActionsPanel.add(goForaging);
        ActionsPanel.add(goHunting);
        ActionsPanel.add(goToBed);

        //Set GridBagConstraints for the second row in the Frame
        gc.anchor=GridBagConstraints.CENTER;
        gc.gridwidth=1;
        gc.gridheight=1;
        gc.gridy=5;
        //add ActionsPanel to homepage
        gc.insets = new Insets(0,10,0,0);
        gc.gridx=0;
        add(ActionsPanel, gc);

        //House Image section/////////////////////////
        gc.insets = new Insets(0,0,0,0);
        gc.gridx=1;
        add(new JLabel(house), gc);

        //Inventory section/////////////////////////
        JPanel InventoryPanel = new JPanel();
        InventoryPanel.setLayout(new BoxLayout(InventoryPanel, BoxLayout.Y_AXIS));

        //swing components
        JLabel inventoryLabel = new JLabel("Inventory");
        inventoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        InventoryPanel.add(inventoryLabel);
        FoodLabel = new JLabel("Food: " + player.getFood());
        SeedsLabel = new JLabel("Seeds: " + player.getSeeds());
        ToolsLabel = new JLabel("Tools: 5");
        //add to InventoryPanel
        InventoryPanel.add(FoodLabel);
        InventoryPanel.add(SeedsLabel);
        InventoryPanel.add(ToolsLabel);

        //add InventoryPanel to homepage
        gc.insets = new Insets(0,0,0,10);
        gc.gridx=2;
        add(InventoryPanel, gc);

        //Instructions section/////////////////////////
        JPanel BottomPanel = new JPanel();
        BottomPanel.setLayout(new BoxLayout(BottomPanel, BoxLayout.Y_AXIS));

        //swing components
        JLabel instructions = new JLabel("<html><center>Instructions<br>It's time to survive!<br>go farming, foraging, and hunting to gather food and seeds.<br>go to bed to end the day and age your plants.<br>Beware, each action can be done once a day and costs one food!<br>Above all, DON'T RUN OUT OF FOOD!!</center></html>");
        instructions.setBorder(BorderFactory.createLineBorder(Color.RED));
        instructions.setAlignmentX(CENTER_ALIGNMENT);
        //add to BottomPanel
        BottomPanel.add(instructions);

        //set GridBagConstraints for the third row in the Frame
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.anchor=GridBagConstraints.PAGE_END;
        gc.weighty = 1;
        //add BottomPanel to homepage
        gc.insets = new Insets(0,0,10,0);
        gc.gridx=1;
        add(BottomPanel, gc);

        //Listeners section/////////////////////////
        goFarming.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                fireDetailEvent(new DetailEvent(this, "goFarming", ""));
                //update the home page displays
                goFarming.setEnabled(false);
            }
        });
        goForaging.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                //add a random (0-2) food amount
                int foodRan = (int) (Math.random()*3);
                player.setFood(player.getFood() + foodRan);
                String text = "Consumed 1 food while foraging. ";
                if (foodRan != 0) {
                    text += foodRan;
                } else {
                    text += "No";
                }
                text += " food gained and ";
                //add a random (0-2) seed amount
                int seedRan = (int) (Math.random()*3);
                player.setSeeds(player.getSeeds() + seedRan);
                if (seedRan == 1) {
                    text += "1 seed gained.\n";
                } else if(seedRan > 1) {
                    text += seedRan + " seeds gained.\n";
                } else {
                    text += "no seeds gained.\n";
                }
                //update the home page displays
                goForaging.setEnabled(false);
                displayEventText(text);
                fireDetailEvent(new DetailEvent(this, "goForaging", ""));
            }
        });
        goHunting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                //add a random (0-5) food amount
                int foodRan = (int) (Math.random()*5);
                player.setFood(player.getFood() + foodRan);
                goHunting.setEnabled(false);
                String text = "Consumed 1 food while hunting. ";
                if (foodRan != 0) {
                    text += foodRan;
                } else {
                    text += "No";
                }
                text += " food gained.\n";
                //update the home page displays
                displayEventText(text);
                fireDetailEvent(new DetailEvent(this, "goHunting", ""));
            }
        });
        goToBed.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                fireDetailEvent(new DetailEvent(this, "goToBed", ""));
            }
        });
    }
    //Reset home page componenets for a new day.
    public void displayNightEventText(String text, Person player) {
        //displays the events that occurred overnight
        EventsDisplay.setText(text);
        //increment the day counter
        player.incrementDayCounter();
        DisplayDay.setText("Day " + player.getDayCounter());
        //refresh all the actions
        goFarming.setEnabled(true);
        goHunting.setEnabled(true);
        goForaging.setEnabled(true);
    }
    //Displays the events that occur when the player takes an action.
    public void displayEventText(String text) {
        EventsDisplay.append(text);
    }
    //Updates the inventory display when the player takes an action.
    public void updateInventory(Person player) {
        FoodLabel.setText("Food: " + player.getFood());
        SeedsLabel.setText("Seeds: " + player.getSeeds());
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
