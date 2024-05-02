import javax.swing.*;
import java.awt.*;

public class Midterm2_01 {
    /*In the Java programming language, write a program that opens a 400x200 window with two 200x200 buttons.
    One button says "Close" - which closes the program, and on the other the number "16"
    Each time you press this button, the number is halved until 1. When 1 is reached the button does not change anymore. */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(400, 200);
        JButton button1 = new JButton();
        JButton button2 = new JButton();

        button1.setText("" + 16);
        button1.setSize(200, 200);
        button1.addActionListener(e -> {
            int val = Integer.parseInt(button1.getText());
            int newVal;
            if (val == 1) {
                newVal = val;
            } else {
                newVal = val / 2;
            }
            button1.setText("" + newVal);
        });

        button2.setText("Close");
        button2.setSize(200, 200);
        button2.addActionListener(e -> {
            System.exit(0);
        });

        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 50));
        frame.add(button1);
        frame.add(button2);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
    }
}
