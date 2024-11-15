package com.example.Restaurantto.PDV.mapper.groupFinancial;

import com.example.Restaurantto.PDV.dto.financial.ExpensesDTO;
import com.example.Restaurantto.PDV.model.financial.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExpenseMapper {
    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

    ExpensesDTO toExpensesDTO(Expense expense);
    Expense toExpense(ExpensesDTO expenseDTO);
}
