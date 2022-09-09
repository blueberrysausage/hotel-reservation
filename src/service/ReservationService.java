package service;

import model.*;

import java.util.*;

public class ReservationService {
    private static final Map<String, IRoom> mapOfRoom = new HashMap<>();
    private static final Set<Reservation> reservations = new HashSet<>();
    private static final Set<IRoom> availableRooms = new HashSet<>();
    private static final Set<IRoom> allRooms = new HashSet<>();

    public void addRoom(IRoom room) {
        mapOfRoom.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomID) {
        System.out.println("getARoom:"+mapOfRoom);
        return mapOfRoom.get(roomID);
    }

    public Collection<IRoom> getAllRooms() {
        System.out.println("getALLRooms: "+mapOfRoom);
        allRooms.addAll(mapOfRoom.values());
        return allRooms;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        //System.out.println(mapOfRoom);
        return reservation;
    }

    public Reservation reserveAltRoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Calendar checkInPlusSeven = Calendar.getInstance();
        checkInPlusSeven.setTime(checkInDate);
        checkInPlusSeven.add(Calendar.DATE, 7);
        Calendar checkOutPlusSeven = Calendar.getInstance();
        checkOutPlusSeven.setTime(checkOutDate);
        checkOutPlusSeven.add(Calendar.DATE, 7);
        Reservation reservation = new Reservation(customer, room, checkInPlusSeven.getTime(), checkOutPlusSeven.getTime());
        System.out.println(reservation);
        //System.out.println("room: " + room);
        reservations.add(reservation);
        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        availableRooms.clear();
        for (Reservation reservation: reservations) {
            if (!(checkInDate.before(reservation.getCheckOutDate()) && checkOutDate.after(reservation.getCheckInDate()))) {
                availableRooms.add(reservation.getRoom());
                //System.out.println("availableRooms if: "+availableRooms);
            }
        }
        Collection<IRoom> rooms = mapOfRoom.values();
        //System.out.println("mapOfRoom: " + rooms);
        for (Reservation reservation : reservations) {
            rooms.remove(reservation.getRoom());
        }
        //System.out.println("rooms: " + rooms);
        availableRooms.addAll(rooms);
        //System.out.println("availableRooms: reservationservice" + availableRooms);
        return availableRooms;
    }

    public Collection<IRoom> findAltRooms(Date checkInDate, Date checkOutDate) {
        availableRooms.clear();
        Calendar checkInPlusSeven = Calendar.getInstance();
        checkInPlusSeven.setTime(checkInDate);
        checkInPlusSeven.add(Calendar.DATE, 7);
        Calendar checkOutPlusSeven = Calendar.getInstance();
        checkOutPlusSeven.setTime(checkOutDate);
        checkOutPlusSeven.add(Calendar.DATE, 7);
        for (Reservation reservation: reservations) {
            if (checkInPlusSeven.before(reservation.getCheckOutDate()) && checkOutPlusSeven.after(reservation.getCheckInDate())) {
                return null;
            } else {
                availableRooms.add(reservation.getRoom());
            }
        }
        return availableRooms;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        Set<Reservation> customersReservation = new HashSet<>();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().equals(customer)) {
                customersReservation.add(reservation);
            }
        }
        return customersReservation;
    }

    public void printAllReservation() {
        if (reservations.isEmpty()) {
            System.out.println("No reservation found.");
        }
        else {
            for (Reservation allReservation : reservations) {
                System.out.println(allReservation);
            }
        }
    }
}
