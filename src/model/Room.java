package model;

public class Room implements IRoom {
    private String roomNumber;
    private Double price;
    private RoomType enumeration;
    //is freeOrNot needed?
    private boolean freeOrNot;

    public Room(String roomNumber, Double price, RoomType enumeration, boolean freeOrNot) {
        super();
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
        this.freeOrNot = freeOrNot;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return enumeration;
    }

    @Override
    public boolean isFree() {
        return freeOrNot;
    }

    @Override
    public String toString() {
        return "Room number: " + roomNumber + ". Price: " + price + ". Room type: " + enumeration + ".\n";
    }

}
