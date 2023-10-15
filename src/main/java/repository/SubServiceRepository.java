package repository;

import domain.Service;
import domain.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubServiceRepository extends JpaRepository<SubService,Long> {

    Optional<SubService> findSubServiceByName(String name);

    @Query(value = "select Service from SubService where SubService .name=:name")
    Optional<Service> findServiceBuSubServiceName(@Param("name") String name);

}