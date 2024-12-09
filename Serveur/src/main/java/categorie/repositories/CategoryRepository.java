package categorie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import categorie.entities.Category;

import java.util.Date;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Query("SELECT c FROM Category c WHERE (:childCount IS NULL OR c.nbrChildrends = :childCount)")
	List<Category> findByNbrChildrens(@Param("childCount") Integer childCount);
	
	@Query("SELECT c FROM Category c WHERE (:isRacine IS NULL OR c.ifRacine = :isRacine) " +
           "AND (:childCount IS NULL OR c.nbrChildrends = :childCount)")
    List<Category> findByIsRacine(@Param("isRacine") Boolean isRacine,
                                   @Param("childCount") Integer childCount);

    @Query("SELECT c FROM Category c WHERE c.creationDate >= :startDate " +
           "AND (:childCount IS NULL OR c.nbrChildrends = :childCount)")
    List<Category> findByCreationDateAfter(@Param("startDate") Date startDate, 
                                            @Param("childCount") Integer childCount);

    @Query("SELECT c FROM Category c WHERE c.creationDate <= :endDate " +
           "AND (:childCount IS NULL OR c.nbrChildrends = :childCount)")
    List<Category> findByCreationDateBefore(@Param("endDate") Date endDate, 
                                             @Param("childCount") Integer childCount);

    @Query("SELECT c FROM Category c WHERE c.creationDate BETWEEN :startDate AND :endDate " +
           "AND (:childCount IS NULL OR c.nbrChildrends = :childCount)")
    List<Category> findByCreationDateBetween(@Param("startDate") Date startDate,
                                             @Param("endDate") Date endDate, 
                                             @Param("childCount") Integer childCount);

    @Query("SELECT c FROM Category c WHERE (:isRacine IS NULL OR c.ifRacine = :isRacine) " +
           "AND (:startDate IS NULL OR c.creationDate >= :startDate) " +
           "AND (:endDate IS NULL OR c.creationDate <= :endDate) " +
           "AND (:childCount IS NULL OR c.nbrChildrends = :childCount)")
    List<Category> findByFilters(@Param("isRacine") Boolean isRacine,
                                 @Param("startDate") Date startDate,
                                 @Param("endDate") Date endDate,
                                 @Param("childCount") Integer childCount);
}
