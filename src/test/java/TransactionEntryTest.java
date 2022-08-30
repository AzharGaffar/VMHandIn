import Enums.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionEntryTest {

    @Test
    void printingTransactionEntryAllFieldsFilledOut(){
        TransactionEntry transactionEntry = new TransactionEntry(LocalDate.of(2000, 1, 1), "Sainsburys", Type.CARD, new BigDecimal("5.00"), "Groceries");
        Assertions.assertEquals("TRANSACTION DATE: 2000-01-01 | TRANSACTION VENDOR: Sainsburys | TRANSACTION TYPE: CARD | TRANSACTION AMOUNT: £5.00 | TRANSACTION CATEGORY: Groceries",transactionEntry.toString());
    }

    @Test
    void printingTransactionEntryCategoryFieldMissing(){
        TransactionEntry transactionEntry = new TransactionEntry(LocalDate.of(2000, 1, 1), "Sainsburys", Type.CARD, new BigDecimal("5.00"), " ");
        Assertions.assertEquals("TRANSACTION DATE: 2000-01-01 | TRANSACTION VENDOR: Sainsburys | TRANSACTION TYPE: CARD | TRANSACTION AMOUNT: £5.00 | TRANSACTION CATEGORY: <Undefined>",transactionEntry.toString());
    }
}
