package tw.yijiu.dispatch.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.yijiu.dispatch.domain.Technician;

public interface TechnicianRepository extends JpaRepository<Technician, Long> {
}
