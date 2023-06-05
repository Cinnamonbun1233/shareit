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
    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN  b.item AS i " +
            "JOIN  b.booker AS bk " +
            "WHERE b.id = ?1 " +
            "AND (bk.id = ?2 OR i.owner.id = ?2)")
    Optional<Booking> findBooking(Long bookingId, Long userId);

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN b.item AS i " +
            "JOIN b.booker AS bk " +
            "WHERE b.id = ?1 " +
            "AND i.owner.id = ?2")
    Optional<Booking> findBookingByOwner(Long bookingId, Long ownerId);

    Optional<Booking> findFirstByBookerIdAndItemIdAndEndDateBefore(Long bookerId, Long itemId, LocalDateTime cur);

    @Query(value = "SELECT * " +
            "FROM bookings AS bk " +
            "JOIN items AS i ON bk.item_id=i.id " +
            "JOIN users AS u ON bk.booker_id=u.id " +
            "WHERE bk.item_id=(:id) " +
            "AND bk.start_date <= :cur " +
            "AND bk.status NOT IN('REJECTED', 'CANCELLED') " +
            "ORDER BY bk.start_date DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<Booking> findLastBookingByItemId(@Param("id") Long id, @Param("cur") LocalDateTime cur);

    @Query(value = "SELECT * " +
            "FROM bookings AS bk " +
            "JOIN items AS i ON bk.item_id=i.id " +
            "JOIN users AS u ON bk.booker_id=u.id " +
            "WHERE bk.item_id=(:id) " +
            "AND bk.start_date > :cur " +
            "AND bk.status NOT IN('REJECTED', 'CANCELLED') " +
            "ORDER BY bk.start_date ASC " +
            "LIMIT 1", nativeQuery = true)
    Optional<Booking> findNextBookingByItemId(@Param("id") Long id, @Param("cur") LocalDateTime cur);

    List<Booking> findAllByItemIdIn(List<Long> ids);
}