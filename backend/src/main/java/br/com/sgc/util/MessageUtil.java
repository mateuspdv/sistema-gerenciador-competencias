package br.com.sgc.util;

public class MessageUtil {

    /* Messages for entities not found */
    public static final String CATEGORY_NOT_FOUND = "Categoria não encontrada!";
    public static final String COMPETENCY_NOT_FOUND = "Competência não encontrada!";

    /* Messages for ApiOperation annotation */
    public static final String OPERATION_CATEGORY_FIND_ALL = "Retorna todas as categorias cadastradas no sistema";
    public static final String OPERATION_CATEGORY_FIND_BY_ID = "Retorna uma categoria cadastrada no sistema pelo id";
    public static final String OPERATION_COMPETENCY_FIND_ALL = "Retorna todas as competências cadastradas no sistema";
    public static final String OPERATION_COMPETENCY_FIND_BY_ID = "Retorna uma competência cadastrada no sistema pelo id";
    public static final String OPERATION_COMPETENCY_SAVE = "Cadastra uma competência no sistema";
    public static final String OPERATION_COMPETENCY_UPDATE = "Atualiza uma competência no sistema";
    public static final String OPERATION_COMPETENCY_DELETE_BY_ID = "Deleta uma competência do sistema pelo id";

    /* Messages for validation in dto */
    public static final String VALIDATION_COMPETENCY_NAME_NOT_NULL = "O campo nome não pode ser nulo!";
    public static final String VALIDATION_COMPETENCY_NAME_NOT_EMPTY = "O compo nome não pode ser vazio!";
    public static final String VALIDATION_COMPETENCY_NAME_SIZE_MIN = "O campo nome deve possuir no mínimo 3 caracteres!";
    public static final String VALIDATION_COMPETENCY_NAME_SIZE_MAX = "O campo nome deve possuir no máximo 50 caracteres!";
    public static final String VALIDATION_COMPETENCY_DESCRIPTION_NOT_NULL = "O campo descrição não pode ser nulo!";
    public static final String VALIDATION_COMPETENCY_DESCRIPTION_NOT_EMPTY = "O compo descrição não pode ser vazio!";
    public static final String VALIDATION_COMPETENCY_DESCRIPTION_SIZE_MIN = "O campo descrição deve possuir no mínimo 10 caracteres!";
    public static final String VALIDATION_COMPETENCY_DESCRIPTION_SIZE_MAX = "O campo descrição deve possuir no máximo 100 caracteres!";
    public static final String VALIDATION_COMPETENCY_ID_CATEGORY_NOT_NULL = "O campo categoria não pode ser nulo!";

}
