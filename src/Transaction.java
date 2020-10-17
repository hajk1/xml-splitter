import java.util.Objects;

public class Transaction {
    private double amount;
    private String transactionId;
    private String trackingCode;

    public Transaction(double amount, String transactionId, String trackingCode) {
        this.amount = amount;
        this.transactionId = transactionId;
        this.trackingCode = trackingCode;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(transactionId, that.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", transactionId='" + transactionId + '\'' +
                ", trackingCode='" + trackingCode + '\'' +
                '}';
    }
}
