package stocktradingplatform;
import java.util.*;

public class StockTradingPlatform {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Market market = new Market();
        Portfolio portfolio = new Portfolio();

        System.out.println("Welcome to the Stock Trading Platform!");

        while (true) {
            System.out.println("\n1.. View Market Data");
            System.out.println("2.. Buy Stock");
            System.out.println("3.. Sell Stock");
            System.out.println("4.. View Portfolio");
            System.out.println("5.. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    market.displayMarketData();
                    break;
                case 2:
                    System.out.print("Enter stock symbol to buy: ");
                    String buySymbol = scanner.next();
                    System.out.print("Enter quantity to buy: ");
                    int buyQuantity = scanner.nextInt();
                    market.buyStock(buySymbol, buyQuantity, portfolio);
                    break;
                case 3:
                    System.out.print("Enter stock symbol to sell: ");
                    String sellSymbol = scanner.next();
                    System.out.print("Enter quantity to sell: ");
                    int sellQuantity = scanner.nextInt();
                    market.sellStock(sellSymbol, sellQuantity, portfolio);
                    break;
                case 4:
                    portfolio.displayPortfolio();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}

class Stock {
    String symbol;
    double price;

    Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }
}

class Market {
    private final Map<String, Stock> stocks;

    Market() {
        stocks = new HashMap<>();
        stocks.put("AAPL", new Stock("AAPL", 230.00));
        stocks.put("GOOGL", new Stock("GOOGL", 280.00));
        stocks.put("TSLA", new Stock("TSLA", 800.00));
        stocks.put("AMZN", new Stock("AMZN", 100.00));
    }

    void displayMarketData() {
        System.out.println("\nMarket Data:");
        for (Stock stock : stocks.values()) {
            System.out.println(stock.symbol + ": $" + stock.price);
        }
    }

    void buyStock(String symbol, int quantity, Portfolio portfolio) {
        Stock stock = stocks.get(symbol.toUpperCase());
        if (stock != null) {
            portfolio.addStock(stock, quantity);
            System.out.println("Bought " + quantity + " shares of " + stock.symbol);
        } else {
            System.out.println("Stock symbol not found.");
        }
    }

    void sellStock(String symbol, int quantity, Portfolio portfolio) {
        Stock stock = stocks.get(symbol.toUpperCase());
        if (stock != null) {
            portfolio.removeStock(stock, quantity);
            System.out.println("Sold " + quantity + " shares of " + stock.symbol);
        } else {
            System.out.println("Stock symbol not found.");
        }
    }
}

class Portfolio {
    private final Map<String, Integer> portfolio;

    Portfolio() {
        portfolio = new HashMap<>();
    }

    void addStock(Stock stock, int quantity) {
        portfolio.put(stock.symbol, portfolio.getOrDefault(stock.symbol, 0) + quantity);
    }

    void removeStock(Stock stock, int quantity) {
        int currentQuantity = portfolio.getOrDefault(stock.symbol, 0);
        if (currentQuantity >= quantity) {
            portfolio.put(stock.symbol, currentQuantity - quantity);
            if (portfolio.get(stock.symbol) == 0) {
                portfolio.remove(stock.symbol);
            }
        } else {
            System.out.println("Not enough shares to sell.");
        }
    }

    void displayPortfolio() {
        System.out.println("\nYour Portfolio:");
        if (portfolio.isEmpty()) {
            System.out.println("Portfolio is empty.");
        } else {
            for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue() + " shares");
            }
        }
    }
}
