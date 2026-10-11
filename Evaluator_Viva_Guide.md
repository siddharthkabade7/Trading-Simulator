# Trading Simulator: FA1 Evaluation and Viva Guide

| Course Name | Course Code | Class | Assessment | Maximum Marks |
|---|---|---|---|---:|
| Object Oriented Programming | BIT23PC05 | S.Y B.Tech | FA1 Mini Project Demonstration | 25 |

## Purpose

This guide applies the supplied FA1 rubric to the current Java trading simulator. The rubric has five criteria, each scored out of 5, for a total of 25 marks. The project score below is an evidence-based estimate from the source code; demonstration and presentation must be confirmed in person.

The evaluator should assess three things separately:

1. Whether the student understands the Java and object-oriented concepts used.
2. Whether the student can trace and demonstrate the implemented buy/sell workflow.
3. Whether the student can identify limitations and reason about how to improve correctness.

A student should receive credit for accurately identifying an incomplete feature or defect. They should not receive credit for claiming that an unimplemented feature works.

## What the Project Implements

- `Asset` is an abstract base class. `Stock` and `Crypto` extend it and implement `Tradable`.
- `Market` stores assets by symbol; `Portfolio` stores positions by asset; `User` stores orders by ID.
- `Order` captures the asset price when the order is created and begins in `PENDING` state.
- `TradingEngine` records an order in user history, processes pending orders, and can cancel pending orders.
- A successful buy deducts the order value and updates the portfolio. A successful sell removes portfolio quantity and credits the user.
- `Position` computes a weighted average purchase price when additional units are bought.
- The console menu allows a user to view balance, portfolio, market, and order history, and to initiate buy or sell orders.

## Current Scope and Limitations

An evaluator should verify these against the checked-out code before the assessment, since the source may change:

- The `Market/Admin` menu branch is empty. It does not currently provide admin actions or market management through the UI.
- There is no persistent storage, multi-user login, live market feed, or external exchange integration.
- `Market.getAsset()` returns `null` for an unknown symbol. The order creation path does not check this before accessing the asset price.
- The order is added to history before it is validated or executed. A rejected execution can therefore remain in history as `PENDING`.
- Prices and balances use `double`/`Double`, which can introduce rounding errors in financial calculations. `BigDecimal` with an explicit rounding policy would be a better production choice.
- `Asset.equals()` and `hashCode()` use the symbol. This assumes symbols are unique across all asset types.
- The order captures a price at creation; it does not refresh or compare the price at execution.
- The sell path tracks remaining quantity and credits proceeds, but does not record realized profit/loss per sale.
- The menu relies on `Scanner.nextInt()` and does not recover cleanly from non-numeric input.

These are appropriate discussion points for a basic academic simulator. They are not evidence that the student failed, provided the student can explain their impact and propose sound improvements.

## Supplied Rubric

Each criterion is scored independently. Excellent is 5; Good is 3-4; Satisfactory is 2; Needs Improvement is 1. Maximum total: 25 marks.

| Criterion | Excellent (5) | Good (3-4) | Satisfactory (2) | Needs Improvement (1) |
|---|---|---|---|---|
| Object-Oriented Design | Clear use of classes, inheritance, interfaces, encapsulation; logical structure | Mostly correct structure with minor design issues | Basic structure, limited use of OOP principles | Poor or no structure, missing key concepts |
| Java Code Implementation | Clean, readable code; consistent use of standards | Functional code with minor issues in readability or standards | Code has several issues; partially functional | Disorganized or faulty code; not functional |
| Application of OOP Concepts | Effectively uses inheritance, polymorphism, abstraction, and encapsulation | Uses most OOP concepts correctly | Limited or partial application of OOP concepts | Incorrect or minimal use of OOP concepts |
| Functionality & Output | All features work correctly and efficiently | Most features work, with minor issues | Some features work; others are incomplete or buggy | Major features missing or non-functional |
| Demonstration & Presentation | Clear explanation; confident and answers questions well | Basic explanation with minor gaps | Unclear or hesitant explanation | Unable to explain project or answer questions |

## Provisional Marks for This Project

These marks assess the submitted implementation against the supplied rubric. The score is not an official examiner decision; presentation cannot be verified from source code alone.

| Criterion | Mark | Rubric level | Project-specific reason |
|---|---:|---|---|
| Object-Oriented Design | **4/5** | Good | The project has a logical class structure, an abstract `Asset`, subclasses, an interface, and private fields. Deduct one mark for design weaknesses such as exposing mutable market/history maps and relying on symbol-only asset identity. |
| Java Code Implementation | **3/5** | Good | The core Java implementation is functional and the project report records a successful compile. Readability and consistency vary, and invalid symbols or non-numeric menu input are not handled robustly. |
| Application of OOP Concepts | **3/5** | Good | Inheritance, abstraction, interfaces, and encapsulation are present. Polymorphism is demonstrated mainly by overrides; the engine checks `instanceof Tradable` but does not invoke `canTrade()`, so the concepts are not all used as effectively as the 5-mark descriptor expects. |
| Functionality & Output | **2/5** | Satisfactory | Core user flows for viewing, buying, selling, and order history exist, but the Market/Admin menu is empty and there are important edge-case defects, including unknown-symbol handling and orders recorded before successful execution. |
| Demonstration & Presentation | **3/5 (provisional)** | Assumed Good | This assumes the student can give a basic explanation with minor gaps. It must be replaced after observing the actual demonstration and answers; source files cannot establish confidence or oral understanding. |
| **Provisional total** | **15/25** |  | **12/20 implementation marks assessed from code + assumed 3/5 presentation.** |

If the live presentation receives 1-5 marks, the corresponding total is **13-17/25**. For a defensible final mark, replace the provisional presentation score with the evaluator's observed score. The strongest opportunities to raise the implementation score are to complete the admin actions, validate symbols and input before creating orders, and make order acceptance/execution status consistent when a trade fails.

Do not grade solely on memorized definitions or the number of classes. Ask the student to connect each concept to a specific implementation and to explain the known limitations honestly.

## Suggested Demonstration (5-7 Minutes)

Ask the student to run `javac *.java` followed by `java Main`, then perform a short, controlled walkthrough:

1. Show the starting balance and market assets.
2. Buy a valid quantity of one listed asset. Explain the order's initial status and the confirmation step.
3. Show the changed balance, portfolio quantity, average price, and order history.
4. Buy more of the same asset at the same price, then explain how the weighted average is calculated.
5. Sell part of the position and verify the balance and remaining quantity.
6. Create a pending order and cancel it; explain which state transitions are allowed.
7. Attempt one invalid case, such as a quantity of zero or selling more units than held. Explain the observed behavior and any limitation.

The evaluator should avoid requiring a particular menu sequence if it exposes a known console-flow defect. Instead, ask the student to explain the expected state change and, if needed, trace the corresponding methods in code.

## Viva Questions and Expected Answer Points

The answer points are a marking guide, not a script. Follow a correct answer with a “where is that in your code?” or “what happens if this fails?” prompt.

### A. Project Understanding and OOP

1. **Explain the project architecture and the path of a buy order.**
   - A user selects an asset and quantity; `Order` captures the asset, type, quantity, price, ID, and initial status.
   - `TradingEngine.placeOrder()` adds it to the user's order history. `processOrder()` executes a pending order, updates balance and portfolio, changes its status, and records a `Transaction`.

2. **Why is `Asset` abstract? What do `Stock` and `Crypto` inherit?**
   - It represents shared symbol, name, and price behavior; concrete asset types provide their own details and trading implementation.

3. **What is the difference between extending `Asset` and implementing `Tradable` here?**
   - `extends` reuses the class's shared state/behavior; `implements` promises the `canTrade()` interface contract. Java classes have one direct superclass but can implement multiple interfaces.

4. **Where is runtime polymorphism demonstrated?**
   - A `Stock` or `Crypto` can be held as an `Asset`. Both override `canTrade()`. Note that the current engine checks `instanceof Tradable`; it does not call `canTrade()`.

5. **Give an example of encapsulation. Is it complete?**
   - Fields such as `User.balance`, `Order.status`, and `Position.quantity` are private and accessed through methods. However, methods like `getOrderHistory()` and `getMarket()` expose mutable maps, so callers can bypass class invariants; encapsulation could be strengthened.

6. **Why override both `equals()` and `hashCode()` in `Asset`?**
   - Hash-based collections require equal objects to have equal hash codes. The portfolio map uses `Asset` keys; symbol-based equality lets equivalent asset objects address the same key. It also assumes symbols are globally unique.

### B. Orders, State, and Trading Logic

7. **What is the order lifecycle? Can an executed order be cancelled?**
   - An order starts `PENDING`; processing changes it to `EXECUTED`, and cancellation changes a pending order to `CANCELLED`. `cancelOrder()` only changes pending orders, so executed/cancelled orders are not cancelled again.

8. **Why does `Order` store a price? What pricing assumption does that make?**
   - It snapshots `asset.getPrice()` when constructed. The engine uses this stored price even if the asset price later changes, so it behaves like a fixed-price order rather than a live-price market order.

9. **Trace the balance and portfolio changes for a buy.**
   - Cost is `price * quantity`; the engine withdraws it, creates a `Position`, and inserts or updates that position. If funds are insufficient, an exception is thrown before the portfolio update.

10. **How is the average price calculated after buying more units?**
    - New average = `(old average * old quantity + purchase price * added quantity) / total quantity`. It is a quantity-weighted average cost.

11. **What happens during a partial sell? Does it alter average price?**
    - The position quantity decreases and sale proceeds are deposited. The existing average purchase price is retained; realized gain/loss for the sold portion is not separately stored.

12. **What prevents an order from being processed twice?**
    - `processOrder()` executes only if status is `PENDING`; after success it marks it `EXECUTED`. Ask the student what happens if execution throws before the status update: it remains pending and can be retried.

13. **What is the purpose of `Transaction`? Is transaction history visible to the user?**
    - A transaction associates a user and order with a timestamp and is appended after successful processing. In the current code, the engine keeps the list, but there is no public display/query workflow, and the fields have no getters.

### C. Collections and Complexity

14. **Why use a `HashMap` in `Market`? What is its key?**
    - Symbol is the key, supporting direct lookup by symbol; expected lookup is average $O(1)$, subject to hashing behavior.

15. **Why use a map for portfolio positions instead of a list?**
    - It supports lookup/update by asset and avoids intentionally storing multiple separate positions for the same asset, relying on `Asset.equals()` and `hashCode()`.

16. **What is the benefit and drawback of `HashMap` for order history?**
    - Order ID lookup is convenient; iteration order is unspecified, so history may not be displayed chronologically. A `LinkedHashMap` or ordered collection could preserve insertion order.

### D. Validation, Exceptions, and Edge Cases

17. **What happens if a user enters an unknown symbol?**
    - `Market.getAsset()` returns `null`. `Order` then attempts to read the asset price, which can cause a `NullPointerException`; the input should be checked and reported as a controlled error first.

18. **What is the risk of `OrderType.valueOf(s)`?**
    - It requires an exact enum name such as `BUY` or `SELL`; unexpected spelling/case throws `IllegalArgumentException`. Validate or parse input with a controlled message.

19. **What happens if order processing fails after `placeOrder()`?**
    - The order has already been inserted in history. Since status changes only after successful execution, it may remain there as `PENDING`; the design should define whether failed orders remain, are rejected, or are removed.

20. **What checks prevent selling more assets than the user owns?**
    - `Portfolio.sellPosition()` verifies that the asset exists and `Position.removeQuantity()` rejects a quantity above holdings. It also rejects non-positive quantities.

21. **How does the code handle invalid menu input such as letters?**
    - `Scanner.nextInt()` can throw `InputMismatchException`; current menu handling does not robustly recover. A stronger input loop would read a line and parse/validate it.

22. **Why is `double` problematic for money? What would you use?**
    - Binary floating point cannot exactly represent many decimal fractions. Use `BigDecimal`, construct values from decimal strings, and define scale and rounding rules consistently.

### E. Design Judgment and Extensions

23. **The menu says “Market/Admin.” What can the admin currently do?**
    - Nothing; the corresponding switch branch is empty. The student should identify this as unfinished rather than claim market administration is implemented.

24. **How would you improve order placement so invalid orders do not pollute history?**
    - Validate the asset, quantity, type, tradeability, and relevant funds/holdings before accepting or recording it; distinguish `REJECTED` from `PENDING` if rejected orders must be retained.

25. **How would you make order execution safer if two operations occur at once?**
    - Make validation and state mutation atomic, prevent duplicate execution, and synchronize or use transactional storage as appropriate. In a real exchange, use durable order processing and concurrency controls.

26. **How would you test the weighted-average calculation?**
    - Example: buy 2 units at 10, then 3 at 16. Expected average is `(2*10 + 3*16)/5 = 13.6`; assert total quantity is 5 and average is 13.6.

27. **What are the first three improvements you would prioritize, and why?**
    - Strong answers prioritize correctness and user-visible risk: null/invalid input handling, consistent order validation/status behavior, and decimal-safe money. Further improvements may include tests, admin actions, persistence, and transaction reporting.

## Practical Test Cases for the Evaluator

Use these to verify the student's explanation and implementation. Record actual results; do not assume an expected behavior is implemented merely because it is desirable.

| Test | Expected rule | What it evaluates |
|---|---|---|
| Construct an order with quantity `0` or a negative value | `InvalidQuantityException` | Constructor validation |
| Buy an asset costing more than the current balance | No successful purchase; balance and portfolio should remain unchanged | Funds validation and failure atomicity |
| Sell an asset not held | Controlled asset-not-found failure | Portfolio validation |
| Sell more than the held quantity | `InsufficientAssetException` | Quantity invariant |
| Buy an unknown symbol | Should produce a clear controlled error; current flow may throw `NullPointerException` | Symbol validation gap |
| Buy the same asset twice | Quantities combine; average cost is weighted | Position update logic |
| Cancel a pending order | Status becomes `CANCELLED`; no balance/position change | Order state behavior |
| Process the same executed order again | No second execution while it is not pending | Duplicate processing guard |
| Change asset price after creating an order | Execution still uses captured order price | Price snapshot semantics |

Where a current implementation does not meet the expected rule, assess whether the student can locate the responsible code and explain a safe correction. Do not award implementation marks for behavior that only exists in the student's verbal description.

## Evaluator's Closing Questions

Conclude with these two questions to distinguish understanding from memorization:

1. **Which behavior in this project would you not trust with real money, and what exact code change would you make first?**
2. **What test would prove that your change fixed the problem without breaking successful trades?**

Strong responses name a specific failure mode, point to the responsible method, propose a targeted change, and describe a repeatable test with expected balance, order status, and portfolio results.