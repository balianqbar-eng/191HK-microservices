package tw.yijiu.dispatch.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tw.yijiu.dispatch.domain.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByCompanyId(Long companyId);
}
