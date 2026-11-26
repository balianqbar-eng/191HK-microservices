package tw.yijiu.booking.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(nullable = false, length = 20)
    private String city;

    @Column(nullable = false, length = 200)
    private String line;

    private Integer pings;

    protected Address() {
    }

    Address(Customer customer, String city, String line, Integer pings) {
        this.customer = customer;
        this.city = city;
        this.line = line;
        this.pings = pings;
    }

    public Long getId() { return id; }
    public Customer getCustomer() { return customer; }
    public String getCity() { return city; }
    public String getLine() { return line; }
    public Integer getPings() { return pings; }
}
