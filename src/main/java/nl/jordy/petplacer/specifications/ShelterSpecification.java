package nl.jordy.petplacer.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import nl.jordy.petplacer.models.Shelter;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ShelterSpecification implements Specification<Shelter> {

    private final String shelterName;
    private final String city;

    public ShelterSpecification(String shelterName, String city) {
        this.shelterName = shelterName != null ? shelterName.toLowerCase() : null;
        this.city = city != null ? city.toLowerCase() : null;
    }

    @Override
    public Predicate toPredicate(
            Root<Shelter> root,
            CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder
    ) {
        List<Predicate> predicates = new ArrayList<>();

        if (shelterName != null) {
            predicates.add(criteriaBuilder.like(root.get("shelterName"), "%" + shelterName + "%"));
        }
        if (city != null) {
            predicates.add(criteriaBuilder.like(root.get("city"), "%" + city + "%"));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
