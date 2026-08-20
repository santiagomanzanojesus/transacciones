package com.company.app.config;
import com.company.app.dto.PagedResponse;
import com.company.app.dto.TransaccionDto;
import com.company.app.dto.TransaccionResponse;
import jakarta.validation.constraints.Min;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "persistenceClient", url = "${api.persistence.url}")
public interface PersistenceClient {
    @PostMapping("/api/transacciones")
    TransaccionResponse guardar(@RequestBody TransaccionDto dto);

    @GetMapping("/api/transacciones")
    PagedResponse<TransaccionResponse> obtenerTransacciones(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortDir") String sortDir
    );
}
