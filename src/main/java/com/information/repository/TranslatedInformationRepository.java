package com.information.repository;

import com.information.entity.TranslatedInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TranslatedInformationRepository extends JpaRepository<TranslatedInformation, Long> {
    TranslatedInformation findByInformation_InformationIdAndLanguage(Long informationId, String language);

    @Query("select ti from TranslatedInformation ti " +
            "where (:category = '전체' or ti.information.category = :category) "+
            "and ti.language = :language " +
            "and ti.title like concat('%', :keyword, '%')")
    Page<TranslatedInformation> findByLanguageAndCategoryAndTitle(
            @Param("language") String language,
            @Param("category") String category,
            @Param("keyword") String keyword,
            Pageable pageable);

    @Query(value =
            "select ti.* from translated_information ti " +
                    "join information i on i.information_id = ti.information_id " +
                    "where i.category = :category " +
                    "and i.created_at >= :sevenDaysAgo " +
                    "and ti.language = :language " +
                    "order by rand() " +
                    "limit :i"
            , nativeQuery = true)
    List<TranslatedInformation> findRandomByTagAndLanguageAndRecentDayAndCount(
            @Param("category") String category,
            @Param("language") String language,
            @Param("sevenDaysAgo") String sevenDaysAgo,
            @Param("i") int i);
}
