package nl.jordy.petplacer.specifications;

import jakarta.persistence.criteria.*;
import nl.jordy.petplacer.enums.GenderEnum;
import nl.jordy.petplacer.models.User;
import nl.jordy.petplacer.models.UserOwnedPet;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserOwnedPetSpecification implements Specification<UserOwnedPet> {

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
    private final String ownerUsername;
    private final Boolean isAdopted;

    public UserOwnedPetSpecification(
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

            // user pet attributes
            String ownerUsername,
            Boolean isAdopted
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
        this.ownerUsername = ownerUsername != null ? ownerUsername.toLowerCase() : null;
        this.isAdopted = isAdopted;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public Predicate toPredicate(
            Root<UserOwnedPet> root,
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

        // user pet attributes
        if (ownerUsername != null) {
            Join<UserOwnedPet, User> join = root.join("currentOwner");
            predicates.add(criteriaBuilder.like(join.get("username"), "%" + ownerUsername + "%"));
        }
        if (isAdopted != null) {
            predicates.add(criteriaBuilder.equal(root.get("isAdopted"), isAdopted));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}