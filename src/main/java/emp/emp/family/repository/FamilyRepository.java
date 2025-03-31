package emp.emp.family.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import emp.emp.family.entity.Family;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {

	Optional<Family> findByCode(String code);

	boolean existsByCode(String code);
}
