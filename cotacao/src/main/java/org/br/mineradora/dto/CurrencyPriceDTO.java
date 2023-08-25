package org.br.mineradora.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

/*
 * although the @Jacksonized 
 * annotation suggests adding 
 * @Builder, PROBLEM
 */
@Jacksonized
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyPriceDTO {
	
	
	public USDBRL USDBRL;

}
