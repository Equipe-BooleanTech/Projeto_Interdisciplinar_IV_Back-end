package com.example.Restaurantto.PDV.controller.product;

import com.example.Restaurantto.PDV.dto.product.GetSupplierDTO;
import com.example.Restaurantto.PDV.dto.product.IngredientDTO;
import com.example.Restaurantto.PDV.dto.product.SupplierDTO;
import com.example.Restaurantto.PDV.service.product.IngredientService;
import com.example.Restaurantto.PDV.service.product.SupplierService;
import jakarta.validation.Valid;
import org.hibernate.query.Page;
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
    public ResponseEntity<UUID> salvarFornecedor(@RequestBody @Valid SupplierDTO supplierDTO){
        UUID id = supplierService.salvarFornecedor(supplierDTO);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PostMapping("/create-ingredient")
    public ResponseEntity<UUID> salvarIngrediente(@RequestBody @Valid IngredientDTO ingredientDTO){
        UUID id = ingredientService.salvarIngrediente(ingredientDTO);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PutMapping("/update-supplier/{id}")
    public ResponseEntity<Void> atualizarFornecedor(@PathVariable UUID id, @RequestBody @Valid SupplierDTO supplierDTO){
        supplierService.atualizarFornecedor(id, supplierDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-ingredient/{id}")
    public ResponseEntity<Void> atualizarIngrediente(@PathVariable UUID id, @RequestBody @Valid IngredientDTO ingredientDTO){
        ingredientService.atualizarIngrediente(id, ingredientDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-supplier/{id}")
    public ResponseEntity<Void> deletarFornecedor(@PathVariable UUID id){
        supplierService.deletarFornecedor(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete-ingredient/{id}")
    public ResponseEntity<Void> deletarIngrediente(@PathVariable UUID id){
        ingredientService.deletarIngrediente(id);
        return ResponseEntity.noContent().build();
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
