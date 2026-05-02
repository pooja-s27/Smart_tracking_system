import java.util.ArrayList;
import java.util.Scanner;

class Main{
    public static void main(String[] args){

        ArrayList<Transaction> transaction = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        try {
            java.io.File file = new java.io.File("transactions.txt");
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();


                if (line.trim().isEmpty()) {
                    continue; // skip empty lines
                }

                String[] parts = line.split(",");

                if (parts.length == 4) {
                    double amount = Double.parseDouble(parts[0].trim());
                    String type = parts[1].trim();
                    String category = parts[2].trim();
                    String date = parts[3].trim();

                    transaction.add(new Transaction(amount, type, category, date));
                }
            }
        }
            catch (Exception e) {
                e.printStackTrace();
            }


        System.out.println("********************************");
        System.out.println("Welcome to Smart Expense Tracker!");
        System.out.println("********************************");




        while(true) {
            System.out.println("1. Add Transaction ");
            System.out.println("2. View all Transactions ");
            System.out.println("3. Show Summary ");
            System.out.println("4. Exit ");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            if (choice == 1) {
                System.out.print("Enter amount: ");
                double amount = sc.nextDouble();
                sc.nextLine();

                System.out.print("Enter type (Income/Expense): ");
                String type = sc.nextLine();

                System.out.print("Enter category: ");
                String category = sc.nextLine();

                System.out.print("Enter date: ");
                String date = sc.nextLine();

                Transaction t = new Transaction(amount,type,category,date);
                transaction.add(t);

                try {
                    java.io.FileWriter fw = new java.io.FileWriter("transactions.txt", true);
                    fw.write(amount + "," + type + "," + category + "," + date + "\n");
                    fw.close();
                } catch (Exception e) {
                    System.out.println("Error saving data");
                }


                System.out.println("Transaction added!");

            }


            else if (choice == 2){
                for(Transaction t : transaction){
                    System.out.println("Amount: " + t.amount +
                            ", Type: " + t.type +
                            ", Category: " + t.category +
                            ", Date: " + t.date);
                }

            }

            else if (choice == 3) {

                double totalIncome = 0;
                double totalExpense = 0;

                for (Transaction t : transaction) {
                    if (t.type.equalsIgnoreCase("Income")) {
                        totalIncome += t.amount;
                    } else if (t.type.equalsIgnoreCase("Expense")) {
                        totalExpense += t.amount;
                    }

                }


                double balance = totalIncome - totalExpense;

                System.out.println("Total Income: " + totalIncome);
                System.out.println("Total Expense: " + totalExpense);
                System.out.println("Balance: " + balance);

                java.util.HashMap<String, Double> categoryMap = new java.util.HashMap<>();

                for (Transaction t : transaction) {
                    if (t.type.equalsIgnoreCase("Expense")) {
                        categoryMap.put(
                                t.category,
                                categoryMap.getOrDefault(t.category, 0.0) + t.amount
                        );
                    }

                }

                System.out.println("\n--- Category-wise Expenses ---");

                for (String category : categoryMap.keySet()) {
                    System.out.println(category + ": " + categoryMap.get(category));
                }

            }

            else if(choice == 4){
                System.out.println("**************************");
                System.out.println("Thank you for Visiting!");
                System.out.println("**************************");
                break;
            }



        }

    }
}

