package  sample.Moodles;

import java.time.LocalDate;

public class Client {
    private int id;
    private String name;
    private LocalDate date;

    public Client(int id, String name, LocalDate date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }


    public Client( String name) {
        this.id = -1;
        this.name = name;
        this.date = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


//        99 004 26 77 Mahalla raisi
    // 90 910 71 22,   71 237 32 06   Dildora opa fond


    @Override
    public String toString() {
        return "\nClient{" +
                "\nid=" + id +
                ",\n name='" + name + '\'' +
                ",\n date=" + date.getYear() + "-" + date.getMonthValue() + "-" + date.getDayOfMonth() +
                "\n}";
    }
}
