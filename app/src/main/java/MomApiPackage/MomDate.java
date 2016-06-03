package MomApiPackage;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Robin on 03/06/2016.
 */
public class MomDate implements Comparable<MomDate>, Serializable{
    private static DateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);

    private Date date;

    public MomDate (String dateString) throws ParseException {
        date = formater.parse(dateString);
    }

    public void setDate(String dateString) throws ParseException {
        date = formater.parse(dateString);
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return formater.format(date);
    }

    @Override
    public int compareTo(MomDate another) {
        return date.compareTo(another.getDate());
    }
}
