package com.example.bytecrunch.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class dateValue {

    private String dateToday, dateYesterday, date3DaysAgo, date7DaysAgo;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public void dateValue() {
        // Get today's date
        LocalDate dateNow = LocalDate.now();
        dateToday = dateNow.format(formatter);
        dateYesterday = dateNow.minusDays(1).format((formatter));
        date3DaysAgo = dateNow.minusDays(3).format((formatter));
        date7DaysAgo = dateNow.minusDays(7).format((formatter));
    }

    public String getDateToday() {
        return dateToday;
    }

    public String getDateYesterday() {
        return dateYesterday;
    }

    public String getDate3DaysAgo() {
        return date3DaysAgo;
    }

    public String getDate7DaysAgo() {
        return date7DaysAgo;
    }
}
