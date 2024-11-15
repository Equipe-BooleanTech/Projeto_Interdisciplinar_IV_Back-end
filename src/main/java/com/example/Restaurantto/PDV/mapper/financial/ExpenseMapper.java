package com.example.Restaurantto.PDV.mapper.financial;

import com.example.Restaurantto.PDV.dto.financial.ExpensesDTO;
import com.example.Restaurantto.PDV.model.financial.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExpenseMapper {
    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

    ExpensesDTO toExpenseDTO(Expense expense);
    Expense toExpense(ExpensesDTO expenseDTO);
}
