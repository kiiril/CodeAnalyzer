import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Midterm2_01_y23 {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(300, 200);
        Random random = new Random();
        int a;
        a= random.nextInt(0,100);
        JLabel l1 = new JLabel();
        l1.setText(""+a);
        int b=-1;
        JLabel l2 = new JLabel();
        l2.setText(""+b);

        JButton button = new JButton();
        char charButton = 'x';
        button.setText(""+charButton);

        button.addActionListener(e->{
            int c= random.nextInt(0,100);
            l2.setText(""+c);
            if(a==c){
                button.setEnabled(false);
            }
        });
        frame.add(l1, BorderLayout.NORTH);
        frame.add(l2, BorderLayout.SOUTH);
        frame.add(button, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
