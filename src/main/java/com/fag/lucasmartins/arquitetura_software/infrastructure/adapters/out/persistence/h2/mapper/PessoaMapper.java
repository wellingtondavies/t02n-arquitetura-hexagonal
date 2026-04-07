package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.mapper;

import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PessoaBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.entity.PessoaEntity;
import java.util.UUID;

public class PessoaMapper {

    private PessoaMapper() {
    }

    public static PessoaEntity toEntity(PessoaBO pessoaBO) {
        PessoaEntity entity = new PessoaEntity();
        entity.setId(pessoaBO.getId() != null ? pessoaBO.getId().toString() : null);
        entity.setNomeCompleto(pessoaBO.getNomeCompleto());
        entity.setCpf(pessoaBO.getCpf());
        entity.setDataNascimento(pessoaBO.getDataNascimento());
        entity.setEmail(pessoaBO.getEmail());
        entity.setTelefone(pessoaBO.getTelefone());
        return entity;
    }

    public static PessoaBO toBO(PessoaEntity entity) {
        UUID id = entity.getId() != null ? UUID.fromString(entity.getId()) : null;
        return new PessoaBO(
            id,
            entity.getNomeCompleto(),
            entity.getCpf(),
            entity.getDataNascimento(),
            entity.getEmail(),
            entity.getTelefone()
        );
    }
}