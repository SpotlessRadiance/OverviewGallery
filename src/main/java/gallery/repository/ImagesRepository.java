package gallery.repository;

import gallery.model.Image;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface ImagesRepository extends JpaRepository<Image, Long> {
    @Query(value = "SELECT * FROM images img WHERE img.title LIKE CONCAT('%',:text,'%')", nativeQuery = true)
    List<Image> findByText(@Param("text") String text);


}

