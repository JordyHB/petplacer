package nl.jordy.petplacer.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "donations")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User donator;

    @ManyToOne()
    private Shelter receivingShelter;

    private BigDecimal donationAmount;
    private String donationMessage;
    private Date dateOfDonation = new Date();
    private Date dateOfLastUpdate = new Date();
}
