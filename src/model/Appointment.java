package model;

/**
 * The Appointment class creates a "Appointment" object.
 *
 * @author Matt McHugh
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {

        public int appointmentID;
        public String title;
        public String description;
        public String type;
        public LocalDateTime localStartDateTime;
        public LocalDateTime localEndDateTime;
        public int customerID;
        public int userID;
        public String location;
        public String contactName;
        public String userName;
        public String customerName;
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

        public LocalDateTime getLocalStartDateTime() {
                return localStartDateTime;
        }

        public void setLocalStartDateTime(LocalDateTime localStartDateTime) {
                this.localStartDateTime = localStartDateTime;
        }

        public LocalDateTime getLocalEndDateTime() {
                return localEndDateTime;
        }

        public void setLocalEndDateTime(LocalDateTime localEndDateTime) {
                this.localEndDateTime = localEndDateTime;
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

        /**
         * This is the constructor for the appointment class. It creates a new instance of an appointment object.
         * @param appointmentID
         * @param title
         * @param description
         * @param localStartDateTime
         * @param localEndDateTime
         * @param customerID
         * @param contactID
         * @param location
         * @param type
         * @param userID
         * @param contactName
         * @param userName
         * @param customerName
         */
        public Appointment(int appointmentID, String title, String description, LocalDateTime localStartDateTime,
                           LocalDateTime localEndDateTime, int customerID, int contactID, String location, String type, int userID,
                           String contactName, String userName, String customerName) {

        this.title = title;
        this.appointmentID = appointmentID;
        this.description = description;
        this.type = type;
        this.localStartDateTime = localStartDateTime;
        this.localEndDateTime = localEndDateTime;
        this.customerID = customerID;
        this.contactID = contactID;
        this.location = location;
        this.userID = userID;
        this.contactName = contactName;
        this.userName = userName;
        this.customerName = customerName;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm MM/dd/yyyy");
        this.formattedLocalStartTimeString = localStartDateTime.format(formatter);
        this.formattedLocalEndTimeString = localEndDateTime.format(formatter);
        }
}
