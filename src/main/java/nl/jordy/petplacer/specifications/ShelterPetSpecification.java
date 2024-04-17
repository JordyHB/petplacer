package nl.jordy.petplacer.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import nl.jordy.petplacer.enums.GenderEnum;
import nl.jordy.petplacer.enums.ShelterPetStatus;
import nl.jordy.petplacer.models.ShelterPet;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShelterPetSpecification implements Specification<ShelterPet> {

    private final String name;
    private final String species;
    private final String breed;
    private final Integer minAge;
    private final Integer maxAge;
    private final GenderEnum genderEnum;
    private final Boolean spayedNeutered;
    private final Boolean goodWithKids;
    private final Boolean goodWithDogs;
    private final Boolean goodWithCats;
    private final Long shelterID;
    private final BigDecimal minAdoptionFee;
    private final BigDecimal maxAdoptionFee;
    private final ShelterPetStatus status;

    public ShelterPetSpecification(

            // pet attributes
            String name,
            String species,
            String breed,
            Integer minAge,
            Integer maxAge,
            GenderEnum genderEnum,
            Boolean spayedNeutered,
            Boolean goodWithKids,
            Boolean goodWithDogs,
            Boolean goodWithCats,

            // shelter pet attributes
            Long shelterID,
            BigDecimal minAdoptionFee,
            BigDecimal maxAdoptionFee,
            ShelterPetStatus status
    ) {
        this.name = name != null ? name.toLowerCase() : null;
        this.species = species != null ? species.toLowerCase() : null;
        this.breed = breed != null ? breed.toLowerCase() : null;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.genderEnum = genderEnum;
        this.spayedNeutered = spayedNeutered;
        this.goodWithKids = goodWithKids;
        this.goodWithDogs = goodWithDogs;
        this.goodWithCats = goodWithCats;
        this.shelterID = shelterID;
        this.minAdoptionFee = minAdoptionFee;
        this.maxAdoptionFee = maxAdoptionFee;
        this.status = status;
    }

    @Override
    public Predicate toPredicate(
            Root<ShelterPet> root,
            CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder
    ) {

        List<Predicate> predicates = new ArrayList<>();

        // pet attributes
        if (name != null) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
        }
        if (species != null) {
            predicates.add(criteriaBuilder.like(root.get("species"), "%" + species + "%"));
        }
        if (breed != null) {
            predicates.add(criteriaBuilder.like(root.get("breed"), "%" + breed + "%"));
        }
        // age range
        if (minAge != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("age"), minAge));
        }
        if (maxAge != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("age"), maxAge));
        }
        if (minAge != null && maxAge != null) {
            predicates.add(criteriaBuilder.between(root.get("age"), minAge, maxAge));
        }
        if (genderEnum != null) {
            predicates.add(criteriaBuilder.equal(root.get("gender"), genderEnum));
        }
        // boolean attributes
        if (spayedNeutered != null) {
            predicates.add(criteriaBuilder.equal(root.get("spayedNeutered"), spayedNeutered));
        }
        if (goodWithKids != null) {
            predicates.add(criteriaBuilder.equal(root.get("goodWithKids"), goodWithKids));
        }
        if (goodWithDogs != null) {
            predicates.add(criteriaBuilder.equal(root.get("goodWithDogs"), goodWithDogs));
        }
        if (goodWithCats != null) {
            predicates.add(criteriaBuilder.equal(root.get("goodWithCats"), goodWithCats));
        }

        // shelter pet attributes
        if (shelterID != null) {
            predicates.add(criteriaBuilder.equal(root.get("shelter").get("id"), shelterID));
        }
        if (minAdoptionFee != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("adoptionFee"), minAdoptionFee));
        }
        if (maxAdoptionFee != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("adoptionFee"), maxAdoptionFee));
        }
        if (minAdoptionFee != null && maxAdoptionFee != null) {
            predicates.add(criteriaBuilder.between(root.get("adoptionFee"), minAdoptionFee, maxAdoptionFee));
        }
        if (status != null) {
            predicates.add(criteriaBuilder.equal(root.get("status"), status));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
