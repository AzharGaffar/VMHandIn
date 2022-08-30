import Enums.Type;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class TransactionFileReader {

    public String readFile(String filePath) throws IOException {
        String fileResult = "";
        try {
            fileResult = Files.readString(Path.of(filePath));
        } catch (NoSuchFileException e) {
            throw new NoSuchFileException("File has wrong path.");
        }

        if (fileResult.equals("")) {
            throw new IOException("This file is empty.");
        }

        return fileResult;
    }

    public TransactionEntries createListOfTransactionsFromFile(String filePath) throws IOException {
        TransactionEntries transactionList = new TransactionEntries(new ArrayList<>());
        readFile(filePath).strip().lines().forEach(transaction -> {
            transaction = transaction.strip();

            // Skipping empty lines between transactions if there are any.
            if (transaction.equals("")) {
                return;
            }

            LocalDate transactionDate;
            try {
                // US Locale recognises Sep instead of Sept
                DateTimeFormatter formatOfDate = DateTimeFormatter.ofPattern("dd/MMM/yyyy").withLocale(Locale.US);
                transactionDate = LocalDate.parse(transaction.substring(0, 11), formatOfDate);
            } catch (Exception e) {
                throw new IllegalStateException("Date could not be correctly converted. It may not be in the correct format.");
            }

            Type transactionType;
            if (transaction.contains("card")) {
                transactionType = Type.CARD;
            } else if (transaction.contains("direct debit")) {
                transactionType = Type.DIRECTDEBIT;
            } else if (transaction.contains("internet")) {
                transactionType = Type.INTERNET;
            } else {
                throw new IllegalStateException("Invalid Transaction Type encountered.");
            }

            String transactionVendor = transaction.substring(11, transaction.indexOf(transactionType.toString().toLowerCase().replace("directdebit", "direct debit"))).strip();

            String transactionCategory = transaction.substring(transaction.lastIndexOf(" ")).strip();
            if (transactionCategory.contains("£")) {
                transactionCategory = " ";
            }

            BigDecimal transactionAmount;
            try {
                if (transactionCategory.equals(" ")) {
                    transactionAmount = new BigDecimal(transaction.substring(transaction.indexOf("£") + 1));
                } else {
                    transactionAmount = new BigDecimal(transaction.substring(transaction.indexOf("£") + 1, transaction.lastIndexOf(" ")));
                }
                transactionAmount = transactionAmount.setScale(2,RoundingMode.HALF_EVEN);
            } catch (Exception e) {
                throw new IllegalStateException("Your transaction needs to contain any amount in £ .");
            }

            transactionList.addTransaction(new TransactionEntry(transactionDate, transactionVendor, transactionType, transactionAmount, transactionCategory));
        });

        return transactionList;
    }
}
