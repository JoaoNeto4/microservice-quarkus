package org.br.mineradora.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.br.mineradora.client.CurrencyPriceClient;
import org.br.mineradora.dto.CurrencyPriceDTO;
import org.br.mineradora.dto.QuotationDTO;
import org.br.mineradora.entity.QuotationEntity;
import org.br.mineradora.message.KafkaEvents;
import org.br.mineradora.repository.QuotationRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class QuotationService {

	
	@Inject
	@RestClient
	CurrencyPriceClient currencyPriceClient;
	
	@Inject
	QuotationRepository quotationRepository;
	
	@Inject
	KafkaEvents kafkaEvents;
	
	
	public void getCurrencyPrice() {
		/* only sends information to Kafka if 
		 * the dollar exchange rate is higher 
		 * than the one in the database*/

		CurrencyPriceDTO currencyPriceInfo = currencyPriceClient.getPriceByPair("USD-BRL");

		if (updateCurrentInfoPrice(currencyPriceInfo)) {
			kafkaEvents.sendNewKafkaEvent(QuotationDTO
					.builder()
					.currencyPrivce(new BigDecimal(currencyPriceInfo.getUSDBRL().getBid()))
					.date(new Date())
					.build());
		}
	}
	
	private Boolean updateCurrentInfoPrice(CurrencyPriceDTO currencyPriceInfo) {
		
		BigDecimal currentPrice = new BigDecimal(currencyPriceInfo.getUSDBRL().getBid());
		Boolean updatePrice = false;
		
		List<QuotationEntity> quotationList = quotationRepository.findAll().list();
		
		
		if (quotationList.isEmpty()){
			
			saveQuotation(currencyPriceInfo);
			updatePrice = true;
			
		} else {
			QuotationEntity lastDollarPrice = quotationList
					.get(quotationList.size() -1);
					//3 positions; 0,1,2 indices
			
			if (currentPrice.floatValue() > lastDollarPrice.getCurrencyPrice().floatValue()) {
				updatePrice = true;
				saveQuotation(currencyPriceInfo);
			}
		}
		
		return updatePrice;
	}
	
	
	private void saveQuotation(CurrencyPriceDTO currencyInfo) {
		
		QuotationEntity quotation = new QuotationEntity();
		
		quotation.setDate(new Date());
		quotation.setCurrencyPrice(new BigDecimal(currencyInfo.getUSDBRL().getBid()));
		quotation.setPctChange(currencyInfo.getUSDBRL().getPctChange());
		quotation.setPair("USD-BRL");
		
		quotationRepository.persist(quotation);
	}
}
