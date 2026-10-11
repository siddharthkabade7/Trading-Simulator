import java.util.*;

class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        User user1 = new User(100000.5, "Aditya", 101);
        Market market = new Market();
        Stock stock1 = new Stock("@", "apple", 2345, 2.5);
        Crypto crypto1 = new Crypto("#", "SOLANA", 114920, "SOL MAINET");
        market.addAsset(crypto1);
        market.addAsset(stock1);

        TradingEngine engine = new TradingEngine();
        OrderStatus orderStatus;

        System.out.println("=".repeat(40));
        System.out.println("           TRADING SIMULATOR");
        System.out.println("=".repeat(40));
        int choice, option;
        boolean success = true;
        boolean choiceDone = false;

        do {
            System.out.println(" 1. USER");
            System.out.println(" 2. Market/Admin");
            System.out.println(" 3. Exit");
            System.out.print("Enter The Choice :");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("=".repeat(40));
                    System.out.println("               USER MENU");
                    System.out.println("=".repeat(40));
                    System.out.println(" 1. View Balance");
                    System.out.println(" 2. View portfolio");
                    System.out.println(" 3. View Market");
                    System.out.println(" 4. Buy Asset");
                    System.out.println(" 5. Sell Asset");
                    System.out.println(" 6. View Order History");
                    System.out.println(" 7. Back");

                    do {
                        System.out.print("Enter The Option :");
                        choice = sc.nextInt();

                        switch (choice) {
                            case 1:
                                System.out.println("User Balance :" + user1.getBalance());
                                break;

                            case 2:
                                System.out.println("User Portfolio :");
                                for (Position position : user1.getPortfolio().getpositions()) {
                                    System.out.println("=".repeat(40));
                                    System.out.println("Asset :" + position.getAsset().getName());
                                    System.out.println("Quantity :" + position.getQuantity());
                                    System.out.println("Average Price :" + position.getAveragePrice());
                                    System.out.println("=".repeat(40));
                                }
                                break;
                            case 3:
                                System.out.println("Market :");
                                for (Map.Entry<String, Asset> entry : market.getMarket().entrySet()) {
                                    Asset asset = entry.getValue();
                                    System.out.println("=".repeat(40));
                                    System.out.println("Asset :" + asset.getName());
                                    System.out.println("Symbol :" + entry.getKey());
                                    System.out.println("Price :" + asset.getPrice());
                                    System.out.println("=".repeat(40));
                                }
                                break;
                            case 4:
                                System.out.println("Market");
                                ;
                                int i = 1;
                                for (Map.Entry<String, Asset> entry : market.getMarket().entrySet()) {
                                    System.out.println(i + "." + entry.getValue().getSymbol() + "->"
                                            + entry.getValue().getName() + "->" + entry.getValue().getPrice());
                                    i++;
                                }
                                success = true;

                                do {
                                    String symbol;
                                    System.out.print("Enter The Symbol Of Company : ");
                                    symbol = sc.next();

                                    int quantity;
                                    System.out.print("How much Asset Do You Want To Purchase : ");
                                    quantity = sc.nextInt();

                                    

                                    try {
                                        Order order = new Order(market.getAsset(symbol), "BUY", quantity,
                                                user1.getNextOrderId());
                                        engine.placeOrder(order, user1);

                                        choiceDone = false;

                                        do {
                                            System.out.println("=".repeat(40));
                                            System.out.println("1. BUY");
                                            System.out.println("2. CANCEL");
                                            System.out.println("=".repeat(40));
                                            System.out.print("Enter the choice: ");

                                            choice = sc.nextInt();

                                            switch (choice) {

                                                case 1:
                                                    

                                                    orderStatus = engine.processOrder(user1, order);

                                                    if (orderStatus == OrderStatus.EXECUTED) {
                                                        System.out.println("Order Executed");
                                                        success = false;
                                                        choiceDone = true;
                                                    }
                                                    break;

                                                case 2:
                                                    orderStatus = engine.cancelOrder(user1, order);

                                                    if (orderStatus == OrderStatus.CANCELLED) {
                                                        System.out.println("Order Cancelled");
                                                    }

                                                    success = false;
                                                    choiceDone = true;
                                                    break;

                                                default:
                                                    System.out.println("Invalid Choice");
                                            }

                                        } while (!choiceDone);

                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());

                                    }

                                } while (success);
                                break;
                            case 5:
                                System.out.println("Portfolio");
                                if (user1.getPortfolio().getpositions().isEmpty()) {
                                    System.out.println("Portfolio is empty.");
                                } else {
                                    int j = 1;
                                    for (Position position : user1.getPortfolio().getpositions()) {
                                        Asset asset = position.getAsset();
                                        System.out.println(j + "." + asset.getSymbol() + "->"
                                                + asset.getName() + "->" + asset.getPrice());
                                        j++;
                                    }
                                    success = true;

                                    do {
                                        String symbol;
                                        System.out.print("Enter The Symbol Of Company : ");
                                        symbol = sc.next();

                                        int quantity;
                                        System.out.print("How much Asset Do You Want To Sell : ");
                                        quantity = sc.nextInt();

                                        try {
                                            Order order = new Order(market.getAsset(symbol), "SELL", quantity,
                                                    user1.getNextOrderId());
                                            engine.placeOrder(order, user1);

                                            choiceDone = false;

                                            do {
                                                System.out.println("=".repeat(40));
                                                System.out.println("1. SELL");
                                                System.out.println("2. CANCEL");
                                                System.out.println("=".repeat(40));
                                                System.out.print("Enter the choice: ");

                                                choice = sc.nextInt();

                                                switch (choice) {

                                                    case 1:
                                                        

                                                        orderStatus = engine.processOrder(user1, order);

                                                        if (orderStatus == OrderStatus.EXECUTED) {
                                                            System.out.println("Order Executed");
                                                            success = false;
                                                            choiceDone = true;
                                                        }
                                                        break;

                                                    case 2:
                                                        orderStatus = engine.cancelOrder(user1, order);

                                                        if (orderStatus == OrderStatus.CANCELLED) {
                                                            System.out.println("Order Cancelled");
                                                        }

                                                        success = false;
                                                        choiceDone = true;
                                                        break;

                                                    default:
                                                        System.out.println("Invalid Choice");
                                                        break;
                                                }

                                            } while (!choiceDone);

                                        } catch (Exception e) {
                                            System.out.println(e.getMessage());
                                        }

                                    } while (success);
                                    
                                }
                            break;
                            case 6:
                                for(Map.Entry<Integer,Order> entry:user1.getOrderHistory().entrySet()){
                                    Order order= entry.getValue();
                                    System.out.println("=".repeat(40));
                                    System.out.println(entry.getKey()+"->"+order.getOrderType()+"->"+order.getAsset().getName()+"->"+order.getQuantity()+"->"+order.getOrderStatus());
                                    System.out.println("=".repeat(40));
                                }
                            break;
                            case 7:
                                System.out.println("Exiting The System...");
                            break;
                            default:
                                System.out.println("Invalid Option");
                            break;            

                        }


                    } while (choice != 7);

                    break;

                case 2:
                    break;

                case 3:
                    break;
            }

        } while (choice != 3);
    }
}