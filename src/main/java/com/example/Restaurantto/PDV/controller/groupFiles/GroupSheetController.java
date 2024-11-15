package com.example.Restaurantto.PDV.controller.groupFiles;

import com.example.Restaurantto.PDV.dto.financial.DateRangeDTO;
import com.example.Restaurantto.PDV.dto.global.TimeSummaryDTO;
import com.example.Restaurantto.PDV.dto.groupFile.GroupSheetDTO;
import com.example.Restaurantto.PDV.dto.groupFile.GetGroupSheetDTO;
import com.example.Restaurantto.PDV.exception.dataSheet.DataSheetNotFoundException;
import com.example.Restaurantto.PDV.model.groupFile.GroupSheet;
import com.example.Restaurantto.PDV.service.groupFiles.GroupSheetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/groupsheets")
public class GroupSheetController {

    @Autowired
    private GroupSheetService groupSheetService;

    // Criar GroupSheet
    @PostMapping("/create-groupsheet")
    public ResponseEntity<UUID> salvarGroupSheet(@RequestBody @Valid GroupSheetDTO groupSheetDTO) {
        UUID id = groupSheetService.salvarGroupSheet(groupSheetDTO);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    // Atualizar GroupSheet
    @PutMapping("/update-groupsheet/{id}")
    public ResponseEntity<Void> atualizarGroupSheet(@PathVariable UUID id, @RequestBody @Valid GroupSheetDTO groupSheetDTO) {
        groupSheetService.atualizarGroupSheet(id, groupSheetDTO);
        return ResponseEntity.ok().build();
    }

    // Deletar GroupSheet
    @DeleteMapping("/delete-groupsheet/{id}")
    public ResponseEntity<Void> deletarGroupSheet(@PathVariable UUID id) {
        groupSheetService.deletarGroupSheet(id);
        return ResponseEntity.noContent().build();
    }

    // Listar todos os GroupSheets com paginação
    @GetMapping("/get-groupsheets")
    public ResponseEntity<Page<GetGroupSheetDTO>> listarTodosGroupSheets(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<GetGroupSheetDTO> groupSheets = groupSheetService.listarTodosGroupSheets(PageRequest.of(page, size));
        return ResponseEntity.ok(groupSheets);
    }
    @GetMapping("/get-groupsheet-by-id/{id}")
    public ResponseEntity<?> buscarGrupoDeFichaPorId(@PathVariable UUID id) {
        try {
            Optional<GroupSheet> groupSheet = groupSheetService.listarFichaPeloId(id);
            if (groupSheet.isPresent()){
                return ResponseEntity.ok(groupSheet);
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("GRUPO DE FICHA NÃO ENCONTRADA \n ID INVÁLIDO");
            }

        }catch (DataSheetNotFoundException e){
            throw e;
        }
    }
    
    @PostMapping("/list-groupsheets-by-period")
    public ResponseEntity<?> listarGrupoDeFichasPorPeriodo(
            @RequestBody DateRangeDTO dateRangeDTO,
            @RequestParam(defaultValue = "monthly") String groupingType) {
        TimeSummaryDTO grupoDeFichasPorPeriodo = groupSheetService.listarGrupoDeFichasPorPeriodo(dateRangeDTO);

        if (grupoDeFichasPorPeriodo.data().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("NENHUM GRUPO DE FICHA ENCONTRADO NO PERÍODO ESPECIFICADO");
        } else {
            return ResponseEntity.ok(grupoDeFichasPorPeriodo);
        }
    }
}
