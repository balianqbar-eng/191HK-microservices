package tw.yijiu.booking;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import tw.yijiu.booking.domain.Booking;
import tw.yijiu.booking.domain.Customer;
import tw.yijiu.booking.domain.ServiceType;
import tw.yijiu.booking.repo.BookingRepository;
import tw.yijiu.booking.repo.CustomerRepository;

@DataJpaTest
@TestPropertySource(properties = "spring.sql.init.mode=never")
class BookingRepositoryTest {

    @Autowired TestEntityManager em;
    @Autowired CustomerRepository customers;
    @Autowired BookingRepository bookings;

    @Test
    void customerKeepsAddressesAndBookings() {
        Customer c = new Customer(1L, "王小姐", "0912000001");
        var addr = c.addAddress("台北市", "大安區復興南路一段 100 號", 28);
        em.persist(c);
        em.persist(new Booking(c, addr, ServiceType.MOLD_REMOVAL, LocalDateTime.of(2026, 12, 1, 9, 0)));
        em.flush();
        em.clear();

        var found = bookings.findByCustomerId(c.getId());
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getAddress().getPings()).isEqualTo(28);
        assertThat(found.get(0).getCompanyId()).isEqualTo(1L);
    }

    @Test
    void customersAreScopedByCompany() {
        em.persist(new Customer(1L, "A 公司客人", null));
        em.persist(new Customer(2L, "B 公司客人", null));
        em.flush();

        assertThat(customers.findByCompanyId(1L)).extracting(Customer::getName).containsExactly("A 公司客人");
    }
}
