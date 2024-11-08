---

### Overview
This program simulates a simple banking system where multiple clients (like people or programs) can interact with multiple bank accounts. Each client can deposit or withdraw money from the bank accounts they are assigned to. The program uses multithreading to allow multiple clients to perform these actions at the same time.

### Class Breakdown

#### 1. **BankAccount Class**
This class represents a single bank account. Each `BankAccount` has:
- A balance (initial amount of money in the account).
- A unique account number, like `Account-1`, `Account-2`, etc.

**Key Methods:**
- **`deposit(int amount)`**: Adds money to the account balance.
- **`withdraw(int amount)`**: Takes money out of the account balance if there’s enough money available. If not, it leaves the balance unchanged and prints a message.

Both `deposit` and `withdraw` methods are **synchronized**. This means only one client can perform a `deposit` or `withdraw` action on an account at a time, avoiding conflicts or "race conditions."

**Code:**
```java
public synchronized void deposit(int amount) {
    balance += amount;
    System.out.println("Deposited " + amount + " to " + accountNumber + ". Current Balance: " + balance);
}

public synchronized boolean withdraw(int amount) {
    if (balance >= amount) {
        balance -= amount;
        System.out.println("Withdrew " + amount + " from " + accountNumber + ". Current Balance: " + balance);
        return true;
    } else {
        System.out.println("Insufficient balance for withdrawal in " + accountNumber);
        return false;
    }
}
```

#### 2. **Client Class**
The `Client` class represents a person (or process) that interacts with the bank. Each client:
- Is assigned a list of `BankAccount` objects (bank accounts) they can work with.
- Has a unique name (like "Client-1", "Client-2", etc.).

**How Clients Work:**
When a client "runs" (meaning it starts performing actions):
- It deposits 100 into each assigned account.
- Then it withdraws 50, followed by 30 from each account.

These actions are run in a **separate thread** for each client, allowing multiple clients to perform actions at the same time.

**Code:**
```java
@Override
public void run() {
    for (BankAccount account : accounts) {
        account.deposit(100); // Deposit to each account
        try { Thread.sleep(50); } catch (InterruptedException e) { e.printStackTrace(); }

        account.withdraw(50); // Withdraw from each account
        try { Thread.sleep(50); } catch (InterruptedException e) { e.printStackTrace(); }

        account.withdraw(30); // Another withdrawal from each account
        try { Thread.sleep(50); } catch (InterruptedException e) { e.printStackTrace(); }
    }
}
```

#### 3. **BankSimulation Class**
The `BankSimulation` class sets up the entire program, including:
- Creating multiple `BankAccount` objects and storing them in a list.
- Creating multiple `Client` objects, each with access to one or more `BankAccount` objects.
- Running each client as a separate thread using an **ExecutorService**, which manages a pool of threads.

**How It Works:**
1. We create a list of `BankAccount` objects (`accounts`) with initial balances.
2. We then create a list of `Client` objects, giving each client one or more `BankAccount` objects to manage.
3. Using `ExecutorService`, we run each `Client` in its own thread, allowing all clients to operate on their accounts at the same time.

### Detailed Explanation of Code Execution

1. **Creating Bank Accounts**:
   - The program first creates several `BankAccount` objects.
   - Each account starts with a unique ID and a balance of 500.
   
2. **Creating Clients**:
   - Each client is given access to one or more of these `BankAccount` objects.
   - If there are fewer accounts than clients, the accounts are shared (e.g., the 4th client might work on the same account as the 1st client).

3. **Executing Clients in Threads**:
   - When the program starts, each client’s `run` method is executed in a new thread.
   - Each client deposits 100 into its assigned accounts, waits a short time, withdraws 50, waits again, and then withdraws 30.
   
4. **Synchronization**:
   - The `synchronized` keyword ensures that only one thread can modify an account's balance at a time, so deposits and withdrawals happen one at a time per account.
   - This prevents "race conditions," where multiple threads try to modify the balance at the same time, which would lead to incorrect results.

### Sample Output Explanation

The output might look like this:
```
Deposited 100 to Account-1 . Current Balance: 100.0
Deposited 100 to Account-2 . Current Balance: 100.0
Withdrew 50 from Account-1 . Current Balance: 50.0
Withdrew 50 from Account-2 . Current Balance: 50.0
Withdrew 30 from Account-1 . Current Balance: 20.0
Withdrew 30 from Account-2 . Current Balance: 20.0
```

Here's what happens in this output:
1. The program deposits 100 to each account assigned to a client, raising the balance.
2. Then, each client withdraws 50, reducing the balance accordingly.
3. Finally, each client withdraws 30 more, leaving the balance as shown.

### Why This Structure Works Well

- **Thread Safety**: The `synchronized` methods prevent data conflicts.
- **Scalability**: You can easily increase the number of clients and accounts.
- **Flexibility**: The setup is flexible, allowing you to adjust the number of clients, accounts, and operations without changing the core code.

### Conclusion

This project simulates a small banking system where multiple clients can interact with multiple accounts safely and simultaneously. By using multithreading and synchronization, we ensure each transaction happens correctly without conflicts. The structure of this code allows easy scaling, so it could be modified to handle even more complex banking scenarios if needed.
