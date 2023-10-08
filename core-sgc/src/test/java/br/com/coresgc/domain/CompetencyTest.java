package br.com.coresgc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.coresgc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompetencyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Competency.class);
        Competency competency1 = new Competency();
        competency1.setId(1L);
        Competency competency2 = new Competency();
        competency2.setId(competency1.getId());
        assertThat(competency1).isEqualTo(competency2);
        competency2.setId(2L);
        assertThat(competency1).isNotEqualTo(competency2);
        competency1.setId(null);
        assertThat(competency1).isNotEqualTo(competency2);
    }
}
