package assignmentKunalDkatalis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class ATM {
  public static void main(String[] args)  {

    Scanner sc = new Scanner(System.in);
    Bank theBank = new Bank("Assignment"); //title of bank	

    Customer user;

    while (true) { //loop which runs until program closes/ is quit

      // stay in login prompt until successful login
      user = ATM.mainMenuPrompt(theBank, sc);

      // stay in main menu until user quits
      ATM.customerMenu(user, sc, theBank);

    }

  }

  public static Customer mainMenuPrompt(Bank theBank, Scanner sc) { //Print the ATM's login menu.

    String input;
    Customer authUser = null;

    do {
      System.out.printf("|\tWelcome to Bank %s\t\t|\n", theBank.getName());
      System.out.println("|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|");
      System.out.print("|\tPlease log into your account to continue \t|\n");
      input = sc.nextLine();
      String[] login = input.split(" ");

      if (!"login".equals(login[0])) {
        System.out.println("Incorrect detail " + "Please try again");
      } else {
        authUser = theBank.userLogin(login[1], theBank); // try to get user object corresponding to name
        if (authUser == null) {
          System.out.println("Incorrect detail " +
            "Please try again");
        }
      }
      System.out.println("|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|");
      System.out.println("Welcome :" + authUser.getUserName());
	  System.out.println("Your balance is : ₹" + authUser.getAcctBalance(0));
      System.out.println("|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|");
    } while (authUser == null); // continue looping until successful login by user or quit 
    return authUser;
  }

  public static void customerMenu(Customer theUser, Scanner sc, Bank bank) { //displays ATMS menu and available commands	
    String input;
    ArrayList < String > commands = new ArrayList < > (
      Arrays.asList("deposit", "withdraw", "transfer", "logout"));
    String[] choice;

    do { // user menu

      System.out.println("What would you like to do?");
      System.out.println("  1) Deposit (deposit $amount )");
      System.out.println("  2) Withdraw (withdraw $amount )");
      System.out.println("  3) Transfer (transfer $receiver $amount)");
      System.out.println("  4) Logout (logout)");
      System.out.println();
      System.out.print("|Enter command: ");
      input = sc.nextLine();
      choice = input.split(" ");

      if (!commands.contains(choice[0])) {
        System.out.println("Invalid command.");
      }

    } while (!commands.contains(choice[0]));

    switch (choice[0]) { // process choice

    case "deposit":
	  if(choice.length < 2){
		System.out.println("Missing parameters in command.");
		break;
	  }
      ATM.depositFunds(theUser, sc, choice);
      break;
    case "withdraw":
	if(choice.length < 2){
		System.out.println("Missing parameters in command.");
		break;
	  }
      ATM.withdrawFunds(theUser, sc, choice); //withdraw funds from account
      break;
    case "transfer":
	if(choice.length < 3){
		System.out.println("Missing parameters in command.");
		break;
	  }
      ATM.transferFunds(theUser, sc, bank, choice); //transfer from one account to other
      break;
    case "logout":
	System.out.println("Goodbye :" + theUser.getUserName());
      break;
    }
    if (!"logout".equals(choice[0])) {
      ATM.customerMenu(theUser, sc, bank); //display menu until user wants to quit
    }

  }

  public static void transferFunds(Customer theUser, Scanner sc, Bank bank, String[] choice) { //Process transfer from one account to another.

    int account;
    int toAcct;
    double amount;
    double acctBal;
    String receiver;

    account = 0;
    acctBal = theUser.getAcctBalance(account);
    Customer receiverObj;

    receiverObj = bank.validReceiver(choice[1]);
    if (receiverObj == null) {
      do { // prompt for account to transfer to
        System.out.printf("Enter the correct name of receiver : ");
        receiver = sc.nextLine();
        receiverObj = bank.validReceiver(choice[1]);
        if (receiverObj == null) {
          System.out.println("Invalid account. Please try again.");
        }
      } while (receiverObj == null);
    }

    amount = Double.parseDouble(choice[2]);
    // get amount to transfer
    if (amount < 0 || amount > acctBal) {
      do { // prompt for amount to transfer
        System.out.printf("Enter the correct amount to transfer (max ₹ %.02f): ₹",
          acctBal);
        amount = sc.nextDouble();
        if (amount < 0) {
          System.out.println("Amount must be greater than zero.");
        } else if (amount > acctBal) {
          System.out.printf("Amount must not be greater than balance " +
            "of ₹.02f.\n", acctBal);
        }
      } while (amount < 0 || amount > acctBal);
    }
	theUser.addAcctTransaction(account, -1 * amount, "transfer");
    receiverObj.addAcctTransaction(0, amount, "transfer");

	
	System.out.printf("Transfered ₹ %.02f to %s ",
	amount , receiverObj.getUserName());

    System.out.printf("Your current balance is ₹ %.02f: ₹ ",
	theUser.getAcctBalance(account));
  }

  public static void withdrawFunds(Customer theUser, Scanner sc, String[] choice) { //process a withdrawl from an account

    int account;
    double amount;
    double acctBal;
    String txReference = "withdraw";

    account = 0; // default account
    acctBal = theUser.getAcctBalance(account);
    amount = Double.parseDouble(choice[1]);
    // get amount to transfer
    if (amount < 0 || amount > acctBal) {
      do {
        System.out.printf("Enter the amount to withdraw (max ₹ %.02f): ₹ ",
          acctBal);
        amount = sc.nextDouble();
        if (amount < 0) {
          System.out.println("Amount must be greater than zero.");
        } else if (amount > acctBal) {
          System.out.printf("Amount must not be greater than balance " +
            "of ₹%.02f.\n", acctBal);
        }
      } while (amount < 0 || amount > acctBal);
    }

    theUser.addAcctTransaction(account, -1 * amount, txReference);

    acctBal = theUser.getAcctBalance(account);
    System.out.printf("Your current balance is ₹ %.02f: ₹ ",
      acctBal);
  }

  public static void depositFunds(Customer theUser, Scanner sc, String[] choice) { //process a deposit into an account

    int account;
    double amount = 0.0;
    String txReference = "deposit";

    account = 0; //default account

    // get amount to transfer
    amount = Double.parseDouble(choice[1]);
    if (amount < 0) {
      do {
        System.out.printf("Enter correct amount to deposit: ₹ ");
        amount = sc.nextDouble();
        if (amount < 0) {
          System.out.println("Amount must be greater than zero.");
        }
      } while (amount < 0);
    }

    // do the deposit
    theUser.addAcctTransaction(account, amount, txReference);

    double acctBal = theUser.getAcctBalance(account);
    System.out.printf("Your current balance is ₹ %.02f: ₹ ",
      acctBal);
  }

}