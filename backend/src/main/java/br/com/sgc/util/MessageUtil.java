package br.com.sgc.util;

public class MessageUtil {

    private MessageUtil() {
        throw new IllegalStateException("Utility class!");
    }

    /* Messages for entities not found */
    public static final String CATEGORY_NOT_FOUND = "Categoria não encontrada!";
    public static final String COMPETENCY_NOT_FOUND = "Competência não encontrada!";
    public static final String SENIORITY_NOT_FOUND = "Senioridade não encontrada!";
    public static final String CONTRIBUTOR_NOT_FOUND = "Colaborador não encontrado!";
    public static final String LEVEL_COMPETENCY_NOT_FOUND = "Nível da competência não encontrado!";

    /* Messages for ApiOperation annotation */
    public static final String OPERATION_CATEGORY_FIND_ALL = "Retorna todas as categorias cadastradas no sistema";
    public static final String OPERATION_CATEGORY_FIND_BY_ID = "Retorna uma categoria cadastrada no sistema pelo id";
    public static final String OPERATION_COMPETENCY_FIND_ALL = "Retorna todas as competências cadastradas no sistema";
    public static final String OPERATION_COMPETENCY_FIND_BY_ID = "Retorna uma competência cadastrada no sistema pelo id";
    public static final String OPERATION_COMPETENCY_SAVE = "Cadastra uma competência no sistema";
    public static final String OPERATION_COMPETENCY_UPDATE = "Atualiza uma competência no sistema";
    public static final String OPERATION_COMPETENCY_DELETE_BY_ID = "Deleta uma competência do sistema pelo id";
    public static final String OPERATION_COMPETENCY_GLOBAL_SEARCH_FILTER = "Retorna as competências relacionadas a uma pesquisa global";
    public static final String OPERATION_SENIORITY_FIND_ALL = "Retorna todas as senioridades cadastradas no sistema";
    public static final String OPERATION_SENIORITY_FIND_BY_ID = "Retorna uma senioridade cadastrada no sistema pelo id";
    public static final String OPERATION_CONTRIBUTOR_FIND_ALL = "Retorna todos os colaboradores cadastrados no sistema";
    public static final String OPERATION_CONTRIBUTOR_FIND_BY_ID = "Retorna um colaborador cadastrado no sistema pelo id";
    public static final String OPERATION_CONTRIBUTOR_CREATE = "Cadastra um colaborador no sistema";
    public static final String OPERATION_CONTRIBUTOR_UPDATE = "Atualiza um colaborador no sistema";
    public static final String OPERATION_CONTRIBUTOR_DELETE_BY_ID = "Deleta um colaborador do sistema pelo id";
    public static final String OPERATION_LEVEL_COMPETENCY_FIND_ALL = "Retorna todos os níveis de competência cadastrados no sistema";
    public static final String OPERATION_LEVEL_COMPETENCY_FIND_BY_ID = "Retorna um nível de competência cadastrado no sistema pelo id";

    /* Messages for validation in dto */
    public static final String VALIDATION_COMPETENCY_NAME_NOT_NULL = "O campo nome não pode ser nulo!";
    public static final String VALIDATION_COMPETENCY_NAME_NOT_EMPTY = "O compo nome não pode ser vazio!";
    public static final String VALIDATION_COMPETENCY_NAME_SIZE_MIN = "O campo nome deve possuir no mínimo 3 caracteres!";
    public static final String VALIDATION_COMPETENCY_NAME_SIZE_MAX = "O campo nome deve possuir no máximo 50 caracteres!";
    public static final String VALIDATION_COMPETENCY_DESCRIPTION_NOT_NULL = "O campo descrição não pode ser nulo!";
    public static final String VALIDATION_COMPETENCY_DESCRIPTION_NOT_EMPTY = "O compo descrição não pode ser vazio!";
    public static final String VALIDATION_COMPETENCY_DESCRIPTION_SIZE_MIN = "O campo descrição deve possuir no mínimo 3 caracteres!";
    public static final String VALIDATION_COMPETENCY_DESCRIPTION_SIZE_MAX = "O campo descrição deve possuir no máximo 50 caracteres!";
    public static final String VALIDATION_COMPETENCY_ID_CATEGORY_NOT_NULL = "O campo categoria não pode ser nulo!";
    public static final String VALIDATION_CONTRIBUTOR_FIRST_NAME_NOT_NULL = "O campo nome não pode ser nulo!";
    public static final String VALIDATION_CONTRIBUTOR_FIRST_NAME_NOT_EMPTY = "O campo nome não pode ser vazio!";
    public static final String VALIDATION_CONTRIBUTOR_FIRST_NAME_SIZE_MIN = "O campo nome deve possuir no mínimo 3 caracteres!";
    public static final String VALIDATION_CONTRIBUTOR_FIRST_NAME_SIZE_MAX = "O campo nome deve possuir no máximo 50 caracteres!";
    public static final String VALIDATION_CONTRIBUTOR_LAST_NAME_NOT_NULL = "O campo sobrenome não pode ser nulo!";
    public static final String VALIDATION_CONTRIBUTOR_LAST_NAME_NOT_EMPTY = "O campo sobrenome não pode ser vazio!";
    public static final String VALIDATION_CONTRIBUTOR_LAST_NAME_SIZE_MIN = "O campo sobrenome deve possuir no mínimo 3 caracteres!";
    public static final String VALIDATION_CONTRIBUTOR_LAST_NAME_SIZE_MAX = "O campo sobrenome deve possuir no máximo 50 caracteres!";
    public static final String VALIDATION_CONTRIBUTOR_CPF_NOT_NULL = "O campo CPF não pode ser nulo!";
    public static final String VALIDATION_CONTRIBUTOR_CPF_NOT_EMPTY = "O campo CPF não pode ser vazio!";
    public static final String VALIDATION_CONTRIBUTOR_CPF_CPF = "O campo CPF deve possuir um registro válido!";
    public static final String VALIDATION_CONTRIBUTOR_EMAIL_NOT_NULL = "O campo e-mail não pode ser nulo!";
    public static final String VALIDATION_CONTRIBUTOR_EMAIL_NOT_EMPTY = "O campo e-mail não pode ser vazio!";
    public static final String VALIDATION_CONTRIBUTOR_EMAIL_EMAIL = "O campo e-mail deve possuir um registro válido!";
    public static final String VALIDATION_CONTRIBUTOR_PHOTO_NOT_NULL = "O campo foto não pode ser nulo!";
    public static final String VALIDATION_CONTRIBUTOR_BIRTH_DATE_NOT_NULL = "O campo data de nascimento não pode ser nulo!";
    public static final String VALIDATION_CONTRIBUTOR_BIRTH_DATE_PAST = "O campo data de nascimento deve possuir uma data que se encontra no passado!";
    public static final String VALIDATION_CONTRIBUTOR_ADMISSION_DATE_NOT_NULL = "O campo data de admissão não pode ser nulo!";
    public static final String VALIDATION_CONTRIBUTOR_ADMISSION_DATE_PAST_OR_PRESENT = "O campo data de admissão deve possuir uma data que se encontra no passado ou no presente!";
    public static final String VALIDATION_CONTRIBUTOR_ID_SENIORITY_NOT_NULL = "O campo senioridade não pode ser nulo!";

    /* Messages for exceptions related to deleting entities */
    public static final String EXCEPTION_DELETE_COMPETENCY_LINK_CONTRIBUTOR = "Competência possui vínculo com a entidade Colaborador!";

}
