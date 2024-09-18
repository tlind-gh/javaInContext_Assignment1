//T.Lind, Sep2024

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        /*General note: all user input is passed via methods (located below main method) which catch invalid
        user input and loops until the user provides a valid input for the current prompt*/

        //scanner for receiving user input
        Scanner sc = new Scanner(System.in);
        //variable for account balance, starts at zero but can be changed at program start-up
        double accountBalance = 0;

        //greet user and inform of current account balance
        System.out.println("Welcome to the program for invoice and salary management!");
        System.out.println("\nThe current account balance is: "+ accountBalance);

        //allow user to change the account balance if erroneous
        System.out.print("Is balance correct (Y/N)?: ");

        if (!userInputYesNo()) {
            System.out.print("Input correct balance: ");
            accountBalance = userInputPosDouble("Input must be a positive number. Try again: ");
            System.out.println("Account balance changed to: " + accountBalance);
        }

        /*main menu and the different actions within are incapsulated in a do-while loop to keep
        the program running (and return the user to main menu after an action (e.g., paying salaries)
        is complete)*/

        //variable for holding user input in main menu
        int choice;

        do {
            System.out.println("\nMAIN MENU");
            System.out.println("1. Pay employee salaries (press 1)");
            System.out.println("2. Create new invoice (press 2)");
            System.out.println("3. Pay invoices (press 3)");
            System.out.print("Choose option (or press -1 to exit program): ");

            //run the correct menu option (according to user input) using a switch expression
            choice = userInputMenu();
            switch (choice) {

                case 1:
                    //confirm the user choice via print statement and ask for nr of employees
                    System.out.println("\nPAY EMPLOYEE SALARIES");
                    System.out.print("Input number of employees: ");
                    int nrOfEmployees = userInputPosInt();
                    //create a list to hold employee salaries
                    double[] salaries = new double[nrOfEmployees];
                    //variable for holding sum of all salaries after tax(-30%) in the list
                    double netSumSalaries = 0;

                    //loop the list and store salaries (from user input)
                    System.out.println("Input the salary for each employee");
                    for (int i = 0; i < nrOfEmployees; i++) {
                        System.out.print("Employee " + (i + 1) + ": ");
                        salaries[i] = userInputPosDouble("Input must be a positive number.\nEmployee"+ (i+1) +": ");
                        netSumSalaries += salaries[i]*0.7;
                    }

                    //print employee salaries after tax and print total sum (after tax)
                    System.out.println("\nEmployee salaries after tax");
                    int j = 1;
                    for (double salary : salaries) {
                        System.out.println("Employee " + j + ": " + (salary * 0.7));
                        j++;
                    }
                    System.out.println("Total (after tax): " + netSumSalaries);

                    //ask user if they want to pay salaries (not specified in Assigment description, but option is called "Betala ut löner" so it is implied)
                    accountBalance = makePayment(accountBalance,netSumSalaries,"salaries");

                    /*System.out.print("\nPay employee salaries (Y/N)? :");
                    if (userInputYesNo()) {
                        if ((netSumSalaries) > accountBalance) {
                            System.out.println("Insufficient funds. Salaries not payed.");
                        } else {
                            accountBalance -= netSumSalaries;
                            System.out.println("\nSalaries payed. \nNew account balance: " + accountBalance);
                        }
                    } else {
                        System.out.println("Salaries not payed.");
                    }*/
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
                    //confirm the user choice via print statement and ask for nr of invoices to be payed
                    System.out.println("\nPAY INVOICES");
                    System.out.println("Input the number of invoices to be payed: ");
                    int nrOfInvoices = userInputPosInt();
                    //variable for storing user input
                    //NB! not stored in a list, since we only need the sum of all invoices and individual invoice amounts will not be used by the program again
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

                    //ask user if they want to pay invoices
                    accountBalance = makePayment(accountBalance,netSumInvoices,"invoices");
                    /*System.out.print("\nPay invoices (Y/N)? :");
                    if (userInputYesNo()) {

                        //check if account balance is sufficient to pay the invoices
                        if (netSumInvoices > accountBalance) {
                            System.out.println("Insufficient funds. Invoices not payed.");
                        } else {
                            accountBalance -= netSumInvoices;
                            System.out.println("\nInvoices payed. \nNew account balance: " + accountBalance);
                        }
                    } else {
                        System.out.println("Invoices not payed.");
                    }*/
                    break;

                case -1:
                    //confirm via print statement that the program has been terminated
                    System.out.println("\nProgram terminated");
                    break;

                default:
                    //if user input is not -1, 1, 2 or 3 the user is asked for new input
                    //(should not be able to happen due to the userInoutMenu() method, but added here just in case
                    System.out.println("Input a number between 1-3 or press -1 to exit program: ");
                    choice = userInputMenu();
            }

            //ask user if they want to return to main menu after completed action (e.g., pay salaries)
            if (choice != -1) {
                System.out.print("\nPress 1 to return to main menu or press -1 to exit program: ");
                int input = userInputMenu();
                if (input == -1) {
                    choice = -1;
                }
            }
        } while (choice != -1);
        sc.close();
    }

    static boolean userInputYesNo() {
        boolean isYes = false;
        Scanner sc = new Scanner(System.in);
        String inputString = sc.nextLine();
        inputString = inputString.toLowerCase();
        switch (inputString) {
            case "y": case "yes":
                isYes = true;
                break;
            case "n": case "no":
                break;
            default :
                System.out.print("Please press Y or N: ");
                userInputYesNo();
        }
        return isYes;
    }

    static int userInputMenu() {
        int inputMenu;
        String s = "Input a number between 1-3 or press -1 to exit program: ";
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                inputMenu = sc.nextInt();
                if (inputMenu == -1 || (inputMenu >= 1 && inputMenu <= 3)) {
                    break;
                } else {
                    System.out.print(s);
                }
            } catch (InputMismatchException e) {
                System.out.print(s);
                sc.nextLine();
            }

        }
        return inputMenu;
    }

    static int userInputPosInt() {
        int inputInt;
        String s = "Input must be a positive whole number. Try again: ";
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                inputInt = sc.nextInt();
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
        double inputDouble;
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                inputDouble = sc.nextDouble();
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
        System.out.print("\nPay "+ costType +" (Y/N)?: ");
        if (userInputYesNo()) {
            //check if account balance is sufficient to pay the invoices
            if (cost > accountBalance) {
                System.out.println("Payment could not be made due to insuffient funds.\nCurrent account balance: " + accountBalance);
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
