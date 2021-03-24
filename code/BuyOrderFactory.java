/**
 * Child class for OrderFactory .
 * Factory Class for the BuyOrder Class.
 * Generates buy orders to the countries.
 * @author Yunus Emre Gök 2166460
 */
public class BuyOrderFactory extends OrderFactory {

    /**
     * Generates buy orders to given country
     * @param country order's origin country
     * @return order that is produced
     */
    @Override
    public Order produceOrder(Country country) {
        Order tmp = new BuyOrder(country);
        return tmp;
    }

}