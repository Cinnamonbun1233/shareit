package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long>, QuerydslPredicateExecutor<Booking> {

    Optional<Booking> findFirstByBookerIdAndItemIdAndEndDateBefore(Long bookerId, Long itemId, LocalDateTime cur);

    List<Booking> findAllByItemIdIn(List<Long> ids);

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN b.item AS i " +
            "JOIN b.booker AS bk " +
            "WHERE b.id = :bookingId " +
            "AND (bk.id = :userId OR i.owner.id = :userId)")
    Optional<Booking> findBooking(@Param("bookingId") Long bookingId, @Param("userId") Long userId);

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN b.item AS i " +
            "JOIN b.booker AS bk " +
            "WHERE b.id = :bookingId AND i.owner.id = :ownerId")
    Optional<Booking> findBookingByOwner(@Param("bookingId") Long bookingId, @Param("ownerId") Long ownerId);

    @Query(value = "SELECT * " +
            "FROM bookings AS bk " +
            "JOIN items AS i ON bk.item_id=i.id " +
            "JOIN users AS u ON bk.booker_id=u.id " +
            "WHERE bk.item_id=(:id) " +
            "AND bk.start_date <= :now " +
            "AND bk.status NOT IN('REJECTED', 'CANCELLED') " +
            "ORDER BY bk.start_date DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<Booking> findLastBookingByItemId(@Param("id") Long id, @Param("now") LocalDateTime now);

    @Query(value = "SELECT * " +
            "FROM bookings AS bk " +
            "JOIN items as i ON bk.item_id=i.id " +
            "JOIN users as u ON bk.booker_id=u.id " +
            "WHERE bk.item_id=(:id) " +
            "AND bk.start_date > :now " +
            "AND bk.status NOT IN('REJECTED', 'CANCELLED') " +
            "ORDER BY bk.start_date ASC " +
            "LIMIT 1", nativeQuery = true)
    Optional<Booking> findNextBookingByItemId(@Param("id") Long id, @Param("now") LocalDateTime now);
}