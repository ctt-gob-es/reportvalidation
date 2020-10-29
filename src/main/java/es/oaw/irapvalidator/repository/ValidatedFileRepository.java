package es.oaw.irapvalidator.repository;
import es.oaw.irapvalidator.model.ValidatedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ValidatedFileRepository extends JpaRepository<ValidatedFile, String> {

}