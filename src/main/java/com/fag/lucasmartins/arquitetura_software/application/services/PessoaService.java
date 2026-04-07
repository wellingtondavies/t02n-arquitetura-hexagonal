package com.fag.lucasmartins.arquitetura_software.application.services;

import com.fag.lucasmartins.arquitetura_software.application.ports.in.service.PessoaServicePort;
import com.fag.lucasmartins.arquitetura_software.application.ports.out.persistence.h2.PessoaRepositoryPort;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PessoaBO;
import com.fag.lucasmartins.arquitetura_software.core.domain.exceptions.DomainException;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PessoaService implements PessoaServicePort {

    private final PessoaRepositoryPort pessoaRepositoryPort;

    public PessoaService(PessoaRepositoryPort pessoaRepositoryPort) {
        this.pessoaRepositoryPort = pessoaRepositoryPort;
    }

    @Override
    public List<PessoaBO> listarTodasAsPessoas() {
        return pessoaRepositoryPort.listarTodas();
    }

    @Override
    public PessoaBO buscarPessoaPorId(UUID id) {
        return pessoaRepositoryPort
                .buscarPorId(id)
                .orElseThrow(() -> new DomainException("Pessoa não encontrada para o id informado."));
    }

    @Override
    public PessoaBO cadastrarPessoa(PessoaBO pessoaBO) {
        return pessoaRepositoryPort.salvar(pessoaBO);
    }
}
