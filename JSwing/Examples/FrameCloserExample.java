import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameCloserExample {

    public static void main(String[] args) {
        // Create the main frame (Frame 1)
        JFrame frame1 = new JFrame("Frame 1");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setSize(300, 200);

        JButton openButton = new JButton("Open Frame 2");
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // When button is clicked, create and show Frame 2
                Frame2 frame2 = new Frame2(frame1); // Pass Frame 1 reference to Frame 2
                frame2.setVisible(true);
            }
        });

        frame1.add(openButton);
        frame1.setVisible(true);
    }
}

// Separate class for the second JFrame
class Frame2 extends JFrame {
    private JFrame frameToClose;

    public Frame2(JFrame frameToClose) {
        super("Frame 2");
        this.frameToClose = frameToClose; // Store the reference to Frame 1
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose Frame 2 when closed

        JButton closeButton = new JButton("Close Frame 1");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Use the stored reference to close Frame 1
                frameToClose.dispose();
                // Optionally close Frame 2 as well
                dispose(); 
            }
        });

        add(closeButton);
    }
}
