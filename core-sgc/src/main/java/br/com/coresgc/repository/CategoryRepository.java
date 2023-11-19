package br.com.coresgc.repository;

import br.com.coresgc.domain.Category;
import br.com.coresgc.service.dto.DropdownDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Category entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT NEW br.com.coresgc.service.dto.DropdownDTO(C.id, " +
        " C.name) " +
        " FROM " +
        "   Category C ")
    List<DropdownDTO> findAllDropdown();

}
