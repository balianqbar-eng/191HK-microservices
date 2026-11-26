package tw.yijiu.dispatch.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    // booking-service 的預約 ID；跨服務只存 ID，不做外鍵
    @Column(name = "booking_id", nullable = false)
    private Long bookingId;

    @Column(name = "scheduled_at", nullable = false)
    private LocalDateTime scheduledAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private JobStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technician_id")
    private Technician technician;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("assignedAt ASC")
    private List<JobAssignment> assignments = new ArrayList<>();

    protected Job() {
    }

    public Job(Long companyId, Long bookingId, LocalDateTime scheduledAt) {
        this.companyId = companyId;
        this.bookingId = bookingId;
        this.scheduledAt = scheduledAt;
        this.status = JobStatus.PENDING;
    }

    public JobAssignment assignTo(Technician next, String reason) {
        if (!next.getCompanyId().equals(companyId)) {
            throw new IllegalArgumentException("師傅不屬於這張單的公司");
        }
        JobAssignment record = new JobAssignment(this, technician, next, reason);
        assignments.add(record);
        this.technician = next;
        if (status == JobStatus.PENDING) {
            status = JobStatus.ASSIGNED;
        }
        return record;
    }

    public Long getId() { return id; }
    public Long getCompanyId() { return companyId; }
    public Long getBookingId() { return bookingId; }
    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public JobStatus getStatus() { return status; }
    public Technician getTechnician() { return technician; }
    public List<JobAssignment> getAssignments() { return assignments; }
}
