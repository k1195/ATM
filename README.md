# javaATM
An ATM machine coded in Java which is intended to be run using the console.

## Commands

* `login [name]` - Logs in as this customer and creates the customer if not exist
* `deposit [amount]` - Deposits this amount to the logged in customer
* `withdraw [amount]` - Withdraws this amount from the logged in customer
* `transfer [target] [amount]` - Transfers this amount from the logged in customer to the target customer
* `logout` - Logs out of the current customer

## Examples

* login TestUser
* deposit 10000
* withdraw 500
* transfer TestUser2 500
