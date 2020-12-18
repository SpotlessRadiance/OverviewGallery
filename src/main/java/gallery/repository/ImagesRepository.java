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
//ищем частичные совпадения по названию
    List<Image> findByText(@Param("text") String text);


    //@Query(value = "SELECT image from Image image LEFT JOIN fetch images.tags ")
   // List<Image> findAllWithAllTags();

    //@Query("select image from Image image left join fetch images_tags where image.id =:id")
    //Image findOneWithEagerRelationships(@Param("id") Long id);

   //@Query(value = "SELECT * FROM images img LEFT JOIN images_tags it ON img.image_id = it.tags_id")


   // @Query(value="SELECT image FROM Image image left join fetch image.tags WHERE u.status = ?1")
        //SQL-скрипт для поиска
    //List<Image> findByCategory(@Param("tag") int tag);

    //@Query(value="SELECT image FROM Image image left join fetch tags WHERE ")
    /*@Query(
            value = "SELECT * FROM (images LEFT JOIN user ON institution.user_id = user.id) LEFT JOIN\n" +
                    "(SELECT * FROM building_institutions WHERE building_institutions.building_id = 1) AS reserved_institutions\n" +
                    "ON reserved_institutions.institutions_user_id = kits_nwt.institution.user_id\n" +
                    "WHERE reserved_institutions.institutions_user_id IS null ORDER BY ?#{#pageable}",
            nativeQuery = true)
    Page<Institution> findPotentialInstitutionsByBuildingId(Long userId, Pageable pageable);*/

    /*public List<Long> getIdsByKeyWord(String keyWord) {
        return queryFactory.select(picture.id)
                .from(picture)
                .where(picture.name.toLowerCase()
                        .like("%" + keyWord.toLowerCase() + "%")
                        .or(picture.description.toLowerCase()
                                .like("%" + keyWord.toLowerCase() + "%")))
                .fetch();
    }*/
}

   /*
}
@Query(
        value = "SELECT username, password, description, location, title, user_id FROM (institution INNER JOIN user ON institution.user_id = user.id) LEFT JOIN\n" +
                "(SELECT * FROM building_institutions WHERE building_institutions.building_id = 1) AS reserved_institutions\n" +
                "ON reserved_institutions.institutions_user_id = kits_nwt.institution.user_id\n" +
                "WHERE reserved_institutions.institutions_user_id IS null ORDER BY ?#{#pageable}",
        nativeQuery = true)
Page<Institution> findPotentialInstitutionsByBuildingId(Long userId, Pageable pageable);


       @Query(value = "SELECT DISTINCT img from Image img LEFT JOIN FETCH photo.tags",
                countQuery = "select count(distinct photo) from Photo photo")
        Page<Image> findAllWithEagerRelationships(Pageable pageable);

        @Query(value = "SELECT distinct photo from Photo photo left join fetch photo.tags")
        List<Image> findAllWithAllTags();

        @Query("select photo from Photo photo left join fetch photo.tags where photo.id =:id")
        Optional<Image> findOneWithEagerRelationships(@Param("id") Long id);}*/


