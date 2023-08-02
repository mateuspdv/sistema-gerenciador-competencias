package br.com.sgc.repository;

import br.com.sgc.model.CompetencyModel;
import br.com.sgc.service.dto.CompetencyDto;
import br.com.sgc.service.dto.DropdownCategoryDto;
import br.com.sgc.service.dto.filter.CompetencyFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompetencyRepository extends JpaRepository<CompetencyModel, Long> {

    @Query("select new br.com.sgc.service.dto.CompetencyDto(c.id, " +
            "c.name, " +
            "c.description, " +
            "c.categoryModel.id, " +
            "c.categoryModel.name) from CompetencyModel c where lower(c.name) = lower(:query) or " +
            "lower(c.description) = lower(:query) or " +
            "lower(c.categoryModel.name) = lower(:query) " +
            "order by c.id")
    Page<CompetencyDto> globalSearchFilter(Pageable pageable, @Param("query") String query);

    @Query("SELECT COUNT(CC.competencyModel.id) > 0 " +
            " FROM " +
            " ContributorCompetencyModel CC " +
            " WHERE CC.competencyModel.id = :idCompetency ")
    Boolean haveRelationContributor(@Param("idCompetency") Long idCompetency);

    @Query("select new br.com.sgc.service.dto.DropdownCategoryDto(C.id, " +
            " C.name) " +
            " FROM " +
            " CompetencyModel C ")
    List<DropdownCategoryDto> findAllDropdown();

    @Query("select new br.com.sgc.service.dto.CompetencyDto(C.id, " +
            " C.name, " +
            " C.description, " +
            " C.categoryModel.id, " +
            " C.categoryModel.name) " +
            " FROM " +
            "   CompetencyModel C " +
            " WHERE " +
            "   (lower(:#{#filter.name}) = lower(C.name) OR :#{#filter.name} = '') AND " +
            "   (lower(:#{#filter.description}) = lower(C.description) OR :#{#filter.description} = '') AND " +
            "   (:#{#filter.idCategory} = C.categoryModel.id OR :#{#filter.idCategory} IS NULL) ")
    Page<CompetencyDto> columnsFilter(Pageable pageable, @Param("filter") CompetencyFilterDto filter);

}
