package com.example.Restaurantto.PDV.mapper.product;

import com.example.Restaurantto.PDV.dto.product.GetSupplierDTO;
import com.example.Restaurantto.PDV.dto.product.SupplierDTO;
import com.example.Restaurantto.PDV.model.product.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface SupplierMapper {
    SupplierMapper INSTANCE = Mappers.getMapper(SupplierMapper.class);

    GetSupplierDTO toSupplier(Supplier supplier);
    Supplier toSupplierDTO(GetSupplierDTO getSupplierDTO);
}
