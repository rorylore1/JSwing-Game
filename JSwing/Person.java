import javax.swing.DefaultListModel;
import javax.swing.SwingWorker;

//The Person class represents a player in the game. 
// It stores the player's username, inventory (seeds and food), and the plots of land they have for farming.
public class Person{

    private final String username;
    private int seeds, food;                              //inventory variables
    private int tempSeedAmount = 0, tempFoodGainedAmount = 0; //inventory variables to track changes in the action pages
    private DefaultListModel<Plot> plots = new DefaultListModel<>();
    private String difficultyLevel;      //difficulty level selected
    private int dayCounter = 1;            //day counter, incremented when the player goes to bed
    
    public Person(String newUsername, String mode) {
        //set the inventory based on the game mode selected
        difficultyLevel = mode;
        switch (difficultyLevel) {
            case "freePlayMode":
                seeds = 100;
                food = 100;
                break;
            case "survivalMode":
                seeds = 0;
                food = 10;
                break;
        }
        //set username
        username = newUsername;
        //set the Farming field
        setupField();
    }
    public void resetPlayer() {
        //set the inventory based on the game mode selected
        switch (difficultyLevel) {
            case "freePlayMode":
                seeds = 100;
                food = 100;
                break;
            case "survivalMode":
                seeds = 0;
                food = 10;
                break;
        }
        //reset the Farming field
        setupField();
        //reset other variables
        tempSeedAmount = 0;
        tempFoodGainedAmount = 0;
        dayCounter = 0;
    }

    public int getDayCounter() {
        return dayCounter;
    }
    public void incrementDayCounter() {
        dayCounter++;
    }
    public String getUsername() {
        return username;
    }
    public int getSeeds() {
        return seeds;
    }
    //When setting the seeds, also reset the tempSeedAmount to 0 so that it can be used to track the changes in the next farming session.
    public void setSeeds(int newAmount) {
        seeds = newAmount;
        setTempSeedAmount(0);
    }
    public int getFood() {
        return food;
    }
    //When setting the food, also reset the tempFoodGainedAmount to 0 so that it can be used to track the changes in the next farming session.
    public void setFood(int newAmount) {
        food = newAmount;
        setTempFoodGainedAmount(0);
    }
    public int getTempSeedAmount() {
        return tempSeedAmount;
    }
    public void setTempSeedAmount(int newAmount) {
        tempSeedAmount = newAmount;
    }
    public int getTempFoodGainedAmount() {
        return tempFoodGainedAmount;
    }
    public void setTempFoodGainedAmount(int newAmount) {
        tempFoodGainedAmount = newAmount;
    }
    public DefaultListModel<Plot> getPlots() {
        return plots;
    }
    //Fill the Plot list (field) with the specified number of plots. Use a background thread so that the UI doesn't freeze
    public void setupField() {
        plots.clear();
        SwingWorker<?, ?> creatingFarm = new SwingWorker<>() {
            protected Boolean doInBackground() throws Exception {
                for (int i = 0; i < 21; i++) {
                    plots.addElement(new Plot());
                }
                return true;
            }
        };
        creatingFarm.execute();
    }
}