import javax.swing.*;
import java.awt.*;

public class Main
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        JPanel workPanel = new JPanel();
        DrawingPanel field = new DrawingPanel(8, 8, 100);


        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.add(workPanel);

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        field.setLocation(d.width/2 - frame.getSize().width/2, d.height/2 - frame.getSize().height/2);

        workPanel.setBackground(Color.LIGHT_GRAY);
        field.setBackground(Color.WHITE);
        workPanel.add(field);

        frame.pack();
        frame.setVisible(true);
    }
}
