package com.information.repository;

import com.information.entity.TranslatedInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslatedInformationRepository extends JpaRepository<TranslatedInformation, Long> {
    TranslatedInformation findByInformation_InformationIdAndLanguage(Long informationId, String language);

    @Query("SELECT ti FROM TranslatedInformation ti " +
            "where (ti.information.category = '전체' or ti.information.category = :category) "+
            "and ti.language = :language " +
            "and ti.title like concat('%', :keyword, '%')")
    Page<TranslatedInformation> findByLanguageAndCategoryAndTitle(
            @Param("language") String language,
            @Param("category") String category,
            @Param("keyword") String keyword,
            Pageable pageable);
}
