import Enums.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionEntriesTest {

    @Test
    void addTransactionTest() {
        TransactionEntries expectedResult = new TransactionEntries(List.of(new TransactionEntry(LocalDate.of(1980, 1, 1), "Amazon", Type.CARD, new BigDecimal("20.00"), "RandomSpending")));

        TransactionEntries transactionEntries = new TransactionEntries(new ArrayList<TransactionEntry>());
        TransactionEntry transaction = new TransactionEntry(LocalDate.of(1980, 1, 1), "Amazon", Type.CARD, new BigDecimal("20.00"), "RandomSpending");
        transactionEntries.addTransaction(transaction);
        Assertions.assertEquals(expectedResult, transactionEntries);
    }

    @Test
    void getAllTransactionsForGivenCategoryTestNormal() {
        TransactionEntries expectedResults = new TransactionEntries(List.of(
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Amazon", Type.CARD, new BigDecimal("20.00"), "RandomSpending"),
                new TransactionEntry(LocalDate.of(1980, 1, 1), "Argos", Type.DIRECTDEBIT, new BigDecimal("20.00"), "RandomSpending")
        ));


        TransactionEntries testTransactions = new TransactionEntries(List.of(
                new TransactionEntry(LocalDate.of(1980, 1, 1), "Argos", Type.DIRECTDEBIT, new BigDecimal("20.00"), "RandomSpending"),
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Amazon", Type.CARD, new BigDecimal("20.00"), "RandomSpending"),
                new TransactionEntry(LocalDate.of(1980, 1, 1), "Sainsburys", Type.INTERNET, new BigDecimal("20.00"), "Groceries")
        ));

        Assertions.assertEquals(expectedResults, testTransactions.getAllTransactionsForGivenCategory("RandomSpending"));
    }

    @Test
    void getAllTransactionsForGivenCategoryTestEmptyCategory() {
        TransactionEntries expectedResults = new TransactionEntries(List.of(
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Amazon", Type.CARD, new BigDecimal("20.00"), " "),
                new TransactionEntry(LocalDate.of(1980, 1, 1), "Argos", Type.DIRECTDEBIT, new BigDecimal("20.00"), " ")
        ));


        TransactionEntries testTransactions = new TransactionEntries(List.of(
                new TransactionEntry(LocalDate.of(1980, 1, 1), "Argos", Type.DIRECTDEBIT, new BigDecimal("20.00"), " "),
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Amazon", Type.CARD, new BigDecimal("20.00"), " "),
                new TransactionEntry(LocalDate.of(1980, 1, 1), "Sainsburys", Type.INTERNET, new BigDecimal("20.00"), "Groceries")
        ));

        Assertions.assertEquals(expectedResults, testTransactions.getAllTransactionsForGivenCategory(" "));
    }

    @Test
    void getAllTransactionsForGivenCategoryTestNonExistingCategory() {
        TransactionEntries emptyTransactions = new TransactionEntries(new ArrayList<TransactionEntry>());
        Assertions.assertEquals("NO TRANSACTIONS TO DISPLAY", emptyTransactions.getAllTransactionsForGivenCategory("thisshouldntexist").toString());
    }

    @Test
    void totalAmountOutgoingPerCategoryTest() {
        TransactionEntries testTransactions = new TransactionEntries(List.of(
                new TransactionEntry(LocalDate.of(1980, 1, 1), "Argos", Type.DIRECTDEBIT, new BigDecimal("20.00"), " "),
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Amazon", Type.CARD, new BigDecimal("20.00"), " "),
                new TransactionEntry(LocalDate.of(1980, 1, 1), "Sainsburys", Type.INTERNET, new BigDecimal("20.00"), "Groceries")
        ));

        String expectedResults = """
                The total amount outgoing per category is as follows:
                <Undefined Category>: £40.00
                Groceries: £20.00
                """;

        Assertions.assertEquals(expectedResults, testTransactions.totalAmountOutgoingPerCategory());
    }

    @Test
    void monthlyAverageSpendForAGivenCategoryTestNormal() {
        TransactionEntries testTransactions = new TransactionEntries(List.of(
                new TransactionEntry(LocalDate.of(1980, 1, 2), "Argos", Type.DIRECTDEBIT, new BigDecimal("25.00"), "Groceries"),
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Amazon", Type.CARD, new BigDecimal("20.00"), " "),
                new TransactionEntry(LocalDate.of(1980, 1, 1), "Sainsburys", Type.INTERNET, new BigDecimal("35.50"), "Groceries")
        ));

        String expectedResults = """
                The monthly average spend for the GROCERIES category is as follows:
                (Please note: if a specific time period is not listed, this means that there was never any transaction made in this month and year and therefore the average spend will be £0)
                JANUARY 1980: £30.25
                """;

        Assertions.assertEquals(expectedResults, testTransactions.monthlyAverageSpendForGivenCategory("Groceries"));
    }

    @Test
    void monthlyAverageSpendForAGivenCategoryTestEmptyCategory() {
        TransactionEntries testTransactions = new TransactionEntries(List.of(
                new TransactionEntry(LocalDate.of(1980, 1, 2), "Argos", Type.DIRECTDEBIT, new BigDecimal("25.00"), "Groceries"),
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Amazon", Type.CARD, new BigDecimal("20.00"), " "),
                new TransactionEntry(LocalDate.of(1980, 1, 1), "Sainsburys", Type.INTERNET, new BigDecimal("35.50"), "Groceries")
        ));

        String expectedResults = """
                The monthly average spend for the <UNDEFINED> category is as follows:
                (Please note: if a specific time period is not listed, this means that there was never any transaction made in this month and year and therefore the average spend will be £0)
                JANUARY 2022: £20.00
                """;

        Assertions.assertEquals(expectedResults, testTransactions.monthlyAverageSpendForGivenCategory(" "));
    }

    @Test
    void monthlyAverageSpendForAGivenCategoryTestNonExistent() {
        TransactionEntries testTransactions = new TransactionEntries(List.of(
                new TransactionEntry(LocalDate.of(1980, 1, 2), "Argos", Type.DIRECTDEBIT, new BigDecimal("25.00"), "Groceries"),
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Amazon", Type.CARD, new BigDecimal("20.00"), " "),
                new TransactionEntry(LocalDate.of(1980, 1, 1), "Sainsburys", Type.INTERNET, new BigDecimal("35.50"), "Groceries")
        ));

        String expectedResults = """
                No monthly average spend for the category THISCATEGORYDOESNTEXIST.
                """;

        Assertions.assertEquals(expectedResults, testTransactions.monthlyAverageSpendForGivenCategory("thiscategorydoesntexist"));
    }

    @Test
    void highestSpendingInAGivenCategoryForAGivenYearTestNormal() {
        TransactionEntries testTransactions = new TransactionEntries(List.of(
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Argos", Type.DIRECTDEBIT, new BigDecimal("25.00"), "Groceries"),
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Amazon", Type.CARD, new BigDecimal("20.00"), "Groceries"),
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Sainsburys", Type.INTERNET, new BigDecimal("35.50"), "Groceries"),
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Sainsburys", Type.INTERNET, new BigDecimal("49.00"), "MyMonthlyDD")
        ));

        String expectedResults = """
                The highest spend for the year 2022 in the category of GROCERIES was: £35.50
                The full transaction details are below:
                TRANSACTION DATE: 2022-01-01 | TRANSACTION VENDOR: Sainsburys | TRANSACTION TYPE: INTERNET | TRANSACTION AMOUNT: £35.50 | TRANSACTION CATEGORY: Groceries
                """;

        Assertions.assertEquals(expectedResults, testTransactions.highestOrLowestSpendingInAGivenCategoryForAGivenYear("highest","groceries", 2022));
    }

    @Test
    void lowestSpendingInAGivenCategoryForAGivenYearTestNormal() {
        TransactionEntries testTransactions = new TransactionEntries(List.of(
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Argos", Type.DIRECTDEBIT, new BigDecimal("25.00"), "Groceries"),
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Amazon", Type.CARD, new BigDecimal("20.00"), "Groceries"),
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Sainsburys", Type.INTERNET, new BigDecimal("35.50"), "Groceries"),
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Sainsburys", Type.INTERNET, new BigDecimal("49.00"), "MyMonthlyDD")
        ));

        String expectedResults = """
                The lowest spend for the year 2022 in the category of GROCERIES was: £20.00
                The full transaction details are below:
                TRANSACTION DATE: 2022-01-01 | TRANSACTION VENDOR: Amazon | TRANSACTION TYPE: CARD | TRANSACTION AMOUNT: £20.00 | TRANSACTION CATEGORY: Groceries
                """;

        Assertions.assertEquals(expectedResults, testTransactions.highestOrLowestSpendingInAGivenCategoryForAGivenYear("lowest","groceries", 2022));
    }

    @Test
    void lowestSpendingInAGivenCategoryForAGivenYearTestEmptyCategory() {
        TransactionEntries testTransactions = new TransactionEntries(List.of(
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Argos", Type.DIRECTDEBIT, new BigDecimal("25.00"), "Groceries"),
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Amazon", Type.CARD, new BigDecimal("20.00"), "Groceries"),
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Sainsburys", Type.INTERNET, new BigDecimal("35.50"), "Groceries"),
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Sainsburys", Type.INTERNET, new BigDecimal("49.00"), " ")
        ));

        String expectedResults = """
                The lowest spend for the year 2022 in the category of <UNDEFINED CATEGORY> was: £49.00
                The full transaction details are below:
                TRANSACTION DATE: 2022-01-01 | TRANSACTION VENDOR: Sainsburys | TRANSACTION TYPE: INTERNET | TRANSACTION AMOUNT: £49.00 | TRANSACTION CATEGORY: <Undefined>
                """;

        Assertions.assertEquals(expectedResults, testTransactions.highestOrLowestSpendingInAGivenCategoryForAGivenYear("lowest"," ", 2022));
    }

    @Test
    void spendingInAGivenCategoryForAGivenYearTestBadParameter() {
        TransactionEntries testTransactions = new TransactionEntries(List.of(
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Argos", Type.DIRECTDEBIT, new BigDecimal("25.00"), "Groceries"),
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Amazon", Type.CARD, new BigDecimal("20.00"), "Groceries"),
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Sainsburys", Type.INTERNET, new BigDecimal("35.50"), "Groceries"),
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Sainsburys", Type.INTERNET, new BigDecimal("49.00"), "MyMonthlyDD")
        ));

        String expectedResults = """
                Please only use parameter of "highest" or "lowest\"""";

        Assertions.assertEquals(expectedResults, testTransactions.highestOrLowestSpendingInAGivenCategoryForAGivenYear("thisshouldntexist","groceries", 2022));
    }

    @Test
    void highestOrLowestSpendingInAGivenCategoryForAGivenYearTestEmptyCategoryOrNoTransactionMadeForYear() {
        TransactionEntries testTransactions = new TransactionEntries(List.of(
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Argos", Type.DIRECTDEBIT, new BigDecimal("25.00"), "Groceries"),
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Amazon", Type.CARD, new BigDecimal("20.00"), "Groceries"),
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Sainsburys", Type.INTERNET, new BigDecimal("35.50"), "Groceries"),
                new TransactionEntry(LocalDate.of(2022, 1, 1), "Sainsburys", Type.INTERNET, new BigDecimal("49.00"), "MyMonthlyDD")
        ));

        String expectedResults = """
                NO TRANSACTIONS FOR THE GIVEN CATEGORY AND YEAR TO DISPLAY
                """;

        Assertions.assertEquals(expectedResults, testTransactions.highestOrLowestSpendingInAGivenCategoryForAGivenYear("highest","thisshouldntexist", 2021));
    }

    @Test
    void printingEmptyTransactionTest() {
        TransactionEntries emptyTransactions = new TransactionEntries(new ArrayList<TransactionEntry>());
        Assertions.assertEquals("NO TRANSACTIONS TO DISPLAY", emptyTransactions.toString());
    }
}
