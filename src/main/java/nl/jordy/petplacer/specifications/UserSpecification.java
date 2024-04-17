package nl.jordy.petplacer.specifications;

import jakarta.persistence.criteria.*;
import nl.jordy.petplacer.models.Authority;
import nl.jordy.petplacer.models.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification implements Specification<User> {

    private final String username;
    private final String firstName;
    private final String lastName;
    private final List<String> roles;

    public UserSpecification(String username, String firstName, String lastName, List<String> roles) {
        this.username = username != null ? username.toLowerCase() : null;
        this.firstName = firstName != null ? firstName.toLowerCase() : null;
        this.lastName = lastName != null ? lastName.toLowerCase() : null;
        this.roles = roles;
    }

    @Override
    public Predicate toPredicate(
            Root<User> root,
            CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder
    ) {
        List<Predicate> predicates = new ArrayList<>();

        if (username != null) {
            predicates.add(criteriaBuilder.like(root.get("username"), "%" + username + "%"));
        }
        if (firstName != null) {
            predicates.add(criteriaBuilder.like(root.get("firstName"), "%" + firstName + "%"));
        }
        if (lastName != null) {
            predicates.add(criteriaBuilder.like(root.get("lastName"), "%" + lastName + "%"));
        }
        if (roles != null) {
            Join<User, Authority> join = root.join("authorities");
            Predicate rolePredicate = roles.stream().map(
                    // matches the input with authority in the database ignoring case and letters around the input
                    role -> criteriaBuilder.like(join.get("authority"),"%" +  role.toUpperCase() + "%"))
                    // reduce the predicates to a single predicate
                    .reduce(criteriaBuilder::or)
                    // return an empty predicate if the list is empty
                    .orElse(criteriaBuilder.conjunction());

            predicates.add(rolePredicate);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
