package br.com.sgc.repository;

import br.com.sgc.domain.Employee;
import br.com.sgc.service.filter.EmployeeFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT E " +
            " FROM " +
            "   Employee E " +
            " WHERE " +
            "   (:#{#filter.name} IS NULL OR LOWER(E.name) LIKE LOWER(CONCAT('%', :#{#filter.name}, '%'))) AND " +
            "   (:#{#filter.lastName} IS NULL OR LOWER(E.lastName) LIKE LOWER(CONCAT('%', :#{#filter.lastName}, '%'))) AND " +
            "   (:#{#filter.email} IS NULL OR LOWER(E.email) LIKE LOWER(CONCAT('%', :#{#filter.email}, '%'))) AND " +
            "   (:#{#filter.idSeniority} IS NULL OR E.seniority.id = :#{#filter.idSeniority}) AND " +
            "   E.active = TRUE")
    Page<Employee> filter(@Param("filter") EmployeeFilter filter, Pageable pageable);

    @Query("SELECT E.active " +
            " FROM " +
            "   Employee E " +
            " WHERE " +
            "   E.id = :id")
    Boolean findActiveById(@Param("id") Long id);

}
