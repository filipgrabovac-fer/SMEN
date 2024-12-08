package com.smen.Services;

import com.smen.Models.Proposition;
import com.smen.Repositories.IPropositionRepository;
import org.springframework.stereotype.Service;

@Service
public class PropositionService extends BaseEntityService<Proposition, Long>{

    private final IPropositionRepository propositionRepository;

    public PropositionService(IPropositionRepository propositionRepository) {
        super(propositionRepository);
        this.propositionRepository = propositionRepository;
    }


}
