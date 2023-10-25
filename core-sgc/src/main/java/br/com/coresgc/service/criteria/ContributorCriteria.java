package br.com.coresgc.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link br.com.coresgc.domain.Contributor} entity. This class is used
 * in {@link br.com.coresgc.web.rest.ContributorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /contributors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContributorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter lastName;

    private StringFilter cpf;

    private StringFilter email;

    private LocalDateFilter birthDate;

    private LocalDateFilter admissionDate;

    private ZonedDateTimeFilter creationDate;

    private ZonedDateTimeFilter lastUpdateDate;

    private LongFilter seniorityId;

    private LongFilter competencesId;

    private Boolean distinct;

    public ContributorCriteria() {}

    public ContributorCriteria(ContributorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.cpf = other.cpf == null ? null : other.cpf.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.birthDate = other.birthDate == null ? null : other.birthDate.copy();
        this.admissionDate = other.admissionDate == null ? null : other.admissionDate.copy();
        this.creationDate = other.creationDate == null ? null : other.creationDate.copy();
        this.lastUpdateDate = other.lastUpdateDate == null ? null : other.lastUpdateDate.copy();
        this.seniorityId = other.seniorityId == null ? null : other.seniorityId.copy();
        this.competencesId = other.competencesId == null ? null : other.competencesId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ContributorCriteria copy() {
        return new ContributorCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public StringFilter lastName() {
        if (lastName == null) {
            lastName = new StringFilter();
        }
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getCpf() {
        return cpf;
    }

    public StringFilter cpf() {
        if (cpf == null) {
            cpf = new StringFilter();
        }
        return cpf;
    }

    public void setCpf(StringFilter cpf) {
        this.cpf = cpf;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public LocalDateFilter getBirthDate() {
        return birthDate;
    }

    public LocalDateFilter birthDate() {
        if (birthDate == null) {
            birthDate = new LocalDateFilter();
        }
        return birthDate;
    }

    public void setBirthDate(LocalDateFilter birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDateFilter getAdmissionDate() {
        return admissionDate;
    }

    public LocalDateFilter admissionDate() {
        if (admissionDate == null) {
            admissionDate = new LocalDateFilter();
        }
        return admissionDate;
    }

    public void setAdmissionDate(LocalDateFilter admissionDate) {
        this.admissionDate = admissionDate;
    }

    public ZonedDateTimeFilter getCreationDate() {
        return creationDate;
    }

    public ZonedDateTimeFilter creationDate() {
        if (creationDate == null) {
            creationDate = new ZonedDateTimeFilter();
        }
        return creationDate;
    }

    public void setCreationDate(ZonedDateTimeFilter creationDate) {
        this.creationDate = creationDate;
    }

    public ZonedDateTimeFilter getLastUpdateDate() {
        return lastUpdateDate;
    }

    public ZonedDateTimeFilter lastUpdateDate() {
        if (lastUpdateDate == null) {
            lastUpdateDate = new ZonedDateTimeFilter();
        }
        return lastUpdateDate;
    }

    public void setLastUpdateDate(ZonedDateTimeFilter lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public LongFilter getSeniorityId() {
        return seniorityId;
    }

    public LongFilter seniorityId() {
        if (seniorityId == null) {
            seniorityId = new LongFilter();
        }
        return seniorityId;
    }

    public void setSeniorityId(LongFilter seniorityId) {
        this.seniorityId = seniorityId;
    }

    public LongFilter getCompetencesId() {
        return competencesId;
    }

    public LongFilter competencesId() {
        if (competencesId == null) {
            competencesId = new LongFilter();
        }
        return competencesId;
    }

    public void setCompetencesId(LongFilter competencesId) {
        this.competencesId = competencesId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ContributorCriteria that = (ContributorCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(cpf, that.cpf) &&
            Objects.equals(email, that.email) &&
            Objects.equals(birthDate, that.birthDate) &&
            Objects.equals(admissionDate, that.admissionDate) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(lastUpdateDate, that.lastUpdateDate) &&
            Objects.equals(seniorityId, that.seniorityId) &&
            Objects.equals(competencesId, that.competencesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            lastName,
            cpf,
            email,
            birthDate,
            admissionDate,
            creationDate,
            lastUpdateDate,
            seniorityId,
            competencesId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContributorCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (lastName != null ? "lastName=" + lastName + ", " : "") +
            (cpf != null ? "cpf=" + cpf + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (birthDate != null ? "birthDate=" + birthDate + ", " : "") +
            (admissionDate != null ? "admissionDate=" + admissionDate + ", " : "") +
            (creationDate != null ? "creationDate=" + creationDate + ", " : "") +
            (lastUpdateDate != null ? "lastUpdateDate=" + lastUpdateDate + ", " : "") +
            (seniorityId != null ? "seniorityId=" + seniorityId + ", " : "") +
            (competencesId != null ? "competencesId=" + competencesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
