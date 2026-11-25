// ============================================================================================
// DOCUMENTACIÓN · clients/ClientController.java
//
// QUÉ ES:        La puerta HTTP de clientes (/api/clients).
// QUÉ HACE:      Listar, ver, crear, editar y activar/desactivar clientes; delega todo
//                en ClientService.
// SI LO CAMBIAS: Cada método tiene su @PreAuthorize: si cambias el permiso, cambia
//                quién puede usarlo.
// ============================================================================================
package com.dronemission.clients;

import com.dronemission.clients.dto.ClientRequest;
import com.dronemission.clients.dto.ClientResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Tag(name = "Clientes")
public class ClientController {

    /** Dependencia inyectada por Spring (por constructor): servicio de cliente. */
    private final ClientService clientService;

    @GetMapping
    @PreAuthorize("hasAuthority('CLIENTES_VER')")
    public List<ClientResponse> findAll(@RequestParam(required = false) String q) {
        return clientService.findAll(q);
    }

    /**
     * Busca cliente por identificador.
     * Endpoint GET /api/clients/{id} (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('CLIENTES_VER').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return ClientResponse
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CLIENTES_VER')")
    public ClientResponse findById(@PathVariable Long id) {
        return clientService.findById(id);
    }

    /**
     * Crea cliente.
     * Endpoint POST /api/clients (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('CLIENTES_GESTIONAR').
     * @param request petición (ClientRequest)
     * @return ClientResponse
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('CLIENTES_GESTIONAR')")
    public ClientResponse create(@Valid @RequestBody ClientRequest request) {
        return clientService.create(request);
    }

    /**
     * Actualiza cliente.
     * Endpoint PUT /api/clients/{id} (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('CLIENTES_GESTIONAR').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @param request petición (ClientRequest)
     * @return ClientResponse
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CLIENTES_GESTIONAR')")
    public ClientResponse update(@PathVariable Long id, @Valid @RequestBody ClientRequest request) {
        return clientService.update(id, request);
    }

    /**
     * Desactiva (baja lógica) cliente.
     * Endpoint DELETE /api/clients/{id} (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('CLIENTES_GESTIONAR').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CLIENTES_GESTIONAR')")
    @Operation(summary = "Desactivar cliente")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        clientService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
