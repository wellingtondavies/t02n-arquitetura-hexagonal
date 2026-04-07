package com.fag.lucasmartins.arquitetura_software.core.domain.bo;

import com.fag.lucasmartins.arquitetura_software.core.domain.exceptions.DomainException;
import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

public class PessoaBO {

    private UUID id;
    private String nomeCompleto;
    private String cpf;
    private LocalDate dataNascimento;
    private String email;
    private String telefone;

    public PessoaBO(UUID id, String nomeCompleto, String cpf, LocalDate dataNascimento, String email, String telefone) {
        String nomeNormalizado = normalizarTexto(nomeCompleto);
        String cpfNormalizado = normalizarDigitos(cpf);
        String emailNormalizado = normalizarTexto(email);
        String telefoneNormalizado = normalizarDigitos(telefone);

        validarNome(nomeNormalizado);
        validarCpf(cpfNormalizado);
        validarIdade(dataNascimento);
        validarEmail(emailNormalizado);
        validarTelefone(telefoneNormalizado);

        this.id = id != null ? id : UUID.randomUUID();
        this.nomeCompleto = nomeNormalizado;
        this.cpf = cpfNormalizado;
        this.dataNascimento = dataNascimento;
        this.email = emailNormalizado;
        this.telefone = telefoneNormalizado;
    }

    private String normalizarTexto(String valor) {
        return valor == null ? null : valor.trim();
    }

    private String normalizarDigitos(String valor) {
        return valor == null ? null : valor.replaceAll("\\D", "");
    }

    private void validarNome(String nomeCompleto) {
        if (nomeCompleto == null || nomeCompleto.isEmpty()) {
            throw new DomainException("Nome completo é obrigatório.");
        }
    }

    private void validarIdade(LocalDate dataNascimento) {
        if (dataNascimento == null) {
            throw new DomainException("A data de nascimento é obrigatória.");
        }
        int idadeCalculada = Period.between(dataNascimento, LocalDate.now()).getYears();
        if (idadeCalculada < 18) {
            throw new DomainException("O cliente deve ter no mínimo 18 anos.");
        }
    }

    private void validarCpf(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            throw new DomainException("CPF é obrigatório.");
        }

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            throw new DomainException("O CPF deve conter exatamente 11 caracteres.");
        }

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * (10 - i);
        }
        int resto = 11 - (soma % 11);
        int digito1 = (resto >= 10) ? 0 : resto;

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * (11 - i);
        }
        resto = 11 - (soma % 11);
        int digito2 = (resto >= 10) ? 0 : resto;

        if (digito1 != (cpf.charAt(9) - '0') || digito2 != (cpf.charAt(10) - '0')) {
            throw new DomainException("Dígitos verificadores do CPF não conferem.");
        }
    }

    private void validarEmail(String email) {
        if (email == null || email.isEmpty() || !email.contains("@")) {
            throw new DomainException("O e-mail é obrigatório e deve ser válido (conter '@').");
        }
    }

    private void validarTelefone(String telefone) {
        if (telefone == null || !telefone.matches("\\d{11}")) {
            throw new DomainException("O telefone deve possuir 11 dígitos (sem parenteses ou traços).");
        }
    }

    public UUID getId() { return id; }
    public String getNomeCompleto() { return nomeCompleto; }
    public String getCpf() { return cpf; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
}
