package br.com.mercadocancelier.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.mercadocancelier.entity.Fornecedor;
import br.com.mercadocancelier.repository.FornecedoresRepositoy;
import br.com.mercadocancelier.service.FornecedorService;
import jakarta.transaction.Transactional;

@Service
public class FornecedorServiceImpl implements FornecedorService {
	
	@Autowired	
	private FornecedoresRepositoy repository;

	@Override
	public Fornecedor salvar(Fornecedor fornecedor) {
		Fornecedor outroFornecedor = repository.buscarPorCnpj(fornecedor.getCnpj());
		if (outroFornecedor != null) {
			if (outroFornecedor.isPersistido()) {
				Preconditions.checkArgument(outroFornecedor.equals(fornecedor), 
						"O CNPJ do fornecedor já esta em uso.");
			}
		}
		Fornecedor fornecedorSalvo = repository.save(fornecedor);
		return fornecedorSalvo;
	}

	@Override
	public Fornecedor buscarPor(Integer id) {
		Fornecedor fornecedorEncontrado = repository.buscarPor(id);
		Preconditions.checkNotNull(fornecedorEncontrado, "Não foi encontrado produto para o id informado");
		return fornecedorEncontrado ;
	}

	@Override
	public Fornecedor alterar(Fornecedor fornecedor) {
		Fornecedor fornecedorSalvo = repository.buscarPor(fornecedor.getId());
		fornecedorSalvo.setNome(fornecedor.getNome());
		fornecedorSalvo.setCnpj(fornecedor.getCnpj());
		fornecedorSalvo.setStatus(fornecedorSalvo.getStatus());
		Fornecedor fornecedorAtualizado = repository.saveAndFlush(fornecedorSalvo);
		return buscarPor(fornecedorAtualizado.getId());
	}

	@Override
	public List<Fornecedor> listarPor(String nome) {
		return repository.listarPor(nome);
	}
	
	@Override
	public List<Fornecedor> listarTodos() {
		return repository.listarTodos();
	}
	
	@Transactional
	public Integer inativarPor(Integer id) {
		Preconditions.checkNotNull(id, "O fornecedor é obrigatório");
		Integer fornecedorInvativado = repository.inativarPor(id);
		return fornecedorInvativado;
	}
	
	@Transactional
	public Integer ativarPor(Integer id) {
		Preconditions.checkNotNull(id, "O fornecedor é obrigatório");
		Integer fornecedorAtivado = repository.ativarPor(id);
		return fornecedorAtivado;
	}


}
