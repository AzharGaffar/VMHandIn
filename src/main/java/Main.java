public class Main {

    public static void main(String[] args) {
        TransactionFileReader transactionFileReader = new TransactionFileReader();
        try {
            System.out.println("############################## ORIGINAL TRANSACTIONS #############################################################################");
            TransactionEntries transactions = transactionFileReader.createListOfTransactionsFromFile("src/main/resources/textfiles/TransactionList.txt");
            System.out.println(transactions.toString());

            System.out.println("############################## QUESTION 1 ########################################################################################");
            TransactionEntries transactionsForGivenCategoryGroceries = transactions.getAllTransactionsForGivenCategory("Groceries");
            System.out.println(transactionsForGivenCategoryGroceries.toString());

            TransactionEntries transactionsForGivenCategoryMyMonthlyDD = transactions.getAllTransactionsForGivenCategory("mymonthlydd");
            System.out.println(transactionsForGivenCategoryMyMonthlyDD.toString());

            TransactionEntries transactionsForGivenCategoryEmpty = transactions.getAllTransactionsForGivenCategory(" ");
            System.out.println(transactionsForGivenCategoryEmpty.toString());

            System.out.println("############################## QUESTION 2 ########################################################################################");
            System.out.println(transactions.totalAmountOutgoingPerCategory());

            System.out.println("############################## QUESTION 3 ########################################################################################");
            System.out.println(transactions.monthlyAverageSpendForGivenCategory("groceries"));
            System.out.println(transactions.monthlyAverageSpendForGivenCategory("Mymonthlydd"));
            System.out.println(transactions.monthlyAverageSpendForGivenCategory(" "));
            System.out.println(transactions.monthlyAverageSpendForGivenCategory("wefwefew"));

            System.out.println("############################## QUESTION 4 ########################################################################################");
            System.out.println(transactions.highestOrLowestSpendingInAGivenCategoryForAGivenYear("highest", "MyMonthlyDD", 2020));

            System.out.println("############################## QUESTION 5 ########################################################################################");
            System.out.println(transactions.highestOrLowestSpendingInAGivenCategoryForAGivenYear("lowest", "mymonthlydd", 2020));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
