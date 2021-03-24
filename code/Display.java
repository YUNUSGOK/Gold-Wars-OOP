import javax.swing.*;
import java.awt.*;

public class Display extends JPanel {
    public Display() { this.setBackground(new Color(180, 180, 180)); }

    @Override
    public Dimension getPreferredSize() { return super.getPreferredSize(); }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Common.getGoldPrice().draw((Graphics2D) g);
        g.drawLine(0, Common.getUpperLineY(), Common.getWindowWidth(), Common.getUpperLineY());

        for(int i=0; i<5;i++)
        {
            //if(i==0 || i==4) continue;
            Common.countries[i].draw((Graphics2D) g);
            Common.countries[i].drawOrders((Graphics2D) g);
            Common.countries[i].getAgent().draw((Graphics2D) g);
        }

    }
}