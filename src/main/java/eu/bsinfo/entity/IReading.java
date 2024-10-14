package eu.bsinfo.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

public interface IReading extends IId {

    enum KindOfMeter {
        HEIZUNG, STROM, UNBEKANNT, WASSER;
    }

    String getComment();

    ICustomer getCustomer();

    LocalDate getDateOfReading();

    KindOfMeter getKindOfMeter();

    Double getMeterCount();

    String getMeterId();

    Boolean getSubstitute();

    String printDateOfReading();

    void setComment(String comment);

    void setCustomer(ICustomer customer);

    void setDateOfReading(LocalDate dateOfReading);

    void setKindOfMeter(KindOfMeter kindOfMeter);

    void setMeterCount(Double meterCount);

    void setMeterId(String meterId);

    void setSubstitute(Boolean substitute);


    /**
     * Creates a new {@link IReading} from a {@link ResultSet}
     *
     * @param resultSet the {@link ResultSet} containing the data
     * @return the newly created readings
     * @throws SQLException if an SQL error occurs
     */
    static IReading from(ResultSet resultSet) throws SQLException {
        var comment = resultSet.getString("comment");
        var customer = resultSet.getObject("customer");
        var dateOfReading = resultSet.getObject("dateOfReading");
        var kindOfMeter = resultSet.getObject("kindOfMeter");
        var meterCount = resultSet.getDouble("meterCount");
        var meterId = resultSet.getString("meterId");
        var substitute = resultSet.getBoolean("substitute");
        var printDateOfReading = resultSet.getString("printDateOfReading");

        //*must be implemented
        //return new DefaultReading(comment, customer, dateOfReading, kindOfMeter, meterCount, meterId, substitute, printDateOfReading);
        return null;
    }
}