package com.example.Restaurantto.PDV.controller.groupSheet;

import com.example.Restaurantto.PDV.dto.groupSheet.GroupSheetDTO;
import com.example.Restaurantto.PDV.dto.groupSheet.GetGroupSheetDTO;
import com.example.Restaurantto.PDV.service.groupSheet.GroupSheetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
