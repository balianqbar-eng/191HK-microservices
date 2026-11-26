package tw.yijiu.booking.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address_id")
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type", nullable = false, length = 30)
    private ServiceType serviceType;

    @Column(name = "preferred_at", nullable = false)
    private LocalDateTime preferredAt;

    @Column(name = "quoted_amount", precision = 10, scale = 0)
    private BigDecimal quotedAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BookingStatus status;

    protected Booking() {
    }

    public Booking(Customer customer, Address address, ServiceType serviceType, LocalDateTime preferredAt) {
        this.companyId = customer.getCompanyId();
        this.customer = customer;
        this.address = address;
        this.serviceType = serviceType;
        this.preferredAt = preferredAt;
        this.status = BookingStatus.DRAFT;
    }

    public Long getId() { return id; }
    public Long getCompanyId() { return companyId; }
    public Customer getCustomer() { return customer; }
    public Address getAddress() { return address; }
    public ServiceType getServiceType() { return serviceType; }
    public LocalDateTime getPreferredAt() { return preferredAt; }
    public BigDecimal getQuotedAmount() { return quotedAmount; }
    public BookingStatus getStatus() { return status; }
}
