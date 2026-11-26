package tw.yijiu.dispatch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import tw.yijiu.dispatch.domain.Job;
import tw.yijiu.dispatch.domain.JobStatus;
import tw.yijiu.dispatch.domain.Technician;
import tw.yijiu.dispatch.repo.JobRepository;

@DataJpaTest
@TestPropertySource(properties = "spring.sql.init.mode=never")
class JobRepositoryTest {

    @Autowired TestEntityManager em;
    @Autowired JobRepository jobs;

    @Test
    void reassignKeepsHistory() {
        Technician ming = em.persist(new Technician(1L, "阿明"));
        Technician hua = em.persist(new Technician(1L, "阿華"));
        Job job = new Job(1L, 100L, LocalDateTime.of(2026, 12, 1, 9, 0));
        job.assignTo(ming, "首次派工");
        job.assignTo(hua, "改派");
        em.persist(job);
        em.flush();
        em.clear();

        Job found = jobs.findById(job.getId()).orElseThrow();
        assertThat(found.getStatus()).isEqualTo(JobStatus.ASSIGNED);
        assertThat(found.getTechnician().getName()).isEqualTo("阿華");
        assertThat(found.getAssignments()).hasSize(2);
        assertThat(found.getAssignments().get(1).getFromTechnician().getName()).isEqualTo("阿明");
    }

    @Test
    void cannotAssignTechnicianFromAnotherCompany() {
        Technician other = em.persist(new Technician(2L, "小李"));
        Job job = new Job(1L, 100L, LocalDateTime.of(2026, 12, 1, 9, 0));

        assertThatThrownBy(() -> job.assignTo(other, "越權改派"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
