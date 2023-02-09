package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {

        public int appointmentID;
        public String title;
        public String description;
        public String type;
        public LocalDateTime utcStart;
        public LocalDateTime utcEnd;
        public int customerID;
        public int userID;
        public String location;
        public String contactName;
        public String userName;
        public String customerName;
        public LocalDateTime localStart;
        public LocalDateTime localEnd;
        public String formattedLocalStartTimeString;
        public String formattedLocalEndTimeString;

        public String getFormattedLocalStartTimeString() {
                return formattedLocalStartTimeString;
        }

        public void setFormattedLocalStartTimeString(String formattedLocalStartTimeString) {
                this.formattedLocalStartTimeString = formattedLocalStartTimeString;
        }

        public String getFormattedLocalEndTimeString() {
                return formattedLocalEndTimeString;
        }

        public void setFormattedLocalEndTimeString(String formattedLocalEndTimeString) {
                this.formattedLocalEndTimeString = formattedLocalEndTimeString;
        }

        public LocalDateTime getLocalStart() {
                return localStart;
        }

        public void setLocalStart(LocalDateTime localStart) {
                this.localStart = localStart;
        }

        public LocalDateTime getLocalEnd() {
                return localEnd;
        }

        public void setLocalEnd(LocalDateTime localEnd) {
                this.localEnd = localEnd;
        }

        public String getUserName() {
                return userName;
        }

        public void setUserName(String userName) {
                this.userName = userName;
        }

        public String getCustomerName() {
                return customerName;
        }

        public void setCustomerName(String customerName) {
                this.customerName = customerName;
        }

        public int getUserID() {
                return userID;
        }

        public void setUserID(int userID) {
                this.userID = userID;
        }

        public String getLocation() {
                return location;
        }

        public void setLocation(String location) {
                this.location = location;
        }

        public int getAppointmentID() {
                return appointmentID;
        }

        public void setAppointmentID(int appointmentID) {
                this.appointmentID = appointmentID;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public String getType() {
                return type;
        }

        public void setType(String type) {
                this.type = type;
        }

        public LocalDateTime getUtcStart() {
                return utcStart;
        }

        public void setUtcStart(LocalDateTime utcStart) {
                this.utcStart = utcStart;
        }

        public LocalDateTime getUtcEnd() {
                return utcEnd;
        }

        public void setUtcEnd(LocalDateTime utcEnd) {
                this.utcEnd = utcEnd;
        }

        public int getCustomerID() {
                return customerID;
        }

        public void setCustomerID(int customerID) {
                this.customerID = customerID;
        }

        public int getContactID() {
                return contactID;
        }

        public void setContactID(int contactID) {
                this.contactID = contactID;
        }

        public int contactID;


        public String getContactName() {
                return contactName;
        }

        public void setContactName(String contactName) {
                this.contactName = contactName;
        }

        public Appointment(int appointmentID, String title, String description, LocalDateTime utcStart,
                           LocalDateTime utcEnd, int customerID, int contactID, String location, String type, int userID,
                           String contactName, String userName, String customerName, LocalDateTime localStart, LocalDateTime localEnd) {

        this.title = title;
        this.appointmentID = appointmentID;
        this.description = description;
        this.type = type;
        this.utcStart = utcStart;
        this.utcEnd = utcEnd;
        this.customerID = customerID;
        this.contactID = contactID;
        this.location = location;
        this.userID = userID;
        this.contactName = contactName;
        this.userName = userName;
        this.customerName = customerName;
        this.localStart = localStart;
        this.localEnd = localEnd;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm MM/dd/yyyy");
        this.formattedLocalStartTimeString = localStart.format(formatter);
        this.formattedLocalEndTimeString = localEnd.format(formatter);
        System.out.println(this.formattedLocalStartTimeString);
        System.out.println();
        }
}
