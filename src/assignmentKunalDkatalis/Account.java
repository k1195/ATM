package assignmentKunalDkatalis;

import java.util.ArrayList;

public class Account {

  private static String name;
  private String uuid;

  private Customer accHolder; //the customer object that owns the account
  private ArrayList < AccountTransaction > transactions; //The list of transactions for this account.

  public Account(Customer holder, Bank theBank) { //create a account object
    this.accHolder = holder; // set the account name and holder
    // get next account UUID
    this.uuid = theBank.getNewAccountUUID();

    this.transactions = new ArrayList < AccountTransaction > ();
  }

  public String getUUID() {
    return this.uuid;
  }

  public void addTransaction(double amount) {
    AccountTransaction newTrans = new AccountTransaction(amount, this); //add transaction to list
    this.transactions.add(newTrans);

  }
  public void addTransaction(double amount, String note) { //add transaction to list

    AccountTransaction newTrans = new AccountTransaction(amount, note, this); // create new transaction and add it to our list
    this.transactions.add(newTrans);

  }

  public double getBalance() { //get balance of this account by adding its transactions' values

    double balance = 0;
    for (AccountTransaction t: this.transactions) {
      balance += t.getAmount();
    }
    return balance;

  }
  public static String getName() {
    return name;
  }

} //account