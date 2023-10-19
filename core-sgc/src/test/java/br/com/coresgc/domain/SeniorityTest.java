package br.com.coresgc.domain;

import br.com.coresgc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SeniorityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Seniority.class);
        Seniority seniority1 = new Seniority();
        seniority1.setId(1L);
        Seniority seniority2 = new Seniority();
        seniority2.setId(seniority1.getId());
        assertThat(seniority1).isEqualTo(seniority2);
        seniority2.setId(2L);
        assertThat(seniority1).isNotEqualTo(seniority2);
        seniority1.setId(null);
        assertThat(seniority1).isNotEqualTo(seniority2);
    }

}
