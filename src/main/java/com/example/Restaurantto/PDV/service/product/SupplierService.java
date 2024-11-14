package com.example.Restaurantto.PDV.service.product;

import com.example.Restaurantto.PDV.dto.financial.DateRangeDTO;
import com.example.Restaurantto.PDV.dto.global.TimeSummaryDTO;
import com.example.Restaurantto.PDV.dto.product.GetSupplierDTO;
import com.example.Restaurantto.PDV.dto.product.SupplierDTO;
import com.example.Restaurantto.PDV.exception.product.SupplierAlreadyRegisteredException;
import com.example.Restaurantto.PDV.exception.product.SupplierNotFoundException;
import com.example.Restaurantto.PDV.model.product.Supplier;
import com.example.Restaurantto.PDV.repository.product.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    public UUID salvarFornecedor(SupplierDTO supplierDTO) {
    if (supplierRepository.findByName(supplierDTO.name()).isPresent()) {
        throw new SupplierAlreadyRegisteredException("FORNECEDOR JÁ CADASTRADO");
    }
        Supplier newSupplier = Supplier.builder()
                .name(supplierDTO.name())
                .cnpj(supplierDTO.cnpj())
                .contact(supplierDTO.contact())
                .phone(supplierDTO.phone())
                .createdAt(LocalDate.now())
                .build();

        supplierRepository.save(newSupplier);

        return newSupplier.getId();
    }

    public void atualizarFornecedor(UUID id,SupplierDTO supplierDTO) {
        Supplier supplier= supplierRepository.findById(id)
                .orElseThrow(()-> new SupplierNotFoundException("FORNECEDOR NÃO ENCONTRADO"));

        supplier.setName(supplierDTO.name());
        supplier.setCnpj(supplierDTO.cnpj());
        supplier.setContact(supplierDTO.contact());
        supplier.setPhone(supplierDTO.phone());

        supplierRepository.save(supplier);

    }

    public void deletarFornecedor(UUID id) {
        if (!supplierRepository.existsById(id)) {
           throw new SupplierNotFoundException("FORNECEDOR NÃO ENCONTRADO");
        }
        supplierRepository.deleteById(id);
    }

    private GetSupplierDTO listarFornecedor(Supplier supplier) {
        return new GetSupplierDTO(
                supplier.getId(),
                supplier.getName(),
                supplier.getCnpj(),
                supplier.getContact(),
                supplier.getPhone(),
                supplier.getCreatedAt());
    }

    public Page<GetSupplierDTO> listarTodosFornecedores(PageRequest pageRequest) {
        return supplierRepository.findAll(pageRequest)
                .map(this::listarFornecedor);

    }

    public Optional<Supplier> listarFornecedorPeloId(UUID id) {
        return supplierRepository.findById(id);
    }

    public TimeSummaryDTO listarfornecedoresPorPeriodo(DateRangeDTO dateRangeDTO) {
        List<Supplier> suppliers = supplierRepository.findAllByCreatedAtBetween(dateRangeDTO.startDate(), dateRangeDTO.endDate());

        List<GetSupplierDTO> supplierDTOList = suppliers.stream()
                .map(this::listarFornecedor)
                .toList();

        return new TimeSummaryDTO(Collections.singletonList(supplierDTOList), supplierDTOList.size());
    }
}
