package com.pedro.sistemapedido.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pedro.sistemapedido.model.Cliente;
import com.pedro.sistemapedido.model.MyUserDetails;
import com.pedro.sistemapedido.model.Produto;
import com.pedro.sistemapedido.repository.ProdutoRepository;

@Controller
@RequestMapping("/produtos")
public class ProdutosController {
	
	@Autowired
	ProdutoRepository produtoRepo;
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView abrir(@AuthenticationPrincipal MyUserDetails user) {
		ModelAndView view = new ModelAndView("produtos/produtos");
		
		return view.addObject("user", user);
	}

	@GetMapping("/buscar")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView buscar() {
		ModelAndView view = new ModelAndView("produtos/tabela_produto");
		
		List<Produto> lista = produtoRepo.findAll();		
		
		return view.addObject("lista", lista);
	}
	
	@GetMapping(value = { "/filtrar/{nome}", "/filtrar" })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List> role(@PathVariable Optional<String> nome,@AuthenticationPrincipal MyUserDetails user) {
		List lista = null;
		String pesquisa = "";
		if (nome.isPresent()) {
			pesquisa = nome.get();
		}
		lista = produtoRepo.findByNomeAutoComplete(pesquisa);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@GetMapping(value = {"/modal", "/modal/{pesquisa}"})
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView modal(@PathVariable Optional<String> pesquisa) {
		ModelAndView view = new ModelAndView("produtos/modal_produtos");	
		
		if(pesquisa.isPresent()) {
			
			Produto produto = produtoRepo.findById(Long.parseLong(pesquisa.get())).get();
			
			return view.addObject("produto", produto);
		}else {
			return view.addObject("produto", new Produto());
		}
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public String salvar(@AuthenticationPrincipal MyUserDetails usuarioSistema, Produto produto) {
			try {
				Date date= new Date();
				long time = date.getTime();
				Timestamp ts = new Timestamp(time);
				
				produto.setDataCriacao(ts);
				
				produtoRepo.save(produto);
				
				return "redirect:/produtos";
				
			} catch (Exception e) {
				return "redirect:/produtos";
			}		
	}
	
	@GetMapping("/delete/{idProduto}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> delete(@AuthenticationPrincipal MyUserDetails usuarioSistema,@PathVariable String idProduto) {
		try {
			Produto prodTemp = produtoRepo.findById(Long.parseLong(idProduto)).get();
			
			produtoRepo.delete(prodTemp);
						
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
		}
		
	}
}
