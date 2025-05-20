package com.information.repository;

import com.information.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    long countByInformation_InformationIdAndUser_UserId(Long informationId, Long userId);

    Bookmark findByInformation_InformationIdAndUser_UserId(long informationId, Long userId);
}
