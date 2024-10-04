package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Sale result = repository.findById(id).orElseThrow();
		return new SaleMinDTO(result);
	}

	public Page<SaleReportDTO> findAllReports(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable) {

		if (minDate == null) {
			minDate = LocalDate.now().minusYears(2); // Coloquei 2 anos para apareçer alguma coisa
		}
		if (maxDate == null) {
			maxDate =  LocalDate.now();
		}

		Page<Sale> salePage = repository.searchAllReports(minDate, maxDate, name, pageable);
		return salePage.map(SaleReportDTO::new);
	}

	public List<SaleSummaryDTO> findAllSummary(LocalDate minDate, LocalDate maxDate) {

		if (minDate == null) {
			minDate = LocalDate.now().minusYears(2); // Coloquei 2 anos para apareçer alguma coisa
		}
		if (maxDate == null) {
			maxDate =  LocalDate.now();
		}

        return repository.searchAllSummary(minDate, maxDate);
	}
}
