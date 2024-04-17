package nl.jordy.petplacer.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import nl.jordy.petplacer.enums.AdoptionRequestStatus;
import nl.jordy.petplacer.models.AdoptionRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AdoptionRequestSpecification implements Specification<AdoptionRequest> {

    private final AdoptionRequestStatus status;
    private final String applicantName;
    private final Long petID;
    private final Long shelterID;

    public AdoptionRequestSpecification(
            AdoptionRequestStatus status,
            String applicantName,
            Long petID,
            Long shelterID
    ) {
        this.status = status;
        this.applicantName = applicantName != null ? applicantName.toLowerCase() : null;
        this.petID = petID;
        this.shelterID = shelterID;
    }

    @Override
    public Predicate toPredicate(
            Root<AdoptionRequest> root,
            CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder
    ) {

        List<Predicate> predicates = new ArrayList<>();

        if (status != null) {
            predicates.add(criteriaBuilder.equal(root.get("status"), status));
        }

        if (applicantName != null) {
            predicates.add(criteriaBuilder.like(root.get("adoptionApplicant").get("username"), "%" + applicantName + "%"));
        }

        if (petID != null) {
            predicates.add(criteriaBuilder.equal(root.get("requestedPet").get("id"), petID));
        }

        if (shelterID != null) {
            predicates.add(criteriaBuilder.equal(root.get("requestedPet").get("shelter").get("id"), shelterID));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }


}
