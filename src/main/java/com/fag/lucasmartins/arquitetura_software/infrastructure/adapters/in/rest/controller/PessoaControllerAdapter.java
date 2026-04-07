package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.controller;

import com.fag.lucasmartins.arquitetura_software.application.ports.in.service.PessoaServicePort;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PessoaBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.dto.PessoaDTO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.mapper.PessoaDTOMapper;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pessoas") 
public class PessoaControllerAdapter {

    private final PessoaServicePort pessoaServicePort;

    public PessoaControllerAdapter(PessoaServicePort pessoaServicePort) {
        this.pessoaServicePort = pessoaServicePort;
    }

    @PostMapping
    public ResponseEntity<PessoaDTO> cadastrarPessoa(@RequestBody PessoaDTO pessoaDTO) {
        // 1. Recebe DTO, tenta converter em BO. Se o BO for inválido (menor de idade, sem @), já explode a DomainException aqui
        PessoaBO pessoaBO = PessoaDTOMapper.toBo(pessoaDTO);

        // 2. BO é válido! Passa pra porta de entrada
        PessoaBO pessoaCriadaBO = pessoaServicePort.cadastrarPessoa(pessoaBO);

        // 3. Converte volta para DTO pra não mostrar o núcleo do negócio pra API
        PessoaDTO pessoaCriadaDTO = PessoaDTOMapper.toDto(pessoaCriadaBO);

        return ResponseEntity.status(201).body(pessoaCriadaDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> buscarPorId(@PathVariable UUID id) {
        PessoaBO pessoaBO = pessoaServicePort.buscarPessoaPorId(id);
        return ResponseEntity.ok(PessoaDTOMapper.toDto(pessoaBO));
    }

    @GetMapping
    public ResponseEntity<List<PessoaDTO>> listarTodas() {
        List<PessoaDTO> listaDTO = pessoaServicePort.listarTodasAsPessoas()
                .stream()
                .map(PessoaDTOMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(listaDTO);
    }
}
