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
import com.pedro.sistemapedido.repository.ClienteRepository;

@Controller
@RequestMapping("/clientes")
public class ClientesController {
	
	@Autowired
	ClienteRepository clienteRepo;

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView abrir(@AuthenticationPrincipal MyUserDetails user) {
		ModelAndView view = new ModelAndView("cliente/cliente");
		
		return view.addObject("user", user);
	}
	
	@GetMapping("/buscar")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView buscar() {
		ModelAndView view = new ModelAndView("cliente/tabela_cliente");
		
		List<Cliente> lista = clienteRepo.findAll();		
		
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
		lista = clienteRepo.findByNomeAutoComplete(pesquisa);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@GetMapping(value = {"/modal", "/modal/{pesquisa}"})
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView modal(@PathVariable Optional<String> pesquisa) {
		ModelAndView view = new ModelAndView("cliente/modal_cliente");	
		
		if(pesquisa.isPresent()) {
			
			Cliente cliente = clienteRepo.findById(Long.parseLong(pesquisa.get())).get();
			
			return view.addObject("cliente", cliente);
		}else {
			return view.addObject("cliente", new Cliente());
		}
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public String salvar(@AuthenticationPrincipal MyUserDetails usuarioSistema, Cliente cliente) {
			try {
				Date date= new Date();
				long time = date.getTime();
				Timestamp ts = new Timestamp(time);
				
				cliente.setDataCriacao(ts);
				
				clienteRepo.save(cliente);
				
				return "redirect:/clientes";
				
			} catch (Exception e) {
				return "redirect:/clientes";
			}		
	}
	
	@GetMapping("/delete/{idCliente}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> delete(@AuthenticationPrincipal MyUserDetails usuarioSistema,@PathVariable String idCliente) {
		try {
			Cliente clienteTemp = clienteRepo.findById(Long.parseLong(idCliente)).get();
			
			clienteRepo.delete(clienteTemp);
						
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
		}
		
	}
}
