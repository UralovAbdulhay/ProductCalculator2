package sample.Classes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeHisobla {

    private LocalDateTime start_dateTime;
    LocalDateTime end_dateTime;


    public TimeHisobla(LocalDateTime start_dateTime, LocalDateTime end_dateTime) {
        this.start_dateTime = start_dateTime;
        this.end_dateTime = end_dateTime;
    }




    public LocalDateTime getStart_dateTime() {
        return start_dateTime;
    }

    public void setStart_dateTime(LocalDateTime start_dateTime) {
        this.start_dateTime = start_dateTime;
    }

    public LocalDateTime getEnd_dateTime() {
        return end_dateTime;
    }

    public void setEnd_dateTime(LocalDateTime end_dateTime) {
        this.end_dateTime = end_dateTime;
    }





    void ayir() {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        String start = "15.07.2020 21:00";
        System.out.println(start);

        LocalDateTime end_dateTime = LocalDateTime.parse(start, format);
        LocalDateTime start_dateTime = LocalDateTime.now();
        System.out.println("dateTime - > " + start_dateTime);
        System.out.println("currentDateTime -> " + end_dateTime);


        LocalDateTime dateTime = LocalDateTime.of(LocalDate.of(
                start_dateTime.minusYears(end_dateTime.getYear()).getYear(),
                start_dateTime.minusMonths(end_dateTime.getMonthValue()).getMonth(),
                start_dateTime.minusDays(end_dateTime.getDayOfMonth()).getDayOfMonth()
                ),
                LocalTime.of(start_dateTime.minusHours(end_dateTime.getHour()).getHour(),
                        start_dateTime.minusMinutes(end_dateTime.getMinute()).getMinute()
                )

        );

        System.out.println("Ayirma = " + dateTime);

    }


}
