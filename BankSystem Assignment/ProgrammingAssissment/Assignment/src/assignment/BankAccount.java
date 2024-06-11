package assignment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Account {
    public String accountNumber, accountHolderName;
    public double balance;
    public ArrayList<String> transactionHistory;

    public Account(String accountNumber, String accountHolderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        addTransaction("Account created with initial balance: " + initialBalance);
    }

    public void deposit(double amount) {
        balance += amount;
        addTransaction("Deposited: " + amount + ", New balance: " + balance);
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            addTransaction("Failed withdrawal attempt of: " + amount);
            return false;
        }
        balance -= amount;
        addTransaction("Withdrew: " + amount + ", New balance: " + balance);
        return true;
    }

    public final void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }

    public void printTransactionHistory() {
        System.out.println("Transaction history for account " + accountNumber + ":");
        transactionHistory.forEach(System.out::println);
    }
}

public class BankAccount {
    public static HashMap<String, Account> accounts = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Banking Application Menu:");
            System.out.println("1. Create a new account");
            System.out.println("2. Deposit money");
            System.out.println("3. Withdraw money");
            System.out.println("4. Transfer money");
            System.out.println("5. View transaction history");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createAccount(scanner);
                case 2 -> deposit(scanner);
                case 3 -> withdraw(scanner);
                case 4 -> transfer(scanner);
                case 5 -> viewTransactionHistory(scanner);
                case 6 -> {
                    System.out.println("Exiting the application. Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void createAccount(Scanner scanner) {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter account holder's name: ");
        String accountHolderName = scanner.nextLine();
        System.out.print("Enter initial balance: ");
        double initialBalance = scanner.nextDouble();
        scanner.nextLine();
        accounts.put(accountNumber, new Account(accountNumber, accountHolderName, initialBalance));
        System.out.println("Account created successfully.");
    }

    public static void deposit(Scanner scanner) {
        Account account = getAccount(scanner);
        if (account != null) {
            System.out.print("Enter deposit amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            account.deposit(amount);
            System.out.println("Deposit successful.");
        }
    }

    public static void withdraw(Scanner scanner) {
        Account account = getAccount(scanner);
        if (account != null) {
            System.out.print("Enter withdrawal amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            if (account.withdraw(amount)) {
                System.out.println("Withdrawal successful.");
            } else {
                System.out.println("Insufficient balance.");
            }
        }
    }

    public static void transfer(Scanner scanner) {
        System.out.print("Enter source account number: ");
        String sourceAccountNumber = scanner.nextLine();
        Account sourceAccount = accounts.get(sourceAccountNumber);
        if (sourceAccount != null) {
            System.out.print("Enter destination account number: ");
            String destinationAccountNumber = scanner.nextLine();
            Account destinationAccount = accounts.get(destinationAccountNumber);
            if (destinationAccount != null) {
                System.out.print("Enter transfer amount: ");
                double amount = scanner.nextDouble();
                scanner.nextLine();
                if (sourceAccount.withdraw(amount)) {
                    destinationAccount.deposit(amount);
                    String transaction = "Transferred: " + amount + " from account " + sourceAccountNumber + " to account " + destinationAccountNumber;
                    sourceAccount.addTransaction(transaction);
                    destinationAccount.addTransaction(transaction);
                    System.out.println("Transfer successful.");
                } else {
                    System.out.println("Insufficient balance in source account.");
                }
            } else {
                System.out.println("Destination account not found.");
            }
        } else {
            System.out.println("Source account not found.");
        }
    }

    public static void viewTransactionHistory(Scanner scanner) {
        Account account = getAccount(scanner);
        if (account != null) {
            account.printTransactionHistory();
        }
    }

    private static Account getAccount(Scanner scanner) {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        Account account = accounts.get(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
        }
        return account;
    }
}