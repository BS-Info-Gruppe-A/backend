package eu.bsinfo.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;
import java.util.UUID;

public class DefaultReading implements IReading{

    private UUID id;
    private String comment;
    private ICustomer customer;
    private LocalDate dateOfReading;
    private KindOfMeter kindOfMeter;
    private Double meterCount;
    private int meterId;
    private Boolean substitute;

    public DefaultReading(UUID id, String comment, ICustomer customer, LocalDate dateOfReading, KindOfMeter kindOfMeter, Double meterCount, int meterId, Boolean substitute) {
        this.id = id;
        this.comment = comment;
        this.customer = customer;
        this.dateOfReading = dateOfReading;
        this.kindOfMeter = kindOfMeter;
        this.meterCount = meterCount;
        this.meterId = meterId;
        this.substitute = substitute;
    }

    @Override
    public String getComment(){
        return comment;
        }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public ICustomer getCustomer() {
        return customer;
    }

    @Override
    public void setCustomer(ICustomer customer) {
        this.customer = customer;
    }

    @Override
    public LocalDate getDateOfReading() {
        return dateOfReading;
    }

    @Override
    public void setDateOfReading(LocalDate dateOfReading) {
        this.dateOfReading = dateOfReading;
    }

    @Override
    public KindOfMeter getKindOfMeter() {
        return kindOfMeter;
    }

    @Override
    public void setKindOfMeter(KindOfMeter kindOfMeter) {
        this.kindOfMeter = kindOfMeter;
    }

    @Override
    public Double getMeterCount() {
        return meterCount;
    }

    @Override
    public void setMeterCount(Double meterCount) {
        this.meterCount = meterCount;
    }

    @Override
    public int getMeterId() {
        return meterId;
    }

    @Override
    public void setMeterId(int meterId) {
        this.meterId = meterId;
    }

    @Override
    public Boolean getSubstitute() {
        return substitute;
    }

    @Override
    public String printDateOfReading() {
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(dateOfReading);
    }

    @Override
    public void setSubstitute(Boolean substitute) {
        this.substitute = substitute;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultReading that)) return false;
        return meterId == that.meterId && Objects.equals(id, that.id) && Objects.equals(comment, that.comment) && Objects.equals(customer, that.customer) && Objects.equals(dateOfReading, that.dateOfReading) && kindOfMeter == that.kindOfMeter && Objects.equals(meterCount, that.meterCount) && Objects.equals(substitute, that.substitute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, comment, customer, dateOfReading, kindOfMeter, meterCount, meterId, substitute);
    }

    @Override
    public String toString() {
        return "DefaultReading{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", customer=" + customer +
                ", dateOfReading=" + dateOfReading +
                ", kindOfMeter=" + kindOfMeter +
                ", meterCount=" + meterCount +
                ", meterId=" + meterId +
                ", substitute=" + substitute +
                '}';
    }
}
