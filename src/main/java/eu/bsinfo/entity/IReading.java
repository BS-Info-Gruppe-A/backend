package eu.bsinfo.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

@JsonDeserialize(as = DefaultReading.class)
public interface IReading extends IId {

    enum KindOfMeter {
        HEIZUNG, STROM, UNBEKANNT, WASSER
    }

    String getComment();

    ICustomer getCustomer();

    LocalDate getDateOfReading();

    KindOfMeter getKindOfMeter();

    Double getMeterCount();

    int getMeterId();

    Boolean getSubstitute();

    String printDateOfReading();

    void setComment(String comment);

    void setCustomer(ICustomer customer);

    void setDateOfReading(LocalDate dateOfReading);

    void setKindOfMeter(KindOfMeter kindOfMeter);

    void setMeterCount(Double meterCount);

    void setMeterId(int meterId);

    void setSubstitute(Boolean substitute);


    /// Creates a new [IReading] from a [ResultSet]
    ///
    /// @param resultSet the [ResultSet] containing the data
    /// @return the newly created readings
    /// @throws SQLException if an SQL error occurs
    static IReading from(ResultSet resultSet) throws SQLException {
        var id = resultSet.getObject("id", UUID.class);
        var comment = resultSet.getString("comment");
        var customer = ICustomer.from(resultSet, "customer_id");
        var dateOfReading = resultSet.getTimestamp("read_date").toLocalDateTime().toLocalDate();
        var kindOfMeter = resultSet.getString("meter_type");
        var meterCount = resultSet.getDouble("meter_count");
        var meterId = resultSet.getInt("meter_id");
        var substitute = resultSet.getBoolean("substitute");

        return new DefaultReading(id, comment, customer, dateOfReading, KindOfMeter.valueOf(kindOfMeter), meterCount, meterId, substitute);
    }
}