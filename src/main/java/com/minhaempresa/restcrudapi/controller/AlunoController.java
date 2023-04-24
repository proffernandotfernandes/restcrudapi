package com.minhaempresa.restcrudapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.minhaempresa.restcrudapi.model.Aluno;
import com.minhaempresa.restcrudapi.repository.AlunoRepository;

@RestController
@RequestMapping("/alunos")
public class AlunoController {
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@GetMapping
	public List<Aluno> listar() {
		
		return alunoRepository.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Aluno adicionar(@RequestBody Aluno aluno) {
		
		return alunoRepository.save(aluno);
	}
	
	
	@PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizar(@PathVariable Long id, @RequestBody Aluno aluno) {
		//findById retorna um objeto SE existir, se não cria e insere 
		Aluno updatedAluno = alunoRepository.findById(id)
                .map(c -> {
                    c.setNome(aluno.getNome());
                    c.setNota(aluno.getNota());
                    return alunoRepository.save(c);
                })
                .orElseGet(() -> {
                	aluno.setIdAluno(id);
                    return alunoRepository.save(aluno);
                });
		
        return new ResponseEntity<>(updatedAluno, HttpStatus.OK);
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
		alunoRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
}
