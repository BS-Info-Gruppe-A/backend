package eu.bsinfo.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

@JsonDeserialize(as = DefaultReading.class)
public interface IReading extends IId {

    /// Creates a new [IReading] from a [ResultSet]
    ///
    /// @param resultSet the [ResultSet] containing the data
    /// @return the newly created readings
    /// @throws SQLException if an SQL error occurs
    static IReading from(ResultSet resultSet) throws SQLException {
        var id = resultSet.getObject("id", UUID.class);
        var comment = resultSet.getString("comment");
        var customer = resultSet.getObject("customer_id") == null ? null : ICustomer.from(resultSet, "customer_id");
        var dateOfReading = resultSet.getTimestamp("read_date").toLocalDateTime().toLocalDate();
        var kindOfMeter = resultSet.getString("meter_type");
        var meterCount = resultSet.getDouble("meter_count");
        var meterId = resultSet.getInt("meter_id");
        var substitute = resultSet.getBoolean("substitute");

        return new DefaultReading(id, comment, customer, dateOfReading, KindOfMeter.valueOf(kindOfMeter), meterCount, meterId, substitute);
    }

    String getComment();

    void setComment(String comment);

    ICustomer getCustomer();

    void setCustomer(ICustomer customer);

    LocalDate getDateOfReading();

    void setDateOfReading(LocalDate dateOfReading);

    KindOfMeter getKindOfMeter();

    void setKindOfMeter(KindOfMeter kindOfMeter);

    Double getMeterCount();

    void setMeterCount(Double meterCount);

    int getMeterId();

    void setMeterId(int meterId);

    Boolean getSubstitute();

    void setSubstitute(Boolean substitute);

    String printDateOfReading();


    enum KindOfMeter {
        HEIZUNG, STROM, UNBEKANNT, WASSER
    }
}