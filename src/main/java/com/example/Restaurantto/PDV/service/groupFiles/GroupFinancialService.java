package com.example.Restaurantto.PDV.service.groupFiles;

import com.example.Restaurantto.PDV.dto.financial.DateRangeDTO;
import com.example.Restaurantto.PDV.dto.financial.ExpensesDTO;
import com.example.Restaurantto.PDV.dto.financial.RevenueDTO;
import com.example.Restaurantto.PDV.dto.global.TimeSummaryDTO;
import com.example.Restaurantto.PDV.dto.groupFile.GetGroupFinancialDTO;
import com.example.Restaurantto.PDV.dto.groupFile.GroupFinancialDTO;
import com.example.Restaurantto.PDV.exception.financial.RecordNotFoundException;
import com.example.Restaurantto.PDV.exception.groupFile.GroupFinancialAlreadyRegisteredException;
import com.example.Restaurantto.PDV.exception.groupFile.GroupFinancialNotFoundException;
import com.example.Restaurantto.PDV.mapper.groupFinancial.GroupFinancialMapper;
import com.example.Restaurantto.PDV.model.financial.Expense;
import com.example.Restaurantto.PDV.model.financial.Revenue;
import com.example.Restaurantto.PDV.model.groupFile.GroupFinancial;
import com.example.Restaurantto.PDV.repository.financial.ExpensesRepository;
import com.example.Restaurantto.PDV.repository.financial.RevenueRepository;
import com.example.Restaurantto.PDV.repository.groupFile.GroupFinancialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupFinancialService {

    private final GroupFinancialRepository groupFinancialRepository;
    private final RevenueRepository revenueRepository;
    private final ExpensesRepository expensesRepository;

    @Autowired
    public GroupFinancialService(GroupFinancialRepository groupFinancialRepository, RevenueRepository revenueRepository, ExpensesRepository expensesRepository) {
        this.groupFinancialRepository = groupFinancialRepository;
        this.revenueRepository = revenueRepository;
        this.expensesRepository = expensesRepository;
    }
    private static Set<Revenue> findRevenuesByIds(Set<RevenueDTO> revenueDTOs, RevenueRepository revenueRepository) {
        return revenueDTOs.stream()
                .map(revenueDTO -> revenueRepository.findById(revenueDTO.id())
                        .orElseThrow(() -> new RecordNotFoundException("RECEITA NÃO ENCONTRADA: " + revenueDTO.id())))
                .collect(Collectors.toSet());
    }
    private static Set<Expense> findExpensesByIds(Set<ExpensesDTO> expensesDTO, ExpensesRepository expensesRepository) {
        return expensesDTO.stream().map(expense -> expensesRepository.findById(expense.id())
                        .orElseThrow(() -> new RecordNotFoundException("DESPESA NÃO ENCONTRADA:")))
                .collect(Collectors.toSet());
    }

    public UUID salvarGrupoDeFinancas(GroupFinancialDTO groupFinancialDTO){
        if(groupFinancialRepository.findByName(groupFinancialDTO.name()).isPresent()){
            throw new GroupFinancialAlreadyRegisteredException("GRUPO DE FINANÇAS JÁ CADASTRADO");
        }


        Set<Revenue> revenues =  findRevenuesByIds(groupFinancialDTO.revenues(), revenueRepository);
        Set<Expense> expenses =  findExpensesByIds(groupFinancialDTO.expenses(), expensesRepository);

        GroupFinancial groupFinancial = GroupFinancial.builder()
                .name(groupFinancialDTO.name())
                .revenues(revenues)
                .expenses(expenses)
                .createdAt(LocalDate.now())
                .build();
        groupFinancialRepository.save(groupFinancial);
        return groupFinancial.getId();
    }

    public void atualizarGroupFinancial(UUID id, GroupFinancialDTO groupFinancialDTO){
        GroupFinancial groupFinancial = groupFinancialRepository.findById(id)
                .orElseThrow(() -> new GroupFinancialNotFoundException("GRUPO DE FINANÇAS NÃO ENCONTRADO"));

        Set<Revenue> revenues =  findRevenuesByIds(groupFinancialDTO.revenues(), revenueRepository);
        Set<Expense> expenses =  findExpensesByIds(groupFinancialDTO.expenses(), expensesRepository);

        groupFinancial.setName(groupFinancialDTO.name());
        groupFinancial.setRevenues(revenues);
        groupFinancial.setExpenses(expenses);
        groupFinancialRepository.save(groupFinancial);

    }

    public void deletarGrupoDeFinancas(UUID id){
        groupFinancialRepository.deleteById(id);
    }

    public Page<GetGroupFinancialDTO> listarTodasListasDeFinancas(PageRequest pageRequest){
        return groupFinancialRepository.findAll(pageRequest)
                .map(GroupFinancialMapper.INSTANCE::toGetGroupFinancialDTO);
    }

    public Optional<GroupFinancial> listarFichaPeloId(UUID id){
        return groupFinancialRepository.findById(id);
    }

    public TimeSummaryDTO listarGrupoDeFichasPorPeriodo(DateRangeDTO dateRangeDTO) {
        List<GroupFinancial> data = groupFinancialRepository.findAllByCreatedAtBetween(
                dateRangeDTO.startDate(), dateRangeDTO.endDate()
        );

        List<GetGroupFinancialDTO> dataFinancialDTOList = data.stream()
                .map(GroupFinancialMapper.INSTANCE::toGetGroupFinancialDTO)
                .toList();

        return new TimeSummaryDTO(Collections.singletonList(dataFinancialDTOList), dataFinancialDTOList.size());
    }


}
