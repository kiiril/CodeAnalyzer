import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GUIbuttons {
    public static void main(String[] args) {
        Random r = new Random();
        JFrame frame = new JFrame();
        frame.setBounds(400, 400, 300, 300);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 30,  40));
        int n = 10;
        for (int i = 1; i <=10 ; i++) {
            if (i < n/2) {
                JButton b = new JButton();
                char letter = (char) (r.nextInt(25)+'A'); // random capital letter
                b.setText(String.valueOf(letter));
                b.addActionListener(e -> {
                    b.setForeground(Color.WHITE);
                    b.setBackground(Color.RED);
                });
                frame.add(b);
            }else if(i==7) {
                JButton b = new JButton();
                b.setText("I am unique");
                frame.add(b);
            }
            else{
                JButton b = new JButton();
                int number = r.nextInt(10);
                b.setText(String.valueOf(number));
                b.addActionListener(e -> {
                    b.setBackground(Color.BLUE);
                    b.setForeground(Color.WHITE);
                    b.setSize(new Dimension(50,50));
                });
                frame.add(b);
            }
        }
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
        frame.pack();
    }
}
