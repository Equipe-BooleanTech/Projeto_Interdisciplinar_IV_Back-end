package com.example.Restaurantto.PDV.controller.groupFiles;

import com.example.Restaurantto.PDV.dto.financial.DateRangeDTO;
import com.example.Restaurantto.PDV.dto.global.TimeSummaryDTO;
import com.example.Restaurantto.PDV.dto.groupFile.GetGroupFinancialDTO;
import com.example.Restaurantto.PDV.dto.groupFile.GroupFinancialDTO;
import com.example.Restaurantto.PDV.exception.financial.RecordNotFoundException;
import com.example.Restaurantto.PDV.exception.groupFile.GroupSheetAlreadyRegisteredException;
import com.example.Restaurantto.PDV.model.groupFile.GroupFinancial;
import com.example.Restaurantto.PDV.service.groupFiles.GroupFinancialService;
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
@RequestMapping("/api/groupfinancials")
public class GroupFinancialController {
    private final GroupFinancialService groupFinancialService;
    @Autowired
    public GroupFinancialController(GroupFinancialService groupFinancialService) {
        this.groupFinancialService = groupFinancialService;
    }


    @PostMapping("/create-groupfinancial")
    public ResponseEntity<?> salvarGroupFinancial(@RequestBody GroupFinancialDTO groupFinancialDTO) {
        try {
            UUID id = groupFinancialService.salvarGrupoDeFinancas(groupFinancialDTO);
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        } catch (RecordNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (GroupSheetAlreadyRegisteredException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro interno no servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update-groupfinancial/{id}")
    public ResponseEntity<?> atualizarGroupFinancial(@PathVariable UUID id, @RequestBody @Valid GroupFinancialDTO groupFinancialDTO) {
        try {
            groupFinancialService.atualizarGroupFinancial(id, groupFinancialDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RecordNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (GroupSheetAlreadyRegisteredException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro interno no servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-groupfinancial/{id}")
    public ResponseEntity<?> deletarGroupFinancial(@PathVariable UUID id) {
        try {
            groupFinancialService.deletarGrupoDeFinancas(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RecordNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro interno no servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/get-groupfinancial")
    public ResponseEntity<Page<GetGroupFinancialDTO>> listarTodosGroupFinancial(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<GetGroupFinancialDTO> groupFinancialDTOPage = groupFinancialService.listarTodasListasDeFinancas(PageRequest.of(page, size));
        return new ResponseEntity<>(groupFinancialDTOPage, HttpStatus.OK);
    }

    @GetMapping("/get-groupfinancial-by-id/{id}")
    public ResponseEntity<?> buscarGrupoDeFinancasPorId(@PathVariable UUID id) {
        try {
            Optional<GroupFinancial> groupFinancial = groupFinancialService.listarFichaPeloId(id);
            if (groupFinancial.isPresent()){
                return ResponseEntity.ok(groupFinancial);
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("GRUPO DE FINANÇAS NÃO ENCONTRADA \n ID INVÁLIDO");
            }

        }catch (RecordNotFoundException e){
            throw e;
        }
    }

    @PostMapping("/list-groupfinancial-by-period")
    public ResponseEntity<?> listarGrupoDeFinancasPorPeriodo(
            @RequestBody DateRangeDTO dateRangeDTO,
            @RequestParam(defaultValue = "monthly") String groupingType) {
        TimeSummaryDTO grupoDefinancasPorPeriodo = groupFinancialService.listarGrupoDeFichasPorPeriodo(dateRangeDTO);

        if (grupoDefinancasPorPeriodo.data().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("NENHUM GRUPO DE FINANÇAS ENCONTRADO NO PERÍODO ESPECIFICADO");
        } else {
            return ResponseEntity.ok(grupoDefinancasPorPeriodo);
        }
    }
    }

