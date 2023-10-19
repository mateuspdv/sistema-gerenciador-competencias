package br.com.coresgc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.coresgc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SeniorityDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeniorityDTO.class);
        SeniorityDTO seniorityDTO1 = new SeniorityDTO();
        seniorityDTO1.setId(1L);
        SeniorityDTO seniorityDTO2 = new SeniorityDTO();
        assertThat(seniorityDTO1).isNotEqualTo(seniorityDTO2);
        seniorityDTO2.setId(seniorityDTO1.getId());
        assertThat(seniorityDTO1).isEqualTo(seniorityDTO2);
        seniorityDTO2.setId(2L);
        assertThat(seniorityDTO1).isNotEqualTo(seniorityDTO2);
        seniorityDTO1.setId(null);
        assertThat(seniorityDTO1).isNotEqualTo(seniorityDTO2);
    }

}
