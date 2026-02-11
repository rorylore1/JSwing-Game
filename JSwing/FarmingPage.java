import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;

import javax.swing.*;
import javax.swing.event.EventListenerList;

public class FarmingPage extends JPanel {

    private EventListenerList listenerList = new EventListenerList();
    private ButtonGroup tools = new ButtonGroup();
    private Person player;

    public FarmingPage(Person player, String title) {
        this.player = player;

        //set page container
        setLayout(new BorderLayout());
        JLabel TopTitle = new JLabel(player.GetUsername() + "'s Farm");
        TopTitle.setHorizontalAlignment(JLabel.CENTER);
        add(TopTitle, BorderLayout.PAGE_START);

        //Inventory////////////////////////
        JPanel Inventory = new JPanel();
        Inventory.setLayout(new BoxLayout(Inventory, BoxLayout.Y_AXIS));
        JLabel seeds = new JLabel("      Seeds: " + player.getSeeds());
        JLabel food = new JLabel("      Food Gathered: "+ player.getFood());
        Inventory.add(new JLabel("  Inventory:"));
        Inventory.add(seeds);
        Inventory.add(food);
        add(Inventory, BorderLayout.LINE_END);

        //Tools////////////////////////
        JPanel Tools = new JPanel();
        Tools.setLayout(new BoxLayout(Tools, BoxLayout.Y_AXIS));
        JRadioButton axeAndShovel = new JRadioButton("Axe & Shovel");
        JRadioButton plow = new JRadioButton("Plow");
        JRadioButton seedBag = new JRadioButton("Seed Bag");
        JRadioButton wateringCan = new JRadioButton("Watering Can");
        JRadioButton harvestBasket = new JRadioButton("Harvest Basket");
        axeAndShovel.setActionCommand("axeAndShovel");
        plow.setActionCommand("plow");
        seedBag.setActionCommand("seedBag");
        wateringCan.setActionCommand("wateringCan");
        harvestBasket.setActionCommand("harvestBasket");
        //add to group so only one can be selected at a time
        tools.add(axeAndShovel);
        tools.add(plow);
        tools.add(seedBag);
        tools.add(wateringCan);
        tools.add(harvestBasket);
        //add to panel
        Tools.add(new JLabel("Tools:"));
        Tools.add(axeAndShovel);
        Tools.add(plow);
        Tools.add(seedBag);
        Tools.add(wateringCan);
        Tools.add(harvestBasket);
        //add to farmpage
        add(Tools, BorderLayout.LINE_START);

/*
        //Listeners section/////////////////////////
        axeAndShovel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                fireDetailEvent(new DetailEvent(this, "axeAndShovel"));
                axeAndShovel.setEnabled(false);
            }
        });
        plow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                fireDetailEvent(new DetailEvent(this, "plow"));
                plow.setEnabled(false);
            }
        });
        seedBag.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                fireDetailEvent(new DetailEvent(this, "seedBag"));
                seedBag.setEnabled(false);
            }
        });
        wateringCan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                fireDetailEvent(new DetailEvent(this, "wateringCan"));
                wateringCan.setEnabled(false);
            }
        });
        scythe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                fireDetailEvent(new DetailEvent(this, "scythe"));
                scythe.setEnabled(false);
            }
        });
*/
        //The Field////////////////////////
        //create a list of plots based on the Plot class
        DefaultListModel<Plot> plots = new DefaultListModel<>();
        for(int i = 0; i < 4; i++) {
            plots.addElement(new Plot());
        }
        JList<Plot> Field = new JList<>(plots);
        Field.setCellRenderer(new ImageRenderer());
        Field.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        Field.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        Field.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(Field, BorderLayout.CENTER);

        //The Field Action Buttons////////////////////////
        JPanel FieldActions = new JPanel();
        JButton useTool = new JButton("Use Tool");
        FieldActions.add(useTool);
        add(FieldActions, BorderLayout.PAGE_END);

        useTool.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String selectedTool = tools.getSelection().getActionCommand();
                switch(selectedTool) {
                    case "axeAndShovel":
                        for (Object obj : Field.getSelectedValuesList()) {
                            ((Plot) obj).updatePlot("axeAndShovel");
                        }
                        break;
                    case "plow":
                        for (Object obj : Field.getSelectedValuesList()) {
                            ((Plot) obj).updatePlot("plow");
                        }                        
                        break;
                    case "seedBag":
                        int inventoryAmount = player.getSeeds();
                        for (Object obj : Field.getSelectedValuesList()) {
                            if(inventoryAmount == 0) { break;}
                            ((Plot) obj).updatePlot("seedBag");
                            inventoryAmount--;
                        }
                        player.updateSeeds(inventoryAmount);
                        seeds.setText("      Seeds: " + player.getSeeds());
                        break;
                    case "wateringCan":
                        for (Object obj : Field.getSelectedValuesList()) {
                            ((Plot) obj).updatePlot("wateringCan");
                        }                        
                        break;
                    case "harvestBasket":
                        int foodGathered = player.getFood();
                        for (Object obj : Field.getSelectedValuesList()) {
                            ((Plot) obj).updatePlot("harvestBasket");
                            if(((Plot) obj).getState() == "harvestable") {
                                foodGathered++;
                            }
                        }
                        player.updateFood(foodGathered);
                        food.setText("      Food Gathered: " + player.getFood());
                        break;
                    default:
                        System.out.println("No tool selected");
                        break;
                }
                System.out.println("Used " + tools.getSelection().getActionCommand());
                Field.setCellRenderer(new ImageRenderer());
            }
        });
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

//Custom renderer to display images in the JList
class ImageRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, 
            int index, boolean isSelected, boolean cellHasFocus) {
        
        // Get the default JLabel setup (handles selection colors/borders)
        JLabel label = (JLabel) super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus);
        
        if (value instanceof Plot) {
            label.setIcon(((Plot) value).getPlotImage());
            label.setText(""); // Remove text so only image is visible
        }
        return label;
    }
}

// Class to represent each plot in the field
class Plot extends JPanel {
    private ImageIcon stump = new ImageIcon(new ImageIcon("Images/stump.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    private ImageIcon dirt = new ImageIcon(new ImageIcon("Images/dirt.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    private ImageIcon ploweddirt = new ImageIcon(new ImageIcon("Images/ploweddirt.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    private ImageIcon seedinground = new ImageIcon(new ImageIcon("Images/seedinground.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    private ImageIcon plantday1 = new ImageIcon(new ImageIcon("Images/plantday1.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    private ImageIcon plantday2 = new ImageIcon(new ImageIcon("Images/plantday2.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    private ImageIcon plantday3 = new ImageIcon(new ImageIcon("Images/plantday3.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    private ImageIcon plantday4 = new ImageIcon(new ImageIcon("Images/plantday4.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    private ImageIcon plantgonebad = new ImageIcon(new ImageIcon("Images/plantgonebad.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    private ImageIcon emptyplant = new ImageIcon(new ImageIcon("Images/emptyplant.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

    private ImageIcon plotImage;
    private String state;

    public Plot() {
        this.plotImage = stump;
    }
    public String getState() {
        return state;
    }
    private void updateState(String newState) {
        this.state = newState;
    }
    public ImageIcon getPlotImage() {
        return plotImage;
    }
    public void updatePlot(String toolUsed) {
        switch(toolUsed) {
            case "axeAndShovel":
                this.plotImage = dirt;
                updateState("dirt");
                break;
            case "plow":
                if(this.state == "dirt") {
                    this.plotImage = ploweddirt;
                    updateState("plowed");
                }
                break;
            case "seedBag":
                if(this.state == "plowed") {
                    this.plotImage = seedinground;
                    updateState("seeded");
                }
                break;
            case "wateringCan":
                // Logic to determine plant growth stage and update image accordingly
                break;
            case "harvestBasket":
                if(this.state == "harvestable") {
                    this.plotImage = emptyplant;
                    updateState("deadplant");
                }
                break;
            default:
                break;
        }
    }

    public void GrowOvernight() {
        // Logic to determine plant growth stage and update image accordingly
    }
}
/*
//move Plot class to its own file? or within Person? goal is to be able to save the state of the farm.
//move this code to Person:
        DefaultListModel<Plot> plots = new DefaultListModel<>();
        for(int i = 0; i < 4; i++) {
            plots.addElement(new Plot());
        }


//add logic to handle when the user doesn't select a tool.
*/