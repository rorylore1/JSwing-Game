import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

//Download code at: https://github.com/rorylore1/JSwing-Game

//This is the main class that initializes the login page.
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame window = new LoginFrame("JSwing Farming Game");
            window.setVisible(true);
            window.setSize(300,300);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);
        });

    }
}

//The LoginFrame class creates the login page and listens for the login event.
//When the login event is triggered, it creates a new Person object and opens the HomeFrame.
class LoginFrame extends JFrame {
    private LoginPage page;
    public Person player;

    public LoginFrame(String title) {
        super(title);
        Container loginpage = getContentPane();

        page = new LoginPage();
        page.addDetailListener((DetailEvent event) -> {
            String username = event.getKey();
            if("".equals(username)) {
                JOptionPane.showMessageDialog(null, "Please enter a username.");
                return;
            }
            player = new Person(username, event.getMode());
            HomeFrame window = new HomeFrame(player, "Home Page");
            window.setVisible(true);
            window.setSize(600,600);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);
            dispose(); //close the login frame after opening the home frame
        });
        loginpage.add(page);
    }
}

//The HomeFrame class creates the home page and listens for the events from the HomePage.
//When the events are triggered, it updates the player's inventory and 
//opens the FarmingFrame if the "GoFarming" event is triggered.
class HomeFrame extends JFrame {
    private HomePage page;

    public HomeFrame(Person player, String title) {
        super(title);
        Container homepage = getContentPane();
        
        page = new HomePage(player, title);
        page.addDetailListener((DetailEvent event) -> {
            //each action costs one food
            player.setFood(player.getFood()-1);
            //If user ran out of food, reset the game without restarting the application
            if(player.getFood() == 0) {
                page.updateInventory(player);
                int choice = JOptionPane.showConfirmDialog(null, "You have run out of food and starved to death.\n Game Over.\n Would you like to try again?", "Game Over", JOptionPane.YES_NO_OPTION);
                if(choice == JOptionPane.YES_OPTION) {
                    player.resetPlayer();
                    page.updateInventory(player);
                    page.displayNightEventText("Dawn begins, a new day...\n", player);
                    return;
                } else {
                    System.exit(0);
                }
            }
            switch (event.getKey()) {
                case "goFarming":
                    setEnabled(false);  //disable the home frame while the farming frame is open
                    FarmingFrame window = new FarmingFrame(player, "Farming Page");
                    window.setVisible(true);
                    window.setSize(600,600);
                    window.setResizable(false);
                    window.addWindowListener(new WindowAdapter() {
                        @Override
                        //when the farming frame is closed,
                        public void windowClosing(WindowEvent e) {
                            //display the changes made
                            String text = "Consumed 1 food while tending the farm.";
                            if(player.getTempFoodGainedAmount() > 0) {
                                text += " Harvested " + player.getTempFoodGainedAmount() + " food!";
                            }
                            if(player.getTempSeedAmount() == 1) {
                                text += " Harvested 1 seed!";
                            } else if(player.getTempSeedAmount() > 0) {
                                text += " Net harvest of " + player.getTempSeedAmount() + " seeds!";
                            } else if (player.getTempSeedAmount() == -1) {
                                text += "Planted 1 seed.";
                            } else if (player.getTempSeedAmount() < 0) {
                                text += " Planted " + Math.abs(player.getTempSeedAmount()) + " seeds.";
                            }
                            text+="\n";
                            page.displayEventText(text);
                            //update the player's inventory based on the changes made in the farming page
                            player.setFood(player.getTempFoodGainedAmount()+player.getFood());
                            player.setSeeds(player.getTempSeedAmount()+player.getSeeds());
                            page.updateInventory(player);
                            setEnabled(true);  //re-enable the home frame when the farming frame is closed
                        }
                    });
                    break;
                case "goToBed":
                    String text = "Night Falls...\nCritters roam in the night.\n";
                    int plantsGrown = 0, plantsWilted = 0, plantsDied = 0;
                    //logic for the FarmingPage
                    JList<Plot> Field = new JList<>(player.getPlots());
                    ListModel<Plot> model = Field.getModel();
                    for (int a=0; a < model.getSize(); a++) {
                        Object element = model.getElementAt(a);
                        int plotChange = ((Plot) element).GrowOvernight();
                        switch (plotChange) {
                            case 1: plantsGrown++; break;
                            case 2: plantsWilted++; break;
                            case 3: plantsDied++; break;
                            default: break;
                        }
                    }
                    if(plantsGrown == 1) {
                        text += "1 plant grew! ";
                    } else if(plantsGrown > 1) {
                        text += plantsGrown + " plants grew! ";
                    }
                    if(plantsWilted == 1) {
                        text += "1 plant wilted. ";
                    } else if(plantsWilted > 1) {
                        text += plantsWilted + " plants wilted. ";
                    }
                    if(plantsDied == 1) {
                        text += "1 plant died.";
                    } else if(plantsDied > 1) {
                        text += plantsDied + " plants died.";
                    }
                    if(plantsGrown == 0 && plantsWilted == 0 && plantsDied == 0) {
                        text += "No changes to the farm.";
                    }
                    text += "\nDawn begins, a new day...\n";
                    page.displayNightEventText(text, player);
                    break;
                default: break;
            }
            page.updateInventory(player);
        });
        homepage.add(page);
    }
}

//Creates the farming page.
class FarmingFrame extends JFrame {
    private FarmingPage page;

    public FarmingFrame(Person player, String title) {
        super(title);
        Container farmingpage = getContentPane();
        
        page = new FarmingPage(player, title);
        farmingpage.add(page);
    }
}

/*
Listeners
Action listeners are for J objects in the *Page.java. When the J object is triggered, the action listener is executed. 
The action listener creates a new DetailEvent object when it calls FireDetailEvent(new DetailEvent(params)). 
FireDetailEvent() calls DetailListener.detailEventOccurred(DetailEvent event), which then executes DetailEvent.java.
The Detail listener in Main.java is added to the ListenerList in each file via the addDetailListener() method.
After the action listener is executed, then the DetailEvent is executed from Main.java.

*/