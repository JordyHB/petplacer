package nl.jordy.petplacer.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import nl.jordy.petplacer.models.Donation;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DonationSpecification implements Specification<Donation> {

    private final Long shelterID;
    private final String donatorName;
    private final BigDecimal minDonationAmount;
    private final BigDecimal maxDonationAmount;

    public DonationSpecification(
            Long shelterID,
            String donatorName,
            BigDecimal minDonationAmount,
            BigDecimal maxDonationAmount
    ) {
        this.shelterID = shelterID;
        this.donatorName = donatorName;
        this.minDonationAmount = minDonationAmount;
        this.maxDonationAmount = maxDonationAmount;
    }

    @Override
    public Predicate toPredicate(
            Root<Donation> root,
            CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder
    ) {

        List<Predicate> predicates = new ArrayList<>();

        if (shelterID != null) {
            predicates.add(criteriaBuilder.equal(root.get("receivingShelter").get("id"), shelterID));
        }

        if (donatorName != null) {
            predicates.add(criteriaBuilder.like(root.get("donator").get("username"), "%" + donatorName + "%"));
        }

        if (minDonationAmount != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("donationAmount"), minDonationAmount));
        }

        if (maxDonationAmount != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("donationAmount"), maxDonationAmount));
        }

        if (minDonationAmount != null && maxDonationAmount != null) {
            predicates.add(criteriaBuilder.between(root.get("donationAmount"), minDonationAmount, maxDonationAmount));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }


}
