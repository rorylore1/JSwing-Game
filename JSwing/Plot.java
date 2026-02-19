import java.awt.Component;
import java.awt.Image;
import javax.swing.*;

//Class to represent each plot in the field in FarmingPage.java
public class Plot extends JPanel {
    private final ImageIcon stump = new ImageIcon(new ImageIcon("Images/stump.jpg").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
    private final ImageIcon dirt = new ImageIcon(new ImageIcon("Images/dirt.jpg").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
    private final ImageIcon plowedDirt = new ImageIcon(new ImageIcon("Images/plowed.jpg").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
    private final ImageIcon seedInGround = new ImageIcon(new ImageIcon("Images/seeded.jpg").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
    private final ImageIcon seedInGroundWatered = new ImageIcon(new ImageIcon("Images/seededAndWatered.jpg").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
    private final ImageIcon plantDay1 = new ImageIcon(new ImageIcon("Images/plantDay1.jpg").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
    private final ImageIcon plantDay1Watered = new ImageIcon(new ImageIcon("Images/plantDay1Watered.jpg").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
    private final ImageIcon plantDay2 = new ImageIcon(new ImageIcon("Images/plantDay2.jpg").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
    private final ImageIcon plantDay2Watered = new ImageIcon(new ImageIcon("Images/plantDay2Watered.jpg").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
    private final ImageIcon plantDay3 = new ImageIcon(new ImageIcon("Images/plantDay3.jpg").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
    private final ImageIcon plantDay3Watered = new ImageIcon(new ImageIcon("Images/plantDay3Watered.jpg").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
    private final ImageIcon fullPlant = new ImageIcon(new ImageIcon("Images/fullPlant.jpg").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
    private final ImageIcon fullPlantWatered = new ImageIcon(new ImageIcon("Images/fullPlantWatered.jpg").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
    private final ImageIcon plantGoneBad = new ImageIcon(new ImageIcon("Images/plantGoneBad.jpg").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
    private ImageIcon plotImage;        //image
    private String state = "";          //deadPlant, dirt, plowed, seeded, readyToGrow, & growing. Changes based on the tool used
    private int dyingCounter = 0;       //0-3, incremented when the plant doesn't have water
    private int growingCounter = 0;     //0-5, incremented overnight if watered, starts when state = seeded
    private int harvestAmount = 0;      //0, 1, or 3, incremented overnight if watered

    public Plot() {
        plotImage = stump;
    }
    public String getState() {
        return state;
    }
    private void updateState(String newState) {
        state = newState;
    }
    private void incrementDyingCounter() {
        dyingCounter++;
    }
    private void resetDyingCounter() {
        dyingCounter = 0;
    }
    private int getGrowingCounter() {
        return growingCounter;
    }
    private void incrementGrowingCounter() {
        growingCounter++;
    }
    private void resetGrowingCounter() {
        growingCounter = 0;
    }
    public int getHarvestAmount() {
        return harvestAmount;
    }
    private void updateHarvestAmount(int newHarvestAmount) {
        harvestAmount = newHarvestAmount;
    }
    public void resetHarvestAmount() {
        harvestAmount = 0;
    }
    public ImageIcon getPlotImage() {
        return plotImage;
    }
    public void updatePlot(String toolUsed) {
        switch(toolUsed) {
            case "clearLand":
                resetGrowingCounter();
                resetDyingCounter();
                resetHarvestAmount();
                plotImage = dirt;
                updateState("dirt");
                break;
            case "plow":
                if(state == "dirt") {
                    plotImage = plowedDirt;
                    updateState("plowed");
                }
                break;
            case "seedBag":
                if(state == "plowed") {
                    plotImage = seedInGround;
                    updateState("seeded");
                }
                break;
            case "wateringCan":
                if(state == "seeded" || state == "growing") {
                    switch(getGrowingCounter()) {
                        case(0):
                            plotImage = seedInGroundWatered;
                            break;
                        case(1):
                            plotImage = plantDay1Watered;
                            break;
                        case(2):
                            plotImage = plantDay2Watered;
                            break;
                        case(3):
                            plotImage = plantDay3Watered;
                            break;
                        case(4):
                        case(5):
                        case(6):
                            plotImage = fullPlantWatered;
                            break;
                        default: break;
                    }
                    updateState("readyToGrow");
                    resetDyingCounter();
                }
                break;
            case "harvestBasket":
                if(harvestAmount != 0) {
                    resetGrowingCounter();
                    resetDyingCounter();
                    plotImage = plantGoneBad;
                    updateState("deadPlant");
                }
                break;
            default:
                break;
        }
    }

    public int GrowOvernight() {
        int plotChange=0;  //Indicates what happened to this plot: 1 = plant grew, 2 = plant wilted, 3 = plant died
        //if it's grown for 6 days or not had water for 2 days, it dies
        if(growingCounter == 6 || dyingCounter == 2) {
            plotImage = plantGoneBad;
            resetDyingCounter();
            resetGrowingCounter();
            resetHarvestAmount();
            updateState("deadPlant");
            plotChange=3;
        }
        //if it is growing but did not get water today
        if (state == "growing") {
            incrementDyingCounter();
            plotChange=2;
        }
        //if it was watered today and is readyToGrow
        if (state == "readyToGrow") {
            switch(growingCounter) {
                case(0):
                    plotImage = plantDay1;
                    break;
                case(1):
                    plotImage = plantDay2;
                    break;
                case(2):
                    plotImage = plantDay3;
                    updateHarvestAmount(1);
                    break;
                case(3):
                case(4):
                case(5):
                    plotImage = fullPlant;
                    updateHarvestAmount(3);
                    break;
                default: break;
            }
            incrementGrowingCounter();
            updateState("growing");
            plotChange=1;
        }
        return plotChange;
    }
}

//Custom renderer to display images in the JList
class ImageRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, 
            int index, boolean isSelected, boolean cellHasFocus) {
        
        //get the default JLabel setup (handles selection colors/borders)
        JLabel label = (JLabel) super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus);
        
        if (value instanceof Plot) {
            label.setIcon(((Plot) value).getPlotImage());
            label.setText(""); //remove text so only image is visible
        }
        return label;
    }
}