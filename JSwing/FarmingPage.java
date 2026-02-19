import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

//The FarmingPage class creates the farming page where the player can see their farm, inventory, and tools.
public class FarmingPage extends JPanel {

    private int seedsGained=0, foodGained=0; //variables to track the player's inventory changes in the farming page

    //Constructor for the FarmingPage class. It creates the Swing components and sets up the layout.
    public FarmingPage(Person player, String title) {

        //set page container layout
        setLayout(new BorderLayout());

        //Page Title////////////////////////
        JLabel TopTitle = new JLabel(player.getUsername() + "'s Farm");
        TopTitle.setHorizontalAlignment(JLabel.CENTER);
        add(TopTitle, BorderLayout.PAGE_START);

        //Inventory////////////////////////
        JPanel InventoryPanel = new JPanel();
        InventoryPanel.setLayout(new BoxLayout(InventoryPanel, BoxLayout.Y_AXIS));

        //swing components
        JLabel inventoryLabel = new JLabel("Inventory");
        inventoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel food = new JLabel("Food Gathered: 0");
        JLabel seeds = new JLabel("Seeds: " + player.getSeeds());
        //add to InventoryPanel
        InventoryPanel.add(inventoryLabel);
        InventoryPanel.add(food);
        InventoryPanel.add(seeds);
        //add InventoryPanel to farmpage
        add(InventoryPanel, BorderLayout.LINE_END);

        //Tools////////////////////////
        JPanel ToolsPanel = new JPanel();
        ToolsPanel.setLayout(new BoxLayout(ToolsPanel, BoxLayout.Y_AXIS));

        //swing components
        JRadioButton axeAndShovel = new JRadioButton("Clear Land");
        JRadioButton plow = new JRadioButton("Plow");
        JRadioButton seedBag = new JRadioButton("Seed Bag");
        JRadioButton wateringCan = new JRadioButton("Watering Can");
        JRadioButton harvestBasket = new JRadioButton("Harvest Basket");
        axeAndShovel.setActionCommand("clearLand");
        plow.setActionCommand("plow");
        seedBag.setActionCommand("seedBag");
        wateringCan.setActionCommand("wateringCan");
        harvestBasket.setActionCommand("harvestBasket");
        JButton useTool = new JButton("Use Tool");
        //add to group so only one can be selected at a time
        ButtonGroup tools = new ButtonGroup();
        tools.add(axeAndShovel);
        tools.add(plow);
        tools.add(seedBag);
        tools.add(wateringCan);
        tools.add(harvestBasket);
        //add to ToolsPanel
        ToolsPanel.add(new JLabel("Tools"));
        ToolsPanel.add(axeAndShovel);
        ToolsPanel.add(plow);
        ToolsPanel.add(seedBag);
        ToolsPanel.add(wateringCan);
        ToolsPanel.add(harvestBasket);
        ToolsPanel.add(useTool);
        //add ToolsPanel to farmpage
        add(ToolsPanel, BorderLayout.LINE_START);

        //The Field////////////////////////
        //create a list of plots based on the Plot class
        JList<Plot> plots = new JList<>(player.getPlots());
        plots.setCellRenderer(new ImageRenderer());
        plots.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        plots.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        plots.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        plots.setSelectionBackground(Color.RED);
        //put the plots in the field scrollpane
        JScrollPane field = new JScrollPane(plots);
        field.setPreferredSize(new Dimension(50,50));
        //add the field to the farmpage
        add(field, BorderLayout.CENTER);

        //The Bottom Section////////////////////////
        JPanel BottomPanel = new JPanel();
        BottomPanel.setLayout(new FlowLayout());
        //swing components
        JLabel Instructions = new JLabel("<html><center>Instructions<br>It's time to farm!<br>Each plot can be worked with a tool.<br>Select the tool and plots, then click Use Tool.<br></center></html>");
        Instructions.setBorder(BorderFactory.createLineBorder(Color.RED));
        //add to bottomPanel
        BottomPanel.add(Instructions);
        //add bottomPanel to farmpage
        add(BottomPanel, BorderLayout.PAGE_END);
        
        //Listeners section/////////////////////////
        useTool.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String selectedTool;
                try {
                    selectedTool = tools.getSelection().getActionCommand();
                } catch (NullPointerException e) {
                    JOptionPane.showMessageDialog(null, "Select a tool first.");
                    return;
                }
                //for each selected plot, update the plot based on the tool selected and the logic in the Plot class.
                switch(selectedTool) {
                    case "clearLand":
                        for (Object obj : plots.getSelectedValuesList()) {
                            ((Plot) obj).updatePlot("clearLand");
                        }
                        break;
                    case "plow":
                        for (Object obj : plots.getSelectedValuesList()) {
                            ((Plot) obj).updatePlot("plow");
                        }
                        break;
                    case "seedBag":
                        for (Object obj : plots.getSelectedValuesList()) {
                            if(player.getSeeds()+seedsGained > 0) {
                                ((Plot) obj).updatePlot("seedBag");
                                seedsGained--;
                            } else {
                                JOptionPane.showMessageDialog(null, "Not enough seeds!");
                                break;
                            }
                        }
                        //update inventories
                        player.setTempSeedAmount(seedsGained);
                        seeds.setText("Seeds: " + (player.getSeeds() + seedsGained));
                        break;
                    case "wateringCan":
                        for (Object obj : plots.getSelectedValuesList()) {
                            ((Plot) obj).updatePlot("wateringCan");
                        }
                        break;
                    case "harvestBasket":
                        for (Object obj : plots.getSelectedValuesList()) {
                            ((Plot) obj).updatePlot("harvestBasket");
                            if(((Plot) obj).getHarvestAmount() != 0) {
                                foodGained+= ((Plot) obj).getHarvestAmount();
                                seedsGained++;
                                ((Plot) obj).resetHarvestAmount();
                            }
                        }
                        //update inventories
                        food.setText("Food Gathered: " + foodGained);
                        seeds.setText("Seeds: " + (player.getSeeds() + seedsGained));
                        player.setTempFoodGainedAmount(foodGained);
                        player.setTempSeedAmount(seedsGained);
                        break;
                    default: break;
                }
                //update the plots display after using the tool
                plots.setCellRenderer(new ImageRenderer());
            }
        });
    }
}