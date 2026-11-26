package tw.yijiu.dispatch.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_assignments")
public class JobAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_technician_id")
    private Technician fromTechnician;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "to_technician_id")
    private Technician toTechnician;

    @Column(length = 200)
    private String reason;

    @Column(name = "assigned_at", nullable = false)
    private LocalDateTime assignedAt;

    protected JobAssignment() {
    }

    JobAssignment(Job job, Technician from, Technician to, String reason) {
        this.job = job;
        this.fromTechnician = from;
        this.toTechnician = to;
        this.reason = reason;
        this.assignedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Job getJob() { return job; }
    public Technician getFromTechnician() { return fromTechnician; }
    public Technician getToTechnician() { return toTechnician; }
    public String getReason() { return reason; }
    public LocalDateTime getAssignedAt() { return assignedAt; }
}
