package model;

public class Appointment {

        public int appointmentID;
        public String title;
        public String description;
        public String type;
        public String start;
        public String end;
        public int customerID;
        public int contactID;



    public Customer(int id, String name, String address, String zip, String phone, int division) {
            this.id = id;
            this.name = name;
            this.address = address;
            this.zip = zip;
            this.phone = phone;
            this.division = division;
        }
    }

}
