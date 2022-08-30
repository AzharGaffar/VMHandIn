import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransactionEntries {

    private List<TransactionEntry> listOfTransactions;

    public TransactionEntries(List<TransactionEntry> listOfTransactions) {
        this.listOfTransactions = listOfTransactions;
    }

    public List<TransactionEntry> getListOfTransactions() {
        return listOfTransactions;
    }

    public void setListOfTransactions(List<TransactionEntry> listOfTransactions) {
        this.listOfTransactions = listOfTransactions;
    }

    public void addTransaction(TransactionEntry transactionEntry) {
        listOfTransactions.add(transactionEntry);
    }

    public TransactionEntries getAllTransactionsForGivenCategory(String transactionCategory) {
        return new TransactionEntries(listOfTransactions.stream()
                .filter(transactionEntry -> transactionEntry.getTransactionCategory().equalsIgnoreCase(transactionCategory))
                .sorted(Comparator.comparing(TransactionEntry::getTransactionDate).reversed()).collect(Collectors.toList()));
    }

    public String totalAmountOutgoingPerCategory() {
        Map<String, BigDecimal> transactionCategoryWithTotalAmount = listOfTransactions.stream()
                .collect(Collectors.toMap(TransactionEntry::getTransactionCategory, TransactionEntry::getTransactionAmount,
                        BigDecimal::add));

        StringBuilder result = new StringBuilder("The total amount outgoing per category is as follows:\n");

        transactionCategoryWithTotalAmount.forEach((key, value) -> {
            if (key.equals(" ")) {
                result.append("<Undefined Category>: £").append(value).append("\n");
            } else {
                result.append(key).append(": £").append(value).append("\n");
            }
        });

        return result.toString();
    }

    // Commenting this method as I feel it is a bit hard to read.
    public String monthlyAverageSpendForGivenCategory(String transactionCategory) {
        String finalTransactionCategory = transactionCategory;

        // Creating a sorted reusable stream for the filtered category that can be called whenever it is needed.
        Supplier<Stream<TransactionEntry>> transactionsWithFilteredCategory = () -> listOfTransactions.stream()
                .filter(transaction -> transaction.getTransactionCategory().equalsIgnoreCase(finalTransactionCategory))
                .sorted(Comparator.comparing(TransactionEntry::getTransactionDate));


        // Making a Map that will have the Month and Year alongside the total amount spent in that time period.
        Map<YearMonth, BigDecimal> transactionMonthAndYearWithAverageAmountPerMonth = transactionsWithFilteredCategory.get()
                .collect(Collectors.toMap(transaction -> YearMonth.of(transaction.getTransactionDate().getYear(), transaction.getTransactionDate().getMonth()),
                        TransactionEntry::getTransactionAmount,
                        BigDecimal::add));

        // Looping over this map and dividing the total amounts by the number of transactions that occurred in that time period to get the average.
        transactionMonthAndYearWithAverageAmountPerMonth.forEach((key, value) -> transactionMonthAndYearWithAverageAmountPerMonth.put(key,
                value.divide(BigDecimal.valueOf(transactionsWithFilteredCategory.get()
                                .filter(transaction -> YearMonth.of(transaction.getTransactionDate().getYear(), transaction.getTransactionDate().getMonth()).equals(key)).count()),
                        RoundingMode.HALF_EVEN)));

        if (transactionCategory.equals(" ")) {
            transactionCategory = "<Undefined>";
        }

        if(transactionMonthAndYearWithAverageAmountPerMonth.isEmpty()){
            return "No monthly average spend for the category "+transactionCategory.toUpperCase()+".\n";
        }

        StringBuilder finalResult = new StringBuilder("The monthly average spend for the " + transactionCategory.toUpperCase()
                + " category is as follows:\n" +
                "(Please note: if a specific time period is not listed, this means that there was never any transaction made in this month and year and therefore the average spend will be £0)\n");

        for (Map.Entry<YearMonth, BigDecimal> entry : transactionMonthAndYearWithAverageAmountPerMonth.entrySet()) {
            finalResult.append(entry.getKey().getMonth()).append(" ").append(entry.getKey().getYear()).append(": £").append(entry.getValue()).append("\n");
        }

        return finalResult.toString();
    }

    private List<TransactionEntry> getTransactionsForAGivenCategoryForAGivenYearArrangedByHighestToLowestSpending(String transactionCategory, int year) {
        return listOfTransactions.stream().filter(transaction -> transaction.getTransactionCategory().equalsIgnoreCase(transactionCategory))
                .filter(transaction -> transaction.getTransactionDate().getYear() == year)
                .sorted(Comparator.comparing(TransactionEntry::getTransactionAmount).reversed())
                .collect(Collectors.toList());
    }

    public String highestOrLowestSpendingInAGivenCategoryForAGivenYear(String highestOrLowest, String transactionCategory, int year) {
        List<TransactionEntry> transactions = getTransactionsForAGivenCategoryForAGivenYearArrangedByHighestToLowestSpending(transactionCategory, year);

        if (transactionCategory.equals(" ")) {
            transactionCategory = "<Undefined Category>";
        }

        if (transactions.size() == 0) {
            return "NO TRANSACTIONS FOR THE GIVEN CATEGORY AND YEAR TO DISPLAY\n";
        }

        if (highestOrLowest.equalsIgnoreCase("highest")) {
            TransactionEntry transaction = transactions.get(0);
            return "The highest spend for the year " + year + " in the category of " + transactionCategory.toUpperCase()
                    + " was: £" + transaction.getTransactionAmount() + "\n"
                    + "The full transaction details are below:\n"
                    + transaction + "\n";
        } else if (highestOrLowest.equalsIgnoreCase("lowest")) {
            TransactionEntry transaction = transactions.get(transactions.size() - 1);
            return "The lowest spend for the year " + year + " in the category of " + transactionCategory.toUpperCase()
                    + " was: £" + transaction.getTransactionAmount() + "\n"
                    + "The full transaction details are below:\n"
                    + transaction + "\n";
        } else {
            return "Please only use parameter of \"highest\" or \"lowest\"";
        }
    }

    @Override
    public String toString() {
        StringBuilder transactionString = new StringBuilder();
        for (TransactionEntry transaction : listOfTransactions) {
            transactionString.append(transaction.toString());
            transactionString.append("\n");
        }

        if (transactionString.isEmpty()) {
            return "NO TRANSACTIONS TO DISPLAY";
        }

        return transactionString.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionEntries that = (TransactionEntries) o;
        return Objects.equals(listOfTransactions, that.listOfTransactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listOfTransactions);
    }
}
