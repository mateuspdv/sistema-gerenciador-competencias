package br.com.coresgc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.coresgc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContributorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContributorDTO.class);
        ContributorDTO contributorDTO1 = new ContributorDTO();
        contributorDTO1.setId(1L);
        ContributorDTO contributorDTO2 = new ContributorDTO();
        assertThat(contributorDTO1).isNotEqualTo(contributorDTO2);
        contributorDTO2.setId(contributorDTO1.getId());
        assertThat(contributorDTO1).isEqualTo(contributorDTO2);
        contributorDTO2.setId(2L);
        assertThat(contributorDTO1).isNotEqualTo(contributorDTO2);
        contributorDTO1.setId(null);
        assertThat(contributorDTO1).isNotEqualTo(contributorDTO2);
    }

}
