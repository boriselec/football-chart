import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;

/**
 * User: boris
 * Date: 04.08.13
 * Time: 14:52
 */
public class RussianDateFormat extends DateFormat
{
    int changeYear = 2011;

    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);

        if (year < changeYear)
            return new StringBuffer().append(year);
        else
            return new StringBuffer().append(year).append("/").append(year + 1);
    }

    @Override
    public Date parse(String source, ParsePosition pos)
    {
        return new Date();
    }
}
