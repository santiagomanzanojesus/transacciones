package com.company.app.repository;

import com.company.app.entity.Transaccion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    @Query("SELECT t FROM Transaccion t WHERE " +
            "(:search IS NULL OR LOWER(t.operacion) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(t.cliente) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Transaccion> buscarConFiltro(@Param("search") String search, Pageable pageable);
}
