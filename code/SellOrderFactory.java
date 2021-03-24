/**
 * Child class for OrderFactory .
 * Factory Class for the SellOrder Class.
 * Generates sell orders to the countries.
 * @author Yunus Emre Gök 2166460
 */
public class SellOrderFactory extends OrderFactory {
    /**
     * Generates sell orders to given country
     * @param country order's origin country
     * @return order that is produced
     */
    @Override
    public Order produceOrder(Country country) {
        Order tmp = new SellOrder(country);
        return tmp;
    }

}