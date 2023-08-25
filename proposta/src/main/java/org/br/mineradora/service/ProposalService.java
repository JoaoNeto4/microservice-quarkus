package org.br.mineradora.service;

import org.br.mineradora.dto.ProposalDetailsDTO;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface ProposalService {


    ProposalDetailsDTO findFullProposal(Long id);

    void createNewProposal(ProposalDetailsDTO proposalDetailsDTO);

    void removeProposal(Long id);
    
}
