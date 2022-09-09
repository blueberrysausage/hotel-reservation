package model;

public class FreeRoom extends Room {
    private Double price;
    private boolean freeOrNot;

    public FreeRoom(String roomNumber, Double price, RoomType enumeration, boolean freeOrNot) {
        super (roomNumber, price, enumeration, freeOrNot);
        this.price = 0.0;
        this.freeOrNot = true;
    }

    @Override
    public String toString() {
        return "Room number: " + super.getRoomNumber() + ". Price: " + price + ". Room type: " + super.getRoomType() + ". " + freeOrNot;
    }
}
