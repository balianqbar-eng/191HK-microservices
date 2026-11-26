package tw.yijiu.dispatch.web;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tw.yijiu.dispatch.domain.Job;
import tw.yijiu.dispatch.domain.JobAssignment;
import tw.yijiu.dispatch.repo.JobRepository;

@RestController
@RequestMapping("/api")
@Transactional(readOnly = true)
public class JobController {

    private final JobRepository jobs;

    public JobController(JobRepository jobs) {
        this.jobs = jobs;
    }

    @GetMapping("/companies/{companyId}/jobs")
    public List<JobView> listJobs(@PathVariable Long companyId) {
        return jobs.findByCompanyId(companyId).stream().map(JobView::of).toList();
    }

    @GetMapping("/jobs/{jobId}/assignments")
    public List<AssignmentView> listAssignments(@PathVariable Long jobId) {
        Job job = jobs.findById(jobId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到派工單"));
        return job.getAssignments().stream().map(AssignmentView::of).toList();
    }

    public record JobView(Long id, Long bookingId, LocalDateTime scheduledAt, String status, String technician) {
        static JobView of(Job j) {
            String tech = j.getTechnician() == null ? null : j.getTechnician().getName();
            return new JobView(j.getId(), j.getBookingId(), j.getScheduledAt(), j.getStatus().name(), tech);
        }
    }

    public record AssignmentView(String from, String to, String reason, LocalDateTime assignedAt) {
        static AssignmentView of(JobAssignment a) {
            String from = a.getFromTechnician() == null ? null : a.getFromTechnician().getName();
            return new AssignmentView(from, a.getToTechnician().getName(), a.getReason(), a.getAssignedAt());
        }
    }
}
