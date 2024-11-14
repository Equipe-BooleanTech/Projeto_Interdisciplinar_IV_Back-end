package com.example.Restaurantto.PDV.controller.dataSheet;

import com.example.Restaurantto.PDV.dto.dataSheet.DataSheetDTO;
import com.example.Restaurantto.PDV.dto.dataSheet.GetDataSheetDTO;
import com.example.Restaurantto.PDV.dto.dataSheet.TimeDataSheetSummaryDTO;
import com.example.Restaurantto.PDV.dto.financial.DateRangeDTO;
import com.example.Restaurantto.PDV.dto.product.TimeSupplierSummaryDTO;
import com.example.Restaurantto.PDV.exception.dataSheet.DataSheetNotFoundException;
import com.example.Restaurantto.PDV.exception.product.SupplierNotFoundException;
import com.example.Restaurantto.PDV.model.dataSheet.DataSheet;
import com.example.Restaurantto.PDV.service.dataSheet.DataSheetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/datasheets")
public class DataSheetController {

    @Autowired
    private DataSheetService dataSheetService;


    // Criar DataSheet
    @PostMapping("/create-datasheet")
    public ResponseEntity<UUID> salvarDataSheet(@RequestBody @Valid DataSheetDTO dataSheetDTO) {
        UUID id = dataSheetService.salvarDataSheet(dataSheetDTO);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    // Atualizar DataSheet
    @PutMapping("/update-datasheet/{id}")
    public ResponseEntity<Void> atualizarDataSheet(@PathVariable UUID id, @RequestBody @Valid DataSheetDTO dataSheetDTO) {
        dataSheetService.atualizarDataSheet(id, dataSheetDTO);
        return ResponseEntity.ok().build();
    }


    // Deletar DataSheet
    @DeleteMapping("/delete-datasheet/{id}")
    public ResponseEntity<Void> deletarDataSheet(@PathVariable UUID id) {
        dataSheetService.deletarDataSheet(id);
        return ResponseEntity.noContent().build();
    }

    // Listar todos os DataSheets com paginação
    @GetMapping("/get-datasheets")
    public ResponseEntity<Page<GetDataSheetDTO>> listarTodosDataSheets(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<GetDataSheetDTO> dataSheets = dataSheetService.listarTodosDataSheets(PageRequest.of(page, size));
        return ResponseEntity.ok(dataSheets);
    }

    @GetMapping("/get-datasheet-by-id/{id}")
    public ResponseEntity<?> buscarFichaPorId(@PathVariable UUID id) {
        try {
            Optional<DataSheet> dataSheet = dataSheetService.listarFichaPeloId(id);
            if (dataSheet.isPresent()){
                return ResponseEntity.ok(dataSheet);
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("FICHA NÃO ENCONTRADA \n ID INVÁLIDO");
            }

        }catch (DataSheetNotFoundException e){
            throw e;
        }
    }

    @PostMapping("/list-datasheets-by-period")
    public ResponseEntity<?> listarFichasPorPeriodo(
            @RequestBody DateRangeDTO dateRangeDTO,
            @RequestParam(defaultValue = "monthly") String groupingType) {
        TimeDataSheetSummaryDTO fichasPorPeriodo = dataSheetService.listarFichasPorPeriodo(dateRangeDTO);

        if (fichasPorPeriodo.dataSheet().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("NENHUMA FICHA ENCONTRADA NO PERÍODO ESPECIFICADO");
        } else {
            return ResponseEntity.ok(fichasPorPeriodo);
        }
    }
}
