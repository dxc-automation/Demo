package data;

import java.io.IOException;
import java.text.ParseException;

import static utils.Excel.readFromExcel;

public class Constants {

    private String deviceUDID;


    //***   INSTAGRAM
    private String url;
    private String username;
    private String password;

    public void setDeviceUDID(String deviceUDID) {
        this.deviceUDID = deviceUDID;
    }

    public String getDeviceUDID() {
        return deviceUDID;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void readInstagramData(int row) throws IOException, ParseException {
        setUrl(readFromExcel("Instagram", row, 0).toString());
        setUsername(readFromExcel("Instagram", row, 1).toString());
        setPassword(readFromExcel("Instagram", row, 2).toString());
    }


    //***   REGISTRATION

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
        setFirstName(readFromExcel(sheet, row, 0).toString());
        setLastName(readFromExcel(sheet, row, 1).toString());
        setEmail(readFromExcel(sheet, row, 2).toString());
        setGender(readFromExcel(sheet, row, 3).toString());
        setMobile(readFromExcel(sheet, row, 4).toString());
        setBirthDate(readFromExcel(sheet, row, 5).toString());
        setSubject(readFromExcel(sheet, row, 6).toString());
        setFirstHobby(readFromExcel(sheet, row, 7).toString());
        setSecondHobby(readFromExcel(sheet, row, 8).toString());
        setAddress(readFromExcel(sheet, row, 9).toString());
        setState(readFromExcel(sheet, row, 10).toString());
        setCity(readFromExcel(sheet, row, 11).toString());
    }
}
