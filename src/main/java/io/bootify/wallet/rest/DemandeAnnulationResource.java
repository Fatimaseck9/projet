package io.bootify.wallet.rest;

import io.bootify.wallet.model.DemandeAnnulationDTO;
import io.bootify.wallet.service.DemandeAnnulationService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/demandeAnnulations", produces = MediaType.APPLICATION_JSON_VALUE)
public class DemandeAnnulationResource {

    private final DemandeAnnulationService demandeAnnulationService;

    public DemandeAnnulationResource(final DemandeAnnulationService demandeAnnulationService) {
        this.demandeAnnulationService = demandeAnnulationService;
    }

    @GetMapping
    public ResponseEntity<List<DemandeAnnulationDTO>> getAllDemandeAnnulations() {
        return ResponseEntity.ok(demandeAnnulationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DemandeAnnulationDTO> getDemandeAnnulation(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(demandeAnnulationService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDemandeAnnulation(
            @RequestBody @Valid final DemandeAnnulationDTO demandeAnnulationDTO) {
        final Long createdId = demandeAnnulationService.create(demandeAnnulationDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateDemandeAnnulation(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final DemandeAnnulationDTO demandeAnnulationDTO) {
        demandeAnnulationService.update(id, demandeAnnulationDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDemandeAnnulation(@PathVariable(name = "id") final Long id) {
        demandeAnnulationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
