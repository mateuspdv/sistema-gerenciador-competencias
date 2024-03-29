package br.com.coresgc.domain;

import br.com.coresgc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SkillTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Skill.class);
        Skill skill1 = new Skill();
        skill1.setId(1L);
        Skill skill2 = new Skill();
        skill2.setId(skill1.getId());
        assertThat(skill1).isEqualTo(skill2);
        skill2.setId(2L);
        assertThat(skill1).isNotEqualTo(skill2);
        skill1.setId(null);
        assertThat(skill1).isNotEqualTo(skill2);
    }

}
