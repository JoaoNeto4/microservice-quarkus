package org.br.mineradora.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="quatation")
@Data
@NoArgsConstructor
public class QuotationEntity {

	@Id
	@GeneratedValue
	private Long id;
	
	private Date date;
	
	@Column(name="currency_price")
	private BigDecimal currencyPrice;
	
	@Column(name="pct_change")
	private String pctChange;
	
	private String pair;
	
}
