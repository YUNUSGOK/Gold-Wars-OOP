/**
 * Base class for BuyOrderFactory and SellOrderFactory.
 * Factory Class for the Order Class.
 * Generates orders to the countries.
 * @author Yunus Emre Gök 2166460
 */
public abstract class OrderFactory {
    /**
     * Generates orders to given country
     * @param country order's origin country
     * @return order that is produced
     */
    public abstract Order produceOrder(Country country);

}