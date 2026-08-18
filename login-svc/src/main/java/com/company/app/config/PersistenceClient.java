package com.company.app.config;

import com.company.app.dto.TransaccionDto;
import com.company.app.dto.TransaccionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "persistenceClient", url = "${api.persistence.url}")
public interface PersistenceClient {
    @PostMapping("/api/transaccion")
    TransaccionResponse guardar(@RequestBody TransaccionDto dto);
}
