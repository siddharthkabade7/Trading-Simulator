# Plan to Average 4/5 in Every FA1 Rubric Section

**Course:** Object Oriented Programming (BIT23PC05)  
**Class:** S.Y B.Tech  
**Assessment:** FA1 Mini Project Demonstration  
**Target:** At least 4/5 in each of 5 sections, or about **20/25 overall**

This is a practical improvement plan for the current Trading Simulator. A 4/5 cannot be guaranteed by changes alone: the evaluator will also judge the live demonstration, explanation, and answers. Prioritize the high-impact items below, then verify every claimed behavior.

## Target Scorecard

| Rubric section | Current estimate | Target | Main evidence needed for 4/5 |
|---|---:|---:|---|
| Object-Oriented Design | 4/5 | 4/5 or higher | Keep responsibilities clear and stop exposing mutable internal maps/collections. |
| Java Code Implementation | 3/5 | 4/5 | Improve readability, input handling, validation, and show repeatable tests. |
| Application of OOP Concepts | 3/5 | 4/5 | Demonstrate useful polymorphism and use the interface through its contract. |
| Functionality & Output | 2/5 | 4/5 | Complete the advertised admin menu and make invalid/failed orders controlled and consistent. |
| Demonstration & Presentation | Not yet observable | 4/5 | Give a clear, rehearsed walkthrough and answer questions using actual code and results. |

The current code-based estimate is 12/20 across the first four sections. To reach a 20/25 total, aim for at least 16/20 from the implementation sections and 4/5 in the live presentation.

## 1. Object-Oriented Design: Target 4/5

The project already has a reasonable base: `Asset`, `Stock`, `Crypto`, `Tradable`, `Market`, `Portfolio`, `Order`, and `TradingEngine` have recognizable roles. Preserve that structure and strengthen its boundaries.

Suggested changes:

- In `User`, avoid exposing the mutable order-history map directly. Add methods such as `addOrder(Order order)` and `getOrders()`; return a read-only view or a copy for inspection.
- In `Market`, avoid returning its internal mutable map from `getMarket()`. Provide operations such as `getAssets()` and `getAsset(symbol)` instead.
- In `Portfolio`, use a consistently named accessor such as `getPositions()` and expose a read-only view for menu display.
- Keep trade rules in `TradingEngine` and portfolio invariants in `Portfolio`/`Position`; keep `Main` focused on input and output.
- Validate constructor inputs such as null assets and blank symbols so invalid objects cannot enter the model.

**Demonstration evidence:** explain one responsibility per class and show that menu code cannot directly alter balances, order history, or market storage.

## 2. Java Code Implementation: Target 4/5

The code contains working Java features, but inconsistent formatting and fragile console parsing make the implementation look less finished than its core model.

Suggested changes:

- Format all Java files consistently. Use descriptive variable names and remove unused variables/imports and stray empty statements.
- Replace repeated buy/sell confirmation loops in `Main` with small methods, for example `handleBuy(...)` and `handleSell(...)`.
- Handle non-numeric input. Read a full line and parse it with a validation loop, or catch `InputMismatchException` and consume the invalid token before prompting again.
- Check for a missing asset before constructing an order. Display a clear message and let the user retry rather than allowing a `NullPointerException`.
- Use `BigDecimal` for currency if time permits; construct from decimal strings and choose a consistent scale/rounding rule. If this is too broad for the assignment, explicitly document the `double` limitation and avoid claiming production-level monetary accuracy.
- Add focused automated tests if the course setup permits. At minimum test order quantity validation, insufficient balance, insufficient holdings, cancellation, and weighted-average cost.

**Demonstration evidence:** compile from a clean state, run the app, and show invalid text/unknown symbol handled without crashing or corrupting state.

## 3. Application of OOP Concepts: Target 4/5

Inheritance and interfaces are present, but the interface currently has a `void canTrade()` method that prints text. `TradingEngine` checks `instanceof Tradable` and does not call that method, so the interface does not yet control the trading behavior.

Suggested changes:

- Give `Tradable` a meaningful contract that the engine actually uses. For example, use a boolean capability/check method or an operation with a clear result; avoid using an interface method only to print a message.
- Call the contract through an `Asset`/`Tradable` reference in the relevant workflow rather than branching on concrete `Stock` and `Crypto` classes.
- Show polymorphism in market output: iterate over assets stored as `Asset` and ask the asset for a type-specific description or behavior. Avoid adding `if (asset instanceof Stock)` branches just to demonstrate inheritance.
- Keep fields private and provide narrowly scoped methods. Avoid public setters or mutable collection access unless there is a clear invariant-preserving reason.
- Be ready to explain the four OOP concepts with exact examples: `Asset` abstraction, `Stock`/`Crypto` inheritance, method overriding/interface dispatch, and private state with controlled access.

**Demonstration evidence:** explain what would happen if another asset type were added and show that the engine could use it without adding a type-specific branch.

## 4. Functionality & Output: Target 4/5

This section needs the most feature work. The top-level menu currently advertises `Market/Admin`, but its branch is empty. The order flow also needs safer validation and clear outcomes.

Suggested changes, in priority order:

1. **Complete the advertised admin menu in `Main`:** list assets, add an asset, and update an asset price. Validate duplicate/blank symbols and non-positive prices. Keep admin operations simple and in-memory; do not imply persistent storage.
2. **Validate before recording an order in `TradingEngine`:** confirm the asset exists, the order fields are valid, it is tradable, and the user has enough balance/holdings. Do not leave a failed order looking `PENDING` as if it could still execute.
3. **Handle unknown symbols in the user menu:** check `market.getAsset(symbol)` and print a retryable error before constructing `Order`.
4. **Make state transitions visible and consistent:** show order ID, type, price, quantity, and final status; cancelled orders must not change balance or portfolio; failed orders must not partially update either.
5. **Handle empty portfolio/history states:** print a clear message instead of an empty section.
6. **Test buy and sell outcomes:** after each action, verify balance, quantity, average price, and order status. Test insufficient funds and selling more than owned.

Avoid adding advanced features such as networking or a database before the basic order invariants work. Reliable existing workflows matter more for this rubric than a long feature list.

**Demonstration evidence:** complete one admin operation, one buy, one partial sell, one cancellation, and one invalid operation. Show that each result is reflected correctly in balance, portfolio, and history.

## 5. Demonstration & Presentation: Target 4/5

This mark depends on the actual presenter, not just the source code. Practice a concise walkthrough and answer in your own words; do not memorize the answer bullets as definitions.

Suggested 4-6 minute sequence:

1. State the problem and scope: an in-memory Java console simulator for a single user, with stocks/crypto, orders, and portfolio tracking.
2. Point out the OOP structure: `Asset` -> `Stock`/`Crypto`, the `Tradable` contract, and the roles of `Market`, `Portfolio`, and `TradingEngine`.
3. Run the program and show the initial market and balance.
4. Buy an affordable asset; explain `PENDING` -> `EXECUTED` and trace the balance and position update.
5. Buy more of the same asset and explain the weighted average with numbers.
6. Sell part of the holding; show proceeds and remaining quantity.
7. Show cancellation and one invalid case, explaining the expected result and how the implementation handles it.
8. Close with one known limitation and one specific improvement. Be honest about anything still incomplete.

Presentation habits that help reach 4/5:

- Keep the project ready to compile and run; use a small, predictable test scenario.
- Answer directly, then point to the class/method that implements the behavior.
- If the evaluator identifies a defect, explain its cause and a safe fix instead of claiming the program cannot fail.
- Do not claim persistence, real-time pricing, multiple users, or completed admin features unless you have implemented and verified them.

## Minimum Test Checklist Before the Demonstration

| Test | Expected result |
|---|---|
| Start the application | Menu appears; valid menu navigation works. |
| View market and balance | Correct seeded assets and initial balance are displayed. |
| Buy a valid affordable quantity | Balance decreases by price × quantity; portfolio and history update. |
| Buy more of the same asset | Quantity combines and average price is quantity-weighted. |
| Sell part of a holding | Balance increases by sale proceeds; remaining quantity is correct. |
| Sell more than held | Clear error; balance and portfolio remain unchanged. |
| Enter an unknown symbol | Clear retryable message; application does not crash. |
| Enter zero/negative quantity | Clear rejection; no order is executed. |
| Cancel a pending order | Status becomes `CANCELLED`; no balance or position changes. |
| Use admin add/update actions | Market list reflects valid changes; invalid/duplicate data is rejected. |

## Final Priority Order

If time is limited, work in this order:

1. Prevent invalid or failed orders from causing crashes or misleading statuses.
2. Implement the advertised admin actions and test them.
3. Make the OOP interface meaningful and used by the engine.
4. Improve encapsulation and console input handling; format the code consistently.
5. Rehearse the walkthrough and prepare to explain the test results and limitations.

A realistic target is **4/5 in each section**, not a guaranteed score. The evaluator assigns marks based on the finished project and the live demonstration.