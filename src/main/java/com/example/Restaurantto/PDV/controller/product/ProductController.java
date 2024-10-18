package com.example.Restaurantto.PDV.controller.product;

import com.example.Restaurantto.PDV.dto.product.GetSupplierDTO;
import com.example.Restaurantto.PDV.dto.product.IngredientDTO;
import com.example.Restaurantto.PDV.dto.product.SupplierDTO;
import com.example.Restaurantto.PDV.exception.product.*;
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
    public ResponseEntity<Page<IngredientDTO>> listarTodosIngredientes(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<IngredientDTO> ingredients = ingredientService.listarTodosIngredientes(PageRequest.of(page,size));
        return ResponseEntity.ok(ingredients);
    }

}
