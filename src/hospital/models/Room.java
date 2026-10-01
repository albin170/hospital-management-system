package hospital.models;

/** Room/Ward model class */
public class Room {

    private int    roomId;
    private String roomNumber;
    private String roomType;    // General, ICU, Private
    private String ward;
    private String status;      // Available, Occupied
    private double pricePerDay;

    public Room() {}

    public Room(int roomId, String roomNumber, String roomType,
                String ward, String status, double pricePerDay) {
        this.roomId      = roomId;
        this.roomNumber  = roomNumber;
        this.roomType    = roomType;
        this.ward        = ward;
        this.status      = status;
        this.pricePerDay = pricePerDay;
    }

    public int    getRoomId()      { return roomId; }
    public String getRoomNumber()  { return roomNumber; }
    public String getRoomType()    { return roomType; }
    public String getWard()        { return ward; }
    public String getStatus()      { return status; }
    public double getPricePerDay() { return pricePerDay; }

    public void setRoomId(int id)             { this.roomId      = id; }
    public void setRoomNumber(String num)      { this.roomNumber  = num; }
    public void setRoomType(String type)       { this.roomType    = type; }
    public void setWard(String ward)           { this.ward        = ward; }
    public void setStatus(String status)       { this.status      = status; }
    public void setPricePerDay(double price)   { this.pricePerDay = price; }

    @Override
    public String toString() { return "Room " + roomNumber + " (" + roomType + ")"; }
}
