import java.awt.*;

/**
 * Child class of the Order Class.
 * Order has a cash value to buy gold to its origin country.
 * @author Yunus Emre Gök 2166460
 */
public class BuyOrder extends Order {
    /**
     * Constructor for BuyOrder that will call the parent constructor
     * @param country is the origin country of the order.
     */
    public BuyOrder( Country country) {
        super(country);
    }
    /**
     * What will be displayed and how it be displayed is determined in here.
     * Order will displayed with 20 unit diameter filled green circle at the current position
     * Above the circle, origin country's initials will be displayed in green.
     * Inside the circle, order Amount will be displayed in black.
     * @param g2d Graphics2D component the draw.
     */
    @Override
    public void draw(Graphics2D g2d) {

        g2d.setFont(font);
        g2d.setPaint(Color.GREEN);
        g2d.fillOval(position.getIntX(),position.getIntY(),20,20);
        g2d.drawString(originCountry.getInitials(), position.getIntX(), position.getIntY()-5);
        g2d.setColor(Color.BLACK);
        g2d.drawString(String.valueOf(orderAmount), position.getIntX()+5, position.getIntY()+15);

    }
    /**
     * When its delivered to the destination line, this method will be called.
     * Origin country will be updated and buy gold depending on the order.
     */
    @Override
    public void deliverOrder() {
        originCountry.processDeliveredOrder(this);
    }

    /**
     * @return Buy Order has positive gold change
     */
    @Override
    public int goldChange() {
        return orderAmount;
    }
}