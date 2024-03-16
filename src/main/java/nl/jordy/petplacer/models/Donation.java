package nl.jordy.petplacer.models;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "donations")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne
    private User donator;

    @ManyToOne
    private Shelter receivingShelter;

    private BigDecimal donationAmount;
    private String donationMessage;

}
