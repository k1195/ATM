package assignmentKunalDkatalis;
import java.util.ArrayList;

public class Customer {

  private String userName;
  private ArrayList < Account > custsAccounts; //The list of accounts for this user.

  public Customer(String firstName, Bank theBank) { //create new customer with given pin, name and branch

    // set user's name
    this.userName = firstName;

    // create empty list of accounts
    this.custsAccounts = new ArrayList < Account > ();

  }

  public String getUserName() {
    return this.userName;
  }

  public void addAccount(Account anAcct) {
    this.custsAccounts.add(anAcct);
  }

  public int numAccounts() {
    return this.custsAccounts.size(); //get number of user accounts
  }

  public double getAcctBalance(int acctIdx) {
    return this.custsAccounts.get(acctIdx).getBalance();
  }

  public void addAcctTransaction(int acctIdx, double amount, String memo) {
    this.custsAccounts.get(acctIdx).addTransaction(amount, memo);
  }

} //customer