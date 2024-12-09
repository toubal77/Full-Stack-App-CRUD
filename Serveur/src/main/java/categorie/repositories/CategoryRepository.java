package categorie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import categorie.entities.Category;

import java.util.Date;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.ifRacine = :isRacine")
    List<Category> findByIsRacine(@Param("isRacine") boolean isRacine);

    @Query("SELECT c FROM Category c WHERE c.creationDate >= :startDate")
    List<Category> findByCreationDateAfter(@Param("startDate") Date startDate);

    @Query("SELECT c FROM Category c WHERE c.creationDate <= :endDate")
    List<Category> findByCreationDateBefore(@Param("endDate") Date endDate);

    @Query("SELECT c FROM Category c WHERE c.creationDate BETWEEN :startDate AND :endDate")
    List<Category> findByCreationDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT c FROM Category c WHERE (:isRacine IS NULL OR c.ifRacine = :isRacine) " +
            "AND (:startDate IS NULL OR c.creationDate >= :startDate) " +
            "AND (:endDate IS NULL OR c.creationDate <= :endDate)")
    List<Category> findByFilters(@Param("isRacine") boolean isRacine,
                                 @Param("startDate") Date startDate,
                                 @Param("endDate") Date endDate);
}