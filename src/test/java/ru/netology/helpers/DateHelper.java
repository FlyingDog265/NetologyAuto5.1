package ru.netology.helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateHelper {

    public static String getDate(){
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String getShiftedDate(int daysToShift){
        return LocalDate.now().plusDays(daysToShift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
