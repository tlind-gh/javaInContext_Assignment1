//T.Lind, Sep2024

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        /*General note: all user input is passed via methods (located below main method) which catch invalid
        user input and loops until the user provides a valid input for the current prompt*/

        /*variable for account balance, starts at zero and can be changed at program start-up
        (would, in an actual program of this type, be imported from memory/external source)*/
        double accountBalance = 0;

        //greet user and inform of current account balance
        System.out.println("\nWelcome to the program for invoice and salary management!");
        System.out.println("The current account balance is: "+ accountBalance);

        /*allow user to change the account balance if erroneous (obvious bad to give the user this power
        in a real accounting software, but is included here to be able to set different amounts during testing
        of the program w/o altering the code)*/
        System.out.print("Is balance correct (Y/N)?: ");
        if (!userInputYesNo()) {
            System.out.print("Input correct balance: ");
            accountBalance = userInputPosDouble("Input must be a positive number. Try again: ");
            System.out.println("Account balance changed to: " + accountBalance);
        }

        /*do-while loop for the main menu (and the different actions/options within it) that keeps the program
        running until the user actively chooses to exit the program*/

        //variable for holding user input in main menu
        int choice;

        do {
            System.out.println("\nMAIN MENU");
            System.out.println("1. Pay employee salaries (press 1)");
            System.out.println("2. Create new invoice (press 2)");
            System.out.println("3. Pay invoices (press 3)");
            System.out.print("Choose option (or press -1 to exit program): ");

            //receive user input and jump to correct code section using a switch expression
            choice = userInputMenu();
            switch (choice) {

                case 1:
                    //confirm the user choice via print statement and ask for nr of employees
                    System.out.println("\nPAY EMPLOYEE SALARIES");
                    System.out.print("Input number of employees: ");
                    int nrOfEmployees = userInputPosInt();

                    //list to store employee salaries
                    double[] salaries = new double[nrOfEmployees];
                    //variable for holding sum of all salaries after tax(-30%)
                    double netSumSalaries = 0;

                    //loop the list and store salaries (from user input)
                    System.out.println("Input the salary for each employee");
                    for (int i = 0; i < nrOfEmployees; i++) {
                        System.out.print("Employee " + (i + 1) + ": ");
                        salaries[i] = userInputPosDouble("Input must be a positive number.\nEmployee"+ (i+1) +": ");
                        netSumSalaries += salaries[i]*0.7;
                    }

                    //print employee salaries after tax and print total sum after tax
                    System.out.println("\nEmployee salaries after tax");
                    int j = 1;
                    for (double salary : salaries) {
                        System.out.println("Employee " + j + ": " + (salary * 0.7));
                        j++;
                    }
                    System.out.println("Total (after tax): " + netSumSalaries);

                    //ask user if they want to pay salaries using makePayment method
                    //(not specified in Assigment description, but option is called "Betala ut löner" so it is implied)
                    accountBalance = makePayment(accountBalance,netSumSalaries,"salaries");
                    break;

                case 2:
                    //confirm the user choice via print statement and ask for the invoice amount
                    System.out.println("\nCREATE NEW INVOICE");
                    System.out.print("Input the cost to be invoiced: ");
                    double costOutgoingInvoice = userInputPosDouble("Input must be a positive number.\nTry again: ");

                    //store the VAT tax in a separate variable
                    double vat = costOutgoingInvoice * 0.20;
                    //add the invoiced amount (w/o VAT) to the account balance
                    accountBalance += (costOutgoingInvoice - vat);

                    //inform user of details of the created invoice and the new account balance
                    System.out.println("\nTotal: " + costOutgoingInvoice);
                    System.out.println("VAT: " + vat);
                    System.out.println("Total after VAT deduction: " + (costOutgoingInvoice - vat));
                    System.out.println("\nInvoice created successfully.\nNew account balance: " + accountBalance);
                    break;

                case 3:
                    //confirm the user choice via print statement and ask for nr of invoices to be paid
                    System.out.println("\nPAY INVOICES");
                    System.out.println("Input the number of invoices to be payed: ");
                    int nrOfInvoices = userInputPosInt();

                    /*variable for storing user input. NB! not stored in a list, since we only need the sum
                    of all invoices and individual invoice amounts will not be used by the program again*/
                    double costIncomingInvoice;
                    //variable for holding sum (with VAT tax deducted) of all invoices
                    double netSumInvoices = 0;

                    //ask for invoice amounts
                    System.out.println("Input the amount of each invoice");
                    for (int i = 0; i < nrOfInvoices; i++) {
                        System.out.print("Invoice " + (i + 1) + ": ");
                        costIncomingInvoice = userInputPosDouble("Input must be a positive number.\nInvoice "+ (i +1) +": ");
                        netSumInvoices += costIncomingInvoice * 0.8;
                    }
                    System.out.println("Sum of invoices to be payed after VAT deduction: " + netSumInvoices);

                    //ask user if they want to pay invoices using makePayment method
                    accountBalance = makePayment(accountBalance,netSumInvoices,"invoices");
                    break;

                case -1:
                    //confirm via print statement that the program has been terminated
                    System.out.println("\nProgram terminated");
                    break;

                default:
                    //if user input is not -1, 1, 2 or 3 the user is asked for new input
                    //(should not be able to happen due to the userInputMenu() method, but added here just in case
                    System.out.println("Input a number between 1-3 or press -1 to exit program: ");
                    choice = userInputMenu();
            }

            //ask user if they want to return to main menu after completed action (e.g., pay salaries) or exit
            if (choice != -1) {
                System.out.print("\nPress 1 to return to main menu or press -1 to exit program: ");
                int input = userInputMenu();
                if (input == -1) {
                    choice = -1;
                }
            }
        } while (choice != -1);
    }


    /*General info userInput methods (i.e., all methods except the "makePayment()"-method:
    1. All methods userInput methods use a while-loop where while is always true. The loops are broken with
    break statements which are only reached after user has given correct/valid input (method dependent)
    2. All methods userInput methods except userInputYesNo() has a try/catch expression to handle user input that is of incorrect data type
    (not int or not double, depending on method). userInputYesNo() stores the input in a String and can therefor
    handle any user input without java giving an error.*/


    static boolean userInputYesNo() {
        /*method for handling user input for yes/no-prompts within the main program. returns a boolean which is
        true or false according to user input (yes = true, no = false)*/

        //boolean needs to be set to false or true to not give an error for the return statement
        boolean isYes = false;
        Scanner sc = new Scanner(System.in);

        //runs until user gives valid input (i.e., y, yes, n or no)
        while (true) {
            String inputString = sc.nextLine();
            /*convert to lowercase to make the method upper/lowercase insensitive (i.e., it does not matter for the
            method output if user types in lower or uppercase*/
            inputString = inputString.toLowerCase();

            //methods accepts both full words (yes/no) and single characters (y/n) and sets boolean accordingly
            switch (inputString) {
                case "y": case "yes":
                    isYes = true;
                    break;
                case "n": case "no":
                    break;
                default:
                    /*if input is not one of the case option, ask the user to try again and continues to next iteration
                    of loop (thus not reaching the break statement below and cont. while loop)*/
                    System.out.print("Please press Y or N: ");
                    continue;
            }
            break;
        }
        return isYes;
    }

    static int userInputMenu() {
        //method for handling user input within the main menu in the main program
        int inputMenu;

        //message to user when entering invalid input
        String userErrorMessage = "Input a number between 1-3 or press -1 to exit program: ";
        Scanner sc = new Scanner(System.in);

        //runs until user gives valid menu input (1,2,3 or -1)
        while (true) {
            try {
                inputMenu = sc.nextInt();
                //valid input -> break loop
                if (inputMenu == -1 || (inputMenu >= 1 && inputMenu <= 3)) {
                    break;
                } else {
                    System.out.print(userErrorMessage);
                }
            } catch (InputMismatchException e) {
                System.out.print(userErrorMessage);
                sc.nextLine();
            }

        }
        return inputMenu;
    }

    static int userInputPosInt() {
        //method for handling user input that needs to be a positive integer

        int inputInt;
        //message to user when entering invalid input
        String s = "Input must be a positive whole number. Try again: ";
        Scanner sc = new Scanner(System.in);

        //runs until user gives valid menu input (i.e., a whole number larger than zero)
        while (true) {
            try {
                inputInt = sc.nextInt();
                //valid input -> break loop
                if (inputInt > 0) {
                    break;
                } else {
                    System.out.print(s);
                }
            } catch (InputMismatchException e) {
                System.out.print(s);
                sc.nextLine();
            }
        }
        return inputInt;
    }

    static double userInputPosDouble(String userErrorMessage) {
        //method for handling user input that needs to be a double equal to or larger than zero
        //appropriate printed error message to user is passed into the method via a string when it is called from main program

        double inputDouble;
        Scanner sc = new Scanner(System.in);

        //runs until valid input is given (i.e, number >= 0)
        while (true) {
            try {
                inputDouble = sc.nextDouble();
                //valid input -> break loop
                if (inputDouble >= 0) {
                    break;
                } else {
                    System.out.print(userErrorMessage);
                }
            } catch (InputMismatchException e) {
                System.out.print(userErrorMessage);
                sc.nextLine();
            }
        }
        return inputDouble;
    }

    static double makePayment(double accountBalance, double cost, String costType) {
        /*method for making a payment from the account. method only allows payment if account has sufficient funds
        the methods is called with three variables which are:
        1. accountBalance (current account balance from main)
        2. cost (amount to be paid/withdrawn from account)
        3. costType (a string specifying what the payment is for, e.g., salaries) which is used in print statements*/

        System.out.print("\nPay "+ costType +" (Y/N)?: ");
        //calls method for handling y/n user input
        if (userInputYesNo()) {
            //check if account balance is sufficient to pay the invoices
            if (cost > accountBalance) {
                System.out.println("Payment could not be made due to insufficient funds.\nCurrent account balance: " + accountBalance);
            } else {
                accountBalance -= cost;
                System.out.println("Payment made. New account balance: " + accountBalance);
            }
        } else {
            System.out.println("No payment made. Current account balance: " + accountBalance);
        }
        return accountBalance;
    }

}
