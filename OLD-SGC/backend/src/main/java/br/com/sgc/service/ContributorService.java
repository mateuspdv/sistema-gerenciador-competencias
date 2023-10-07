package br.com.sgc.service;

import br.com.sgc.service.dto.ContributorDto;
import br.com.sgc.service.dto.ViewContributorDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContributorService {

    Page<ViewContributorDto> findAll(Pageable pageable);

    ContributorDto findById(Long idContributor);

    ContributorDto create(ContributorDto contributorDto);

    ContributorDto update(ContributorDto contributorDto);

    void deleteById(Long idContributor);

}
