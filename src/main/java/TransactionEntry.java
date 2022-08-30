import Enums.Type;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class TransactionEntry {

    private LocalDate transactionDate;
    private String transactionVendor;
    private Type transactionType;
    private BigDecimal transactionAmount;
    private String transactionCategory;

    public TransactionEntry(LocalDate transactionDate, String vendor, Type transactionType, BigDecimal transactionAmount, String transactionCategory) {
        this.transactionDate = transactionDate;
        this.transactionVendor = vendor;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.transactionCategory = transactionCategory;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionVendor() {
        return transactionVendor;
    }

    public void setTransactionVendor(String transactionVendor) {
        this.transactionVendor = transactionVendor;
    }

    public Type getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Type transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(String transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    @Override
    public String toString() {
        if (!transactionCategory.equals(" ")) {
            return "TRANSACTION DATE: " + transactionDate + " | TRANSACTION VENDOR: " + transactionVendor + " | TRANSACTION TYPE: " + transactionType + " | TRANSACTION AMOUNT: £" + transactionAmount + " | TRANSACTION CATEGORY: " + transactionCategory;
        } else {
            return "TRANSACTION DATE: " + transactionDate + " | TRANSACTION VENDOR: " + transactionVendor + " | TRANSACTION TYPE: " + transactionType + " | TRANSACTION AMOUNT: £" + transactionAmount + " | TRANSACTION CATEGORY: <Undefined>";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionEntry that = (TransactionEntry) o;
        return Objects.equals(transactionDate, that.transactionDate) && Objects.equals(transactionVendor, that.transactionVendor) && transactionType == that.transactionType && Objects.equals(transactionAmount, that.transactionAmount) && Objects.equals(transactionCategory, that.transactionCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionDate, transactionVendor, transactionType, transactionAmount, transactionCategory);
    }
}
