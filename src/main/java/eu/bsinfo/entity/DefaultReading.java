package eu.bsinfo.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;
import java.util.UUID;

public class DefaultReading implements IReading {

    @Nullable
    @JsonProperty(required = true)
    private UUID id;
    @NotNull
    @JsonProperty(required = true)
    private String comment;
    @Nullable
    @JsonProperty(required = true)
    private ICustomer customer;
    @NotNull
    @JsonProperty(required = true)
    private LocalDate dateOfReading;
    @NotNull
    @JsonProperty(required = true)
    private KindOfMeter kindOfMeter;
    @NotNull
    @JsonProperty(required = true)
    private Double meterCount;
    @JsonProperty(required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private int meterId;
    @NotNull
    @JsonProperty(required = true)
    private Boolean substitute;

    @JsonCreator
    public DefaultReading(@JsonProperty(value = "id", required = true) @Nullable UUID id,
                          @JsonProperty("comment") @NotNull String comment,
                          @JsonProperty(value = "customer", required = true) @Nullable ICustomer customer,
                          @JsonProperty(value = "dateOfReading", required = true) @NotNull LocalDate dateOfReading,
                          @JsonProperty(value = "kindOfMeter", required = true) @NotNull KindOfMeter kindOfMeter,
                          @JsonProperty(value = "meterCount", required = true) @NotNull Double meterCount,
                          @JsonProperty(value = "meterId", required = true) int meterId,
                          @JsonProperty(value = "substitute", required = true) @NotNull Boolean substitute
    ) {
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
    public @NotNull String getComment() {
        return comment;
    }

    @Override
    public void setComment(@NotNull String comment) {
        this.comment = comment;
    }

    @Nullable
    @Override
    public ICustomer getCustomer() {
        return customer;
    }

    @Override
    public void setCustomer(@NotNull ICustomer customer) {
        this.customer = customer;
    }

    @Override
    public @NotNull LocalDate getDateOfReading() {
        return dateOfReading;
    }

    @Override
    public void setDateOfReading(@NotNull LocalDate dateOfReading) {
        this.dateOfReading = dateOfReading;
    }

    @Override
    public @NotNull KindOfMeter getKindOfMeter() {
        return kindOfMeter;
    }

    @Override
    public void setKindOfMeter(@NotNull KindOfMeter kindOfMeter) {
        this.kindOfMeter = kindOfMeter;
    }

    @Override
    public @NotNull Double getMeterCount() {
        return meterCount;
    }

    @Override
    public void setMeterCount(@NotNull Double meterCount) {
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
    public @NotNull Boolean getSubstitute() {
        return substitute;
    }

    @Override
    public void setSubstitute(@NotNull Boolean substitute) {
        this.substitute = substitute;
    }

    @Override
    public String printDateOfReading() {
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(dateOfReading);
    }

    @Override
    public @Nullable UUID getId() {
        return id;
    }

    @Override
    public void setId(@Nullable UUID id) {
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
