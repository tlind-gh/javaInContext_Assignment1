//T.Lind, 2024Sep18

//import scanner for user input
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //create scanner for receiving user input
        Scanner sc = new Scanner(System.in);

        //declare choice and check variables
        //will be assigned value via user input later, but need to be initiated here for while conditions in do-while loops
        //data type int, since user instructed in to input whole numbers
        int choice;

        //initialize balance variable, starting account balance is 0 for this program
        //data type double, since account balance will be subject operations resulting in decimal numbers
        double balance = 0;

        //greet user and inform of current account balance
        System.out.println("Welcome to the program for invoice and salary management!");
        System.out.println("\nThe current account balance is: "+balance);

        //allow user to change the account balance if erroneous
        System.out.print("Is balance correct (Y/N)?: ");

        if (!userInputYesNo()) {
            System.out.print("Input correct balance: ");
            //CHECK DOUBLE (also can close scanner when all input is via method
            balance = userInputPosDouble("Input must be a positive number. Try again: ");
            System.out.println("Account balance changed to: " + balance);
        }

        //do while, or try while -> give feedback on non-valid answer
        do {
            System.out.println("\nMAIN MENU");
            System.out.println("1. Pay employee salaries (press 1)");
            System.out.println("2. Create new invoice (press 2)");
            System.out.println("3. Pay invoices (press 3)");
            System.out.print("Choose option (or press -1 to exit program): ");

            choice = userInputMenu();
            switch (choice) {
                case 1:
                    System.out.println("\nPAY EMPLOYEE SALARIES");
                    System.out.print("Input number of employees: ");
                    int nrOfEmployees = userInputPosInt();
                    double[] salaries = new double[nrOfEmployees];
                    double sumOfSalaries = 0;
                    System.out.println("Input the salary for each employee");
                    for (int i = 0; i < nrOfEmployees; i++) {
                        System.out.print("Employee " + (i + 1) + ": ");
                        salaries[i] = userInputPosDouble("Input must be a positive number.\nEmployee"+ i +": ");
                        sumOfSalaries += salaries[i];
                    }
                    System.out.println("\nEmployee salaries after tax");
                    for (int i = 0; i < nrOfEmployees; i++) {
                        System.out.println("Employee " + (i + 1) + ": " + (salaries[i] * 0.7));
                    }
                    System.out.println("Total: " + sumOfSalaries);
                    //System.out.println("Pay employee salaries?");
                    if (sumOfSalaries > balance) {
                        System.out.println("Insufficient funds. Salaries not payed.");

                    } else {
                        balance -= sumOfSalaries;
                        System.out.println("\nSalaries payed. \nNew account balance: " + balance);
                    }
                    break;

                case 2:
                    System.out.println("\nCREATE NEW INVOICE");
                    System.out.print("Input the sum of new invoice: ");
                    double costOutgoingInvoice = userInputPosDouble("Input must be a positive number.\nTry again: ");
                    double vat = costOutgoingInvoice * 0.20;
                    balance += (costOutgoingInvoice - vat);

                    System.out.println("\nTotal: " + costOutgoingInvoice);
                    System.out.println("VAT: " + vat);
                    System.out.println("Before VAT: " + (costOutgoingInvoice - vat));
                    System.out.println("\nInvoice created successfully.\nNew account balance: " + balance);
                    break;

                case 3:
                    System.out.println("\nPAY INVOICES");
                    System.out.println("Input the number of invoices to be payed: ");
                    int nrOfInvoices = userInputPosInt();
                    double sumOfInvoices = 0;
                    double costIncomingInvoice;
                    System.out.println("Input the amount of each invoice");
                    for (int i = 0; i < nrOfInvoices; i++) {
                        System.out.print("Invoice " + (i + 1) + ": ");
                        costIncomingInvoice = userInputPosDouble("Input must be a positive number.\nInvoice "+ i +": ");
                        sumOfInvoices += costIncomingInvoice * 0.8;
                    }
                    System.out.println("Sum of invoices to be payed (w/o VAT): " + sumOfInvoices);

                    if (sumOfInvoices > balance) {
                        System.out.println("Insufficient funds. Invoices not payed.");

                    } else {
                        balance -= sumOfInvoices;
                        System.out.println("\nInvoices payed. \nNew account balance: " + balance);
                    }
                    break;

                case -1:
                    System.out.println("\nProgram terminated");
                    break;

                default:
                    System.out.println("Input a number between 1-3 or press -1 to exit program: ");
                    choice = userInputMenu();
            }

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

    static double userInputPosDouble(String s) {
        double inputDouble;
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                inputDouble = sc.nextDouble();
                if (inputDouble >= 0) {
                    break;
                } else {
                    System.out.print(s);
                }
            } catch (InputMismatchException e) {
                System.out.print(s);
                sc.nextLine();
            }
        }
        return inputDouble;
    }

}
