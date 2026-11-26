package tw.yijiu.booking.web;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tw.yijiu.booking.domain.Booking;
import tw.yijiu.booking.domain.Customer;
import tw.yijiu.booking.repo.BookingRepository;
import tw.yijiu.booking.repo.CustomerRepository;

@RestController
@RequestMapping("/api")
@Transactional(readOnly = true)
public class CustomerController {

    private final CustomerRepository customers;
    private final BookingRepository bookings;

    public CustomerController(CustomerRepository customers, BookingRepository bookings) {
        this.customers = customers;
        this.bookings = bookings;
    }

    @GetMapping("/companies/{companyId}/customers")
    public List<CustomerView> listCustomers(@PathVariable Long companyId) {
        return customers.findByCompanyId(companyId).stream().map(CustomerView::of).toList();
    }

    @GetMapping("/customers/{customerId}/bookings")
    public List<BookingView> listBookings(@PathVariable Long customerId) {
        return bookings.findByCustomerId(customerId).stream().map(BookingView::of).toList();
    }

    public record CustomerView(Long id, String name, String phone, int addressCount) {
        static CustomerView of(Customer c) {
            return new CustomerView(c.getId(), c.getName(), c.getPhone(), c.getAddresses().size());
        }
    }

    public record BookingView(Long id, String serviceType, String address, Integer pings,
                              LocalDateTime preferredAt, String status) {
        static BookingView of(Booking b) {
            var a = b.getAddress();
            return new BookingView(b.getId(), b.getServiceType().name(), a.getCity() + a.getLine(),
                    a.getPings(), b.getPreferredAt(), b.getStatus().name());
        }
    }
}
