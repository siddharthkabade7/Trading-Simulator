# Trading Simulator – Concept Review and Coverage Report

## 1. Project Overview
This project is a basic Java-based trading simulator that models assets, users, portfolios, orders, and a simple market. The main aim is to simulate buying and selling stocks/crypto assets, tracking balances, order history, and portfolio positions.

## 2. Core Concepts Checked

### 2.1 OOP Concepts
The project demonstrates the following object-oriented principles:

- Encapsulation:
  - Fields such as `balance`, `portfolio`, `orderHistory`, `quantity`, and `price` are kept private in classes like `User`, `Order`, and `Position`.
  - Access is provided via getter and setter-like methods.

- Inheritance:
  - `Stock` and `Crypto` inherit from `Asset`.
  - This is a good use of inheritance for shared asset attributes such as symbol, name, and price.

- Abstraction:
  - `Asset` is abstract and acts as a base class for concrete asset types.
  - `Tradable` is also an interface used to define a contract for market-tradable assets.

- Polymorphism:
  - Different asset types can be treated through the `Asset` base type.
  - `canTrade()` is overridden in both `Stock` and `Crypto`, showing polymorphic behavior.

### 2.2 Data Structures
The code uses Java collections effectively for core simulation logic:

- `HashMap<String, Asset>` in `Market` stores market assets by symbol.
- `HashMap<Asset, Position>` in `Portfolio` stores holdings for each asset.
- `HashMap<Integer, Order>` in `User` keeps order history by order ID.

This is conceptually correct for a trading app because symbol-based lookups and position tracking are common patterns.

### 2.3 Business Logic Coverage
The project covers these trading concepts:

- Asset pricing and validation
- User balance maintenance
- Buy / sell flow
- Order lifecycle: `PENDING -> EXECUTED` and `CANCELLED`
- Portfolio update and position tracking
- User order history
- Asset lookup in the market

## 3. Logic Review by File

### Main.java
The program is structured around a menu-driven workflow.

Strengths:
- Multi-level menu: user menu and order confirmation flow.
- Asset display and portfolio display are present.
- Buy/sell actions are initiated through user interaction.

Observations:
- The menu logic is functional, but the file is still incomplete for the admin/market side; the `case 2` and `case 3` blocks are empty.
- The program uses the same `choice` variable for both menu navigation and order confirmation, which is workable but can be confusing and harder to maintain.

### Market.java
This class correctly centralizes assets available for trading.

Strengths:
- `addAsset()` prevents duplicate symbols.
- `getAsset(symbol)` provides symbol-based lookup.

Observation:
- If a symbol is invalid or not present, `getAsset()` returns `null`, and the code does not handle this gracefully in all call sites.

### User.java
This class handles the financial state of the user.

Strengths:
- `withdrawAmount()` and `depositAmount()` are implemented logically.
- `nextOrderId` increments correctly to assign unique order IDs.

Observation:
- `balance` is stored as a wrapper type `Double`; this is acceptable for basic simulation, but `double` would be more natural for financial math.

### Portfolio.java
This class is the core of position management.

Strengths:
- `updatePosition()` adds new or existing holdings correctly.
- `sellPosition()` removes quantities and deletes empty positions.
- `netValue()` and `netProfitLoss()` provide meaningful portfolio valuation metrics.

Observation:
- The logic is conceptually correct, but it depends heavily on `Asset.equals()` and `hashCode()` based on symbol.
- This is acceptable as long as symbol uniqueness is maintained reliably.

### Position.java
This class models a single asset holding.

Strengths:
- Maintains both the asset quantity and average price.
- Weighted average pricing logic is a good concept for realistic position accounting.

Observation:
- Average price calculation is mathematically logical for buying the same asset multiple times.

### Order.java
Order creation is generally correct.

Strengths:
- Quantity validation is present.
- Price is captured from the asset price at order time.
- Status is set to `PENDING` initially.

Observation:
- `OrderType.valueOf(s)` will throw if the string is not exactly `BUY` or `SELL`.
- This can be improved with validation and better error handling.

### TradingEngine.java
This is the most important logic class in the project.

Strengths:
- It handles the trade lifecycle and updates user balance and portfolio.
- It adds transactions to a transaction history.
- Validation for tradeability and funds is attempted.

Observation:
- The implementation is logically close to a real trading engine, but there are some weak areas:
  - `placeOrder()` simply adds the order to the user history without checking whether the order is valid before history insertion.
  - `processOrder()` executes the order only if the status is `PENDING`, then sets it to `EXECUTED`.
  - `cancelOrder()` only cancels if it is still pending.

### Asset.java
This base class is well designed for shared asset attributes.

Strengths:
- Symbol, name, and price are centralised.
- Validation prevents invalid prices.
- `equals()` and `hashCode()` are overridden for consistent key-based comparisons.

Observation:
- The equality logic uses symbol only, which is suitable for this small stock/crypto simulator.

## 4. Major Findings / Issues

### Issue 1: Incomplete Market/Admin functionality
The admin side of the application is not implemented. The `case 2:` block in `Main.java` is empty, which means the project is not complete from a feature perspective.

### Issue 2: Missing robust validation for invalid asset symbols
User input can request a symbol that is not present in the market. In that case, `market.getAsset(symbol)` may return `null`, leading to runtime issues instead of controlled error handling.

### Issue 3: Limited exception handling around null/invalid inputs
Several functions rely on thrown exceptions, but the code does not consistently validate user input before creating orders. This makes the app more fragile in real use.

### Issue 4: Some logic is functional but not polished
The code demonstrates core concepts correctly, but it does not yet feel like a complete production-grade trading simulator. It is a solid academic/basic project, but not a fully polished submission.

## 5. Output and Behavior Check
I verified the project by compiling and running it:

- Compile check: `javac *.java` succeeded with no compile-time errors.
- Runtime check: `java Main` started successfully and displayed the trading simulator menu.

This confirms that the application starts correctly and reaches the main interaction flow.

## 6. Final Verdict
### Submission Status: Partially Ready
The project is conceptually correct and compiles successfully. It demonstrates basic Java programming concepts, trading logic, inheritance, portfolio handling, order states, and a working entry point.

However, it is not fully complete as a polished final submission because:
- the admin/market section is unfinished,
- some invalid-input handling is weak,
- and the app still lacks deeper validation and full feature completeness.

### Overall rating:
- Concept understanding: Good
- Code correctness: Good for a basic version
- Project completeness: Moderate
- Submission readiness: Acceptable for a basic academic project, but not fully complete for a final polished submission

## 7. Recommendation
If this project is being submitted as a basic assignment, it is acceptable and shows proper implementation of core trading concepts. If it is meant to be a stronger final project, the following should be added next:

- complete admin/market actions,
- add robust symbol validation and error messages,
- add more transaction details,
- improve input validation and flow control,
- refine menu logic and user experience.

---
Prepared for review as a concept coverage and logic validation report.
