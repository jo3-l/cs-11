package sample;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Friend {
    private String name;
    private String phone;
    private String email;
    private LocalDate birthday;

    public Friend(String name, String phone, String email, LocalDate birthday) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        if (birthday == null) return -1;
        return (int) ChronoUnit.YEARS.between(birthday, LocalDate.now());
    }

    public boolean isBirthdayToday() {
        if (birthday == null) return false;
        LocalDate now = LocalDate.now();
        return now.getMonth() == birthday.getMonth() && now.getDayOfMonth() == birthday.getDayOfMonth();
    }

    @Override
    public String toString() {
        return name;
    }
}