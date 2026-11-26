package tw.yijiu.booking.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tw.yijiu.booking.domain.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerId(Long customerId);
}
