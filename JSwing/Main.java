import java.awt.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame window = new LoginFrame("Login Page");
            window.setVisible(true);
            window.setSize(300,300);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);
        });

    }
}

class LoginFrame extends JFrame {
    private LoginPage page;
    public Person player;

    public LoginFrame(String title) {
        super(title);
        Container loginpage = getContentPane();

        page = new LoginPage();
        page.addDetailListener((DetailEvent event) -> {
            String username = event.getDisplayText();
            player = new Person(username);
            HomeFrame window = new HomeFrame(player, "Home Page");
            window.setVisible(true);
            window.setSize(600,600);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);
            dispose(); // Close the login frame after opening the home frame
        });
        loginpage.add(page);
        // Testing code removed: HomeFrame will be opened after login
    }
}

class HomeFrame extends JFrame {
    private HomePage page;

    public HomeFrame(Person player, String title) {
        super(title);
        Container homepage = getContentPane();
        
        page = new HomePage(player, title);
        page.addDetailListener((DetailEvent event) -> {
            switch (event.getKey()) {
                case "GoToBed":
                    page.displayNightEventText(event.getDisplayText());
                    break;
                case "GoFarming":
                case "GoHunting":
                case "GoForaging":
                    FarmingFrame window = new FarmingFrame(player, "Farming Page");
                    window.setVisible(true);
                    window.setSize(600,400);
                    window.setResizable(false);
                    page.displayEventText(event.getDisplayText());
                    break;
                default:
                    break;
            }
        });
        homepage.add(page);
    }
}

class FarmingFrame extends JFrame {
    private FarmingPage page;

    public FarmingFrame(Person player, String title) {
        super(title);
        Container farmingpage = getContentPane();
        
        page = new FarmingPage(player, title);
        page.addDetailListener((DetailEvent event) -> {
        });
        farmingpage.add(page);
    }
}

/*
//add a debugger to actually map the order
            Listeners
Action listeners are for J objects in the *Page.java. When the J object is triggered, the action listener is executed. 
The action listener creates a new DetailEvent object when it calls FireDetailEvent(new DetailEvent(params)). 
FireDetailEvent() calls DetailListener.detailEventOccurred(DetailEvent event), which then executes DetailEvent.java.
The Detail listener in Main.java is added to the ListenerList in each file via the addDetailListener() method.
After the action listener is executed, then the DetailEvent is executed from Main.java.

*/


/*To Do:
Implement new frames for the buttons (Go Farming, Go Hunting, etc) with different layouts.
Implement the inventory system into the Person class and call it for all the buttons.
Make actionPerformed() a method in each file.
Enhance nightly events with a random event generator that adds or subtracts from the inventory.
Upload to GitHub and add a README with instructions on how to run the program.
Create web link.
Add comments to all code files. Add a debugger to actually map the order for the Listeners.

code to do:
borderlayout
cardlayout
flowlayout
gridlayout
grouplayout
springlayout

Add a page for the map.
*/