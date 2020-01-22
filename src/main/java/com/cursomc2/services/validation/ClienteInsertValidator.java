package com.cursomc2.services.validation;

import com.cursomc2.domain.Cliente;
import com.cursomc2.domain.enums.TipoCliente;
import com.cursomc2.dto.ClienteNewDTO;
import com.cursomc2.repositories.ClienteRepository;
import com.cursomc2.resources.exception.FieldMessage;
import com.cursomc2.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteInsert ann) {
    }

    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CPF Inválido"));
        }
        if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ Inválido"));
        }

        List<Cliente> aux = clienteRepository.findByEmail(objDto.getEmail());
        if (aux.size() > 0) {
            list.add(new FieldMessage("email", "Email já existe"));
        }
        List<Cliente> auxTipo = clienteRepository.findByCpfOuCnpj(objDto.getCpfOuCnpj());
        if (auxTipo.size() > 0) {
            list.add(new FieldMessage("cpfOuCnpj", "CPF/CNPJ já cadastrado"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}