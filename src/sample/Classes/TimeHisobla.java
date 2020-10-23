package sample.Classes;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        String start = "23.09.2020";
        System.out.println(start);

        LocalDate end_dateTime = LocalDate.parse(start, format);
        LocalDate start_dateTime = LocalDate.now();
        System.out.println("dateTime - > " + end_dateTime);
        System.out.println("currentDateTime -> " + start_dateTime);


        LocalDate dateTime = LocalDate.of(
                start_dateTime.minusYears(end_dateTime.getYear()).getYear(),
                start_dateTime.minusMonths(end_dateTime.getMonthValue()).getMonth(),
                start_dateTime.minusDays(end_dateTime.getDayOfMonth()).getDayOfMonth()

//                ,
//                LocalTime.of(start_dateTime.minusHours(end_dateTime.getHour()).getHour(),
//                        start_dateTime.minusMinutes(end_dateTime.getMinute()).getMinute()
//                )

        );

        final long days = ChronoUnit.DAYS.between(start_dateTime, end_dateTime);
//        final long days1 = ChronoUnit.HOURS.between(start_dateTime, end_dateTime);

//        System.out.println("soatlar farqi = " + days1);

        System.out.println("qo'shilgandan so'ng = " + start_dateTime.plusDays(days));

        System.out.println("long days = " + days);

        System.out.println("Ayirma = " + dateTime);

    }

    public void givenTwoDateTimesInJava8_whenDifferentiating_thenWeGetSix() {
        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime sixMinutesBehind = now.minusMinutes(6);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime sixMinutesBehind = LocalDateTime.parse("22.08.2020 01:22", format);

        Duration duration = Duration.between(now, sixMinutesBehind);
        System.out.println(Math.abs(duration.toMinutes()));
        long minutes = Math.abs(duration.toMinutes())%60;
        long hours = (Math.abs(duration.toHours())%24) ;
        long days = (Math.abs(duration.toDays()));

        System.out.println("diff = " + days + " / " + hours + ":" + minutes);
    }


}
