package br.com.sgc.repository;

import br.com.sgc.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT E.active " +
            " FROM " +
            "   Employee E " +
            " WHERE " +
            "   E.id = :id")
    Boolean findActiveById(@Param("id") Long id);

}
