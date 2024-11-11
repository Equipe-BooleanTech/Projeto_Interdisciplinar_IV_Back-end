package com.example.Restaurantto.PDV.controller.product;

import com.example.Restaurantto.PDV.dto.financial.DateRangeDTO;
import com.example.Restaurantto.PDV.dto.product.*;
import com.example.Restaurantto.PDV.exception.product.*;
import com.example.Restaurantto.PDV.model.product.Ingredient;
import com.example.Restaurantto.PDV.model.product.Supplier;
import com.example.Restaurantto.PDV.response.SuccessResponse;
import com.example.Restaurantto.PDV.response.UpdateResponse;
import com.example.Restaurantto.PDV.service.product.IngredientService;
import com.example.Restaurantto.PDV.service.product.SupplierService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private IngredientService ingredientService;

    @PostMapping("/create-supplier")
    public ResponseEntity<SuccessResponse> salvarFornecedor(@RequestBody @Valid SupplierDTO supplierDTO){
        try {
            UUID id = supplierService.salvarFornecedor(supplierDTO);
            SuccessResponse response = new SuccessResponse("FORNECEDOR CRIADO COM SUCESSO!", id);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (SupplierCreationFailedException e){
            throw e;
        }
    }

    @PostMapping("/create-ingredient")
    public ResponseEntity<SuccessResponse> salvarIngrediente(@RequestBody @Valid IngredientDTO ingredientDTO){
        try{
            UUID id = ingredientService.salvarIngrediente(ingredientDTO);
            SuccessResponse response = new SuccessResponse("INGREDIENTE CRIADO COM SUCESSO!", id);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (IngredientCreationFailedException e){
            throw e;
        }


    }

    @PutMapping("/update-supplier/{id}")
    public ResponseEntity<UpdateResponse> atualizarFornecedor(@PathVariable UUID id, @RequestBody @Valid SupplierDTO supplierDTO){
        try {
            supplierService.atualizarFornecedor(id, supplierDTO);

            UpdateResponse response = new UpdateResponse("FORNECEDOR ATUALIZADO COM SUCESSO!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (SupplierUpdateFailedException e){
            throw e;
        }
    }

    @PutMapping("/update-ingredient/{id}")
    public ResponseEntity<UpdateResponse> atualizarIngrediente(@PathVariable UUID id, @RequestBody @Valid IngredientDTO ingredientDTO){
       try {
           ingredientService.atualizarIngrediente(id, ingredientDTO);
           UpdateResponse response = new UpdateResponse("INGREDIENTE ATUALIZADO COM SUCESSO!");
           return new ResponseEntity<>(response, HttpStatus.OK);
       }catch (IngredientUpdateFailedException e){
           throw e;
       }
    }

    @DeleteMapping("/delete-supplier/{id}")
    public ResponseEntity<SuccessResponse> deletarFornecedor(@PathVariable UUID id){
        try {
            supplierService.deletarFornecedor(id);
            SuccessResponse response = new SuccessResponse("FORNECEDOR DELETADO COM SUCESSO!", id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (SupplierDeletionFailedException e){
            throw e;
        }
    }

    @DeleteMapping("/delete-ingredient/{id}")
    public ResponseEntity<SuccessResponse> deletarIngrediente(@PathVariable UUID id){
        try {
            ingredientService.deletarIngrediente(id);
            SuccessResponse response = new SuccessResponse("INGREDIENTE DELETADO COM SUCESSO!", id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (IngredientDeletionFailedException e){
            throw e;
        }
    }

    @GetMapping("/get-supplier")
    public ResponseEntity<Page<GetSupplierDTO>> listarTodosFornecedores(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<GetSupplierDTO> supplier = supplierService.listarTodosFornecedores(PageRequest.of(page, size));
        return ResponseEntity.ok(supplier);
    }

    @GetMapping("/get-ingredients")
    public ResponseEntity<Page<GetIngredientDTO>> listarTodosIngredientes(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<GetIngredientDTO> ingredients = ingredientService.listarTodosIngredientes(PageRequest.of(page,size));
        return ResponseEntity.ok(ingredients);
    }

    @GetMapping("/get-supplier-by-id/{id}")
    public ResponseEntity<?> buscarFonecedorPorId(@PathVariable UUID id) {
        try {
            Optional<Supplier> supplier = supplierService.listarFornecedorPeloId(id);
            if (supplier.isPresent()){
                return ResponseEntity.ok(supplier);
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("FORNECEDOR NÃO ENCONTRADO \n ID INVÁLIDO");
            }

        }catch (SupplierNotFoundException e){
            throw e;
        }
    }

    @GetMapping("/get-ingredient-by-id/{id}")
    public ResponseEntity<?> buscarIngredientePorId(@PathVariable UUID id) {
        try {
            Optional<Ingredient> ingredient = ingredientService.listarIngredientePeloId(id);
            if (ingredient.isPresent()){
                return ResponseEntity.ok(ingredient);
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("INGREDIENTE NÃO ENCONTRADO \n ID INVÁLIDO");
            }
        }catch (IngredientNotFoundException e){
            throw e;
        }
    }

    @PostMapping("/list-ingredients-by-period")
    public ResponseEntity<?> listarIngredientesPorPeriodo(
            @RequestBody DateRangeDTO dateRangeDTO,
            @RequestParam(defaultValue = "monthly") String groupingType) {
        // ATENÇÃO SE PASSAR A URL NORMAL ELE VAI LISTAR POR MÊS
        // PASSANDO A URL ASSIM list-ingredients-by-period?groupingType=weekly ELE LISTA POR SEMANA
        // PASSANDO A URL ASSIM list-ingredients-by-period?groupingType=yearly ELE LISTA POR ANO
        Map<String, TimeIngredientSummaryDTO> ingredientesPorPeriodo = ingredientService.listarIngredientesPorPeriodo(dateRangeDTO, groupingType);

        if (ingredientesPorPeriodo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("NENHUM INGREDIENTE ENCONTRADO NO PERÍODO ESPECIFICADO");
        } else {
            return ResponseEntity.ok(ingredientesPorPeriodo);
        }
    }
    @PostMapping("/list-suppliers-by-period")
    public ResponseEntity<?> listarfornecedoresPorPeriodo(
            @RequestBody DateRangeDTO dateRangeDTO,
            @RequestParam(defaultValue = "monthly") String groupingType) {
        // ATENÇÃO SE PASSAR A URL NORMAL ELE VAI LISTAR POR MÊS
        // PASSANDO A URL ASSIM list-suppliers-by-period?groupingType=weekly ELE LISTA POR SEMANA
        // PASSANDO A URL ASSIM list-suppliers-by-period?groupingType=yearly ELE LISTA POR ANO
        Map<String, TimeSupplierSummaryDTO> fornecedoresPorPeriodo = supplierService.listarForncedoresPorPeriodo(dateRangeDTO, groupingType);

        if (fornecedoresPorPeriodo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("NENHUM FORNCEDOR ENCONTRADO NO PERÍODO ESPECIFICADO");
        } else {
            return ResponseEntity.ok(fornecedoresPorPeriodo);
        }
    }
}
