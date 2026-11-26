package tw.yijiu.booking.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 20)
    private String phone;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    private List<Booking> bookings = new ArrayList<>();

    protected Customer() {
    }

    public Customer(Long companyId, String name, String phone) {
        this.companyId = companyId;
        this.name = name;
        this.phone = phone;
    }

    public Address addAddress(String city, String line, Integer pings) {
        Address address = new Address(this, city, line, pings);
        addresses.add(address);
        return address;
    }

    public Long getId() { return id; }
    public Long getCompanyId() { return companyId; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public List<Address> getAddresses() { return addresses; }
    public List<Booking> getBookings() { return bookings; }
}
