package data;

import org.demo.Excel;

import java.io.IOException;
import java.text.ParseException;

public class Constants {


    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String mobile;
    private String birthDate;
    private String subject;
    private String firstHobby;
    private String secondHobby;
    private String address;
    private String state;
    private String city;


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setFirstHobby(String firstHobby) {
        this.firstHobby = firstHobby;
    }

    public String getFirstHobby() {
        return firstHobby;
    }

    public void setSecondHobby(String secondHobby) {
        this.secondHobby = secondHobby;
    }

    public String getSecondHobby() {
        return secondHobby;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }




    public void readTestData(String sheet, int row) throws IOException, ParseException {
        setFirstName(Excel.readFromExcel(sheet, row, 0).toString());
        setLastName(Excel.readFromExcel(sheet, row, 1).toString());
        setEmail(Excel.readFromExcel(sheet, row, 2).toString());
        setGender(Excel.readFromExcel(sheet, row, 3).toString());
        setMobile(Excel.readFromExcel(sheet, row, 4).toString());
        setBirthDate(Excel.readFromExcel(sheet, row, 5).toString());
        setSubject(Excel.readFromExcel(sheet, row, 6).toString());
        setFirstHobby(Excel.readFromExcel(sheet, row, 7).toString());
        setSecondHobby(Excel.readFromExcel(sheet, row, 8).toString());
        setAddress(Excel.readFromExcel(sheet, row, 9).toString());
        setState(Excel.readFromExcel(sheet, row, 10).toString());
        setCity(Excel.readFromExcel(sheet, row, 11).toString());
    }
}
