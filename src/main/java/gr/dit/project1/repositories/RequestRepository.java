package gr.dit.project1.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gr.dit.project1.entities.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long>{
	
 	@Query("SELECT r.type, count(r.id) FROM Request r WHERE (r.type is not null) and (r.timestamp between :start and :end) GROUP BY r.type ORDER BY count(r.type) DESC")
	List<Object[]> query1(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
	
	@Query("SELECT cast(r.timestamp as date), count(r.id) FROM Request r WHERE (r.type = :type) and (r.timestamp between :start and :end) GROUP BY cast(r.timestamp as date)")
	List<Object[]> query2(@Param("type") String type, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
	
	@Query("select r.sourceIp, r.type, count(r.type) from Request r "
			+ "where cast(r.timestamp as date) = cast(:specific as date) "
			+ "group by r.sourceIp, r.type "
			+ "order by r.sourceIp, count(r.type) DESC")
	List<Object[]> query3(@Param("specific") LocalDateTime specific);
	
	List<Request> findAllBySourceIp(@Param("source") String source);
}
