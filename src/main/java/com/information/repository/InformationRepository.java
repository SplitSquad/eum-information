package com.information.repository;

import com.information.entity.Information;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InformationRepository extends JpaRepository<Information, Long> {

    @Query("SELECT i FROM Information i WHERE " +
    "(:category = '전체' OR i.category = :category)")
    Page<Information> findByCategory(
            @Param("category") String category,
            Pageable pageable);

    @Query("select i from Information i " +
            "join Bookmark b on i.informationId = b.information.informationId " +
            "where b.user.userId = :userId")
    Page<Information> findByBookmarkingAndUser_UserId(
            @Param("userId") long userId, Pageable pageable);
}
