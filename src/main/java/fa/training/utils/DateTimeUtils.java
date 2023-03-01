package fa.training.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@Component
public class DateTimeUtils {
    //utils dung để các class sữ dụng nhiều lần
    public static Date fromStringToDate(String a){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        Date date = null;
        try {
            date = dateFormat.parse(a);
        } catch (ParseException pe) {
            System.err.println("Wrong format: " + a);
        }
        return date;
    }

    public static Date standardizationDate(Date date)  {
        try{
            return  new SimpleDateFormat("yyyy-MM-dd").parse(date.toString());
        } catch (ParseException pe) {
            return null;
        }
    }
    public static String fromDateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(date);
        return formattedDate;
    }
}
