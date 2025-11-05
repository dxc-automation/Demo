package data;

import java.io.IOException;
import java.text.ParseException;

import static utils.Excel.readFromExcel;

public class Constants {

    private String deviceUDID;
    private String deviceName;

    private String balanceBefore;
    private String balanceAfter;
    private String lastWinBefore;
    private String lastWinAfter;
    private String stake;


    public void setBalanceBefore(String balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public String getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceAfter(String balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public String getBalanceAfter() {
        return balanceAfter;
    }

    public void setLastWinBefore(String lastWinBefore) {
        this.lastWinBefore = lastWinBefore;
    }

    public String getLastWinBefore() {
        return lastWinBefore;
    }

    public void setLastWinAfter(String lastWinAfter) {
        this.lastWinAfter = lastWinAfter;
    }

    public String getLastWinAfter() {
        return lastWinAfter;
    }

    public void setStake(String stake) {
        this.stake = stake;
    }

    public String getStake() {
        return stake;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setDeviceUDID(String deviceUDID) {
        this.deviceUDID = deviceUDID;
    }

    public String getDeviceUDID() {
        return deviceUDID;
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
