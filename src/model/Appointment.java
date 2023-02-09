package model;

import java.sql.Timestamp;

public class Appointment {

        public int appointmentID;
        public String title;
        public String description;
        public String type;
        public Timestamp start;
        public Timestamp end;
        public int customerID;

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

        public Timestamp getStart() {
                return start;
        }

        public void setStart(Timestamp start) {
                this.start = start;
        }

        public Timestamp getEnd() {
                return end;
        }

        public void setEnd(Timestamp end) {
                this.end = end;
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



    public Appointment(int appointmentID, String title, String description, String type, Timestamp start, Timestamp end, int customerID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.contactID = contactID;
    }
}
