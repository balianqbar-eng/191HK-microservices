package tw.yijiu.booking.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tw.yijiu.booking.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByCompanyId(Long companyId);
}
