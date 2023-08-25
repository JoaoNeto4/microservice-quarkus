package org.br.mineradora.message;

import org.br.mineradora.dto.ProposalDTO;
import org.br.mineradora.dto.QuotationDTO;
import org.br.mineradora.service.OpportunityService;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;



public class KafkaEvent {
    
    private final Logger LOG = LoggerFactory.getLogger(KafkaEvent.class);

    @Inject
    OpportunityService opportunityService;


    @Incoming("proposal")
    @Transactional
    public void receivedProposal(ProposalDTO proposal){
        LOG.info("-- Recebendo Nova Proposta do tópico kafka --");
        opportunityService.buildOpportunity(proposal);
    }

    
    @Incoming("quotation")
    @Blocking
    public void receiveQuotation(QuotationDTO quotation){
        LOG.info("-- Recebendo Nova Cotação de moeda do tópico kafka --");
        opportunityService.saveQuotation(quotation);
    }


}
