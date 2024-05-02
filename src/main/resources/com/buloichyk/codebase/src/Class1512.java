import javax.swing.*;
import java.awt.*;

// GUI - graphical user interface
public class Class1512 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My first frame for today");
        //frame.setTitle("First frame for today");
        frame.setLocation(200,200);
        frame.setSize(500,500);
        //frame.setBounds(200,200,500,500); // location + frame size
        JButton button = new JButton("First button"); // button
        Button button1 = new Button("Not JButton");
        frame.add(button, BorderLayout.NORTH);
        frame.add(button1,BorderLayout.SOUTH);
        JPanel panel = create_patel(5);
        frame.add(panel);
        //components:
        JTextField tf = new JTextField("Write some text");
        JLabel label = new JLabel("Some text in label");
        JCheckBox checkBox = new JCheckBox("CHECK BOX");
        JRadioButton rb = new JRadioButton("RADIO BUTTON");
        JComboBox comboBox = new JComboBox(new String [] {"1st", "something", "THis man is boring"});
        //add all components
        panel.add(tf);
        panel.add(label);
        panel.add(checkBox);
        panel.add(rb);
        panel.add(comboBox);
        //frame.add(panel);
        //add panel to the frame
        frame.add(panel,BorderLayout.WEST);
        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("Size of the frame");
        JMenuItem size = new JMenuItem("SIZE");
        menu.add(size);
        menubar.add(menu);
        frame.setJMenuBar(menubar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // = frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
        frame.pack();
    }
    public static JPanel create_patel(int n){
        JPanel panel = new JPanel();
        GridLayout gl = new GridLayout(n,n);
        panel.setLayout(gl);
        //matrix for the button n*n
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <=n; j++) {
                JButton b = new JButton(i+","+j);
                b.setForeground(Color.BLACK); // use for text color
                b.setBackground(new Color(169-10*j, 103, 194)); // color for the components (169-10*j - for gradient)
                panel.add(b);
            }
        }
        return panel;
    }
}
