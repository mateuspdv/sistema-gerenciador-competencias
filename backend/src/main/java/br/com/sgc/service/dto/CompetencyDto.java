package br.com.sgc.service.dto;

import br.com.sgc.util.MessageUtil;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class CompetencyDto implements Serializable {

    private Long id;

    @NotNull(message = MessageUtil.VALIDATION_COMPETENCY_NAME_NOT_NULL)
    @NotEmpty(message = MessageUtil.VALIDATION_COMPETENCY_NAME_NOT_EMPTY)
    @Size(min = 3, message = MessageUtil.VALIDATION_COMPETENCY_NAME_SIZE_MIN)
    @Size(max = 50, message = MessageUtil.VALIDATION_COMPETENCY_NAME_SIZE_MAX)
    private String name;

    @NotNull(message = MessageUtil.VALIDATION_COMPETENCY_DESCRIPTION_NOT_NULL)
    @NotEmpty(message = MessageUtil.VALIDATION_COMPETENCY_DESCRIPTION_NOT_EMPTY)
    @Size(min = 3, message = MessageUtil.VALIDATION_COMPETENCY_DESCRIPTION_SIZE_MIN)
    @Size(max = 50, message = MessageUtil.VALIDATION_COMPETENCY_DESCRIPTION_SIZE_MAX)
    private String description;

    @NotNull(message = MessageUtil.VALIDATION_COMPETENCY_ID_CATEGORY_NOT_NULL)
    private Long idCategory;

}
