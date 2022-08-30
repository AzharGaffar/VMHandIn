import Enums.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.NoSuchFileException;
import java.time.LocalDate;
import java.util.List;

public class TransactionFileReaderTest {

    TransactionFileReader transactionFileReader = new TransactionFileReader();

    @Test
    void readCorrectResultFromContentsFile() throws IOException {
        String methodResult = transactionFileReader.readFile("src/test/java/TransactionFileReaderTestFiles/ContentsFiles/correctContentsFile.txt");
        String correctResult = "this\r\nshould\r\nbe read\r\nproperly";
        Assertions.assertEquals(correctResult, methodResult);
    }

    @Test
    void readResultFromWrongPath() {
        Assertions.assertThrows(NoSuchFileException.class,
                () -> {
                    transactionFileReader.readFile("wrongPath.txt");
                });
    }

    @Test
    void readResultFromEmptyFile() {
        Assertions.assertThrows(IOException.class,
                () -> {
                    transactionFileReader.readFile("src/test/java/FileReaderTestFiles/ContentsFiles/emptyContentsFile.txt");
                });
    }

    @Test
    void readFileAndConvertToTransactionObjectNormal() throws IOException {
        TransactionEntry transaction = new TransactionEntry(LocalDate.of(2022,8,21),"Amazon", Type.INTERNET, new BigDecimal("20.00"),"RandomSpending");
        TransactionEntries correctResult = new TransactionEntries(List.of(transaction));
        Assertions.assertEquals(correctResult, transactionFileReader.createListOfTransactionsFromFile("src/test/java/TransactionFileReaderTestFiles/TestTransactions/sampleTransactionNormal.txt"));
    }

    @Test
    void readFileAndConvertToTransactionObjectEmptyCategory() throws IOException {
        TransactionEntry transaction = new TransactionEntry(LocalDate.of(2022,8,21),"Amazon", Type.INTERNET, new BigDecimal("20.00")," ");
        TransactionEntries correctResult = new TransactionEntries(List.of(transaction));
        Assertions.assertEquals(correctResult, transactionFileReader.createListOfTransactionsFromFile("src/test/java/TransactionFileReaderTestFiles/TestTransactions/sampleTransactionEmptyCategory.txt"));
    }

    @Test
    void readFileAndConvertToTransactionObjectMultiWordVendor() throws IOException {
        TransactionEntry transaction = new TransactionEntry(LocalDate.of(2022,8,21),"Amazon Prime Video", Type.INTERNET, new BigDecimal("20.00"),"RandomSpending");
        TransactionEntries correctResult = new TransactionEntries(List.of(transaction));
        Assertions.assertEquals(correctResult, transactionFileReader.createListOfTransactionsFromFile("src/test/java/TransactionFileReaderTestFiles/TestTransactions/sampleTransactionMultiWordVendor.txt"));
    }

    @Test
    void readFileAndConvertToTransactionObjectEmptyLinesBetween() throws IOException {
        TransactionEntry transaction = new TransactionEntry(LocalDate.of(2022,8,21),"Amazon", Type.INTERNET, new BigDecimal("20.00"),"RandomSpending");
        TransactionEntry anotherTransaction = new TransactionEntry(LocalDate.of(2022,8,21),"Amazon Prime Video", Type.INTERNET, new BigDecimal("30.00"),"RandomSpending");
        TransactionEntries correctResult = new TransactionEntries(List.of(transaction,anotherTransaction));
        Assertions.assertEquals(correctResult, transactionFileReader.createListOfTransactionsFromFile("src/test/java/TransactionFileReaderTestFiles/TestTransactions/sampleTransactionEmptyLinesBetween.txt"));
    }

    @Test
    void errorThrownBadDate() {
        Assertions.assertThrows(IllegalStateException.class,
                () -> {
                    transactionFileReader.createListOfTransactionsFromFile("src/test/java/TransactionFileReaderTestFiles/BadTransactions/badDate.txt");
                });
    }

    @Test
    void errorThrownBadTransactionType() {
        Assertions.assertThrows(IllegalStateException.class,
                () -> {
                    transactionFileReader.createListOfTransactionsFromFile("src/test/java/TransactionFileReaderTestFiles/BadTransactions/badTransactionType.txt");
                });
    }

    @Test
    void errorThrownNoAmountGiven() {
        Assertions.assertThrows(IllegalStateException.class,
                () -> {
                    transactionFileReader.createListOfTransactionsFromFile("src/test/java/TransactionFileReaderTestFiles/BadTransactions/noAmountGiven.txt");
                });
    }
}
