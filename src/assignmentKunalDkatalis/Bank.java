package assignmentKunalDkatalis;

import java.util.ArrayList;
import java.util.Random;

public class Bank {

  private String name; //The name of the bank.
  private ArrayList < Customer > usersList; //The account holders of the bank.
  private ArrayList < Account > accountsList; //The accounts of the bank.

  public Bank(String name) {

    this.name = name;

    usersList = new ArrayList < Customer > ();
    accountsList = new ArrayList < Account > ();

  }

  public Customer addUser(String firstName) { //creates a new user in the banks system

    // create a new User object and add it to our list
    Customer newUser = new Customer(firstName, this);
    this.usersList.add(newUser);

    // create a account for the user and add it to our list
    Account newAccount = new Account(newUser, this);
    newUser.addAccount(newAccount);
    this.accountsList.add(newAccount);

    return newUser;

  }

  public void addAccount(Account newAccount) {
    this.accountsList.add(newAccount);
  }

  public String getNewAccountUUID() { //generate a new unique ID for an account
    String uuid;
    Random rng = new Random();
    int len = 10;
    boolean nonUnique = false;
    do {
      uuid = "";
      for (int c = 0; c < len; c++) {
        uuid += ((Integer) rng.nextInt(10)).toString(); //generates a number
      }
      for (Account a: this.accountsList) {
        if (uuid.compareTo(a.getUUID()) == 0) { //if the number is unique, we will use
          nonUnique = true;
          break;
        }
      }
    } while (nonUnique); // continue looping until we get a unique ID
    return uuid;
  }

  public Customer userLogin(String userName, Bank bank) { //checks using given info is given and if so opens account
    for (Customer user: bank.usersList) { // search until end of list of users
      if (user.getUserName().compareTo(userName) == 0) { // if user found/ else create user and , return User object
        return user;
      }
    }

    Customer user = bank.addUser(userName);
    return user;
  }

  public Customer validReceiver(String receiver) {
    for (Customer user: this.usersList) { // search until end of list of users
      if (user.getUserName().compareTo(receiver) == 0) {
        return user;
      }
    }
    return null;
  }

  public String getName() {
    return this.name;
  }

} //bank