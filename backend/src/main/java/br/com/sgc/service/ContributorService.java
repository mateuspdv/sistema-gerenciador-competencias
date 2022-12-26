package br.com.sgc.service;

import br.com.sgc.service.dto.ContributorDto;

import java.util.List;

public interface ContributorService {

    List<ContributorDto> findAll();

    ContributorDto findById(Long idContributor);

    ContributorDto create(ContributorDto contributorDto);

    ContributorDto update(ContributorDto contributorDto);

    void deleteById(Long idContributor);

}
