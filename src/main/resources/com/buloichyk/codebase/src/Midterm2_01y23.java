import javax.swing.*;
import java.awt.*;

/*Write a program in Java programming language that opens a 300x200 window with two buttons,
the first at the top of the window, the other at the bottom.
On the top button it says "9999" and on the other one "3".
When pressing the first button, the number on the second button is reduced by 1,
when the second button is pressed, the number on the second button is reduced by the value in the first button.*/


public class Midterm2_01y23 {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(300, 200);

        JButton b1 = new JButton();
        final int[] B1 = {9999};
        b1.setText(""+B1[0]);

        JButton b2 = new JButton();
        final int[] B2 = {3};
        b2.setText(""+B2[0]);

        b1.addActionListener(e -> {
            B2[0]--;
            b2.setText(""+B2[0]);
        });

        b2.addActionListener(e -> {
            B2[0] = B2[0]- B1[0];
            b2.setText(""+B2[0]);
        });

        //frame.setLayout(new BorderLayout());
        frame.add(b1, BorderLayout.NORTH);
        frame.add(b2, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
