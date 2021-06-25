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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pedro.sistemapedido.model.MyUserDetails;
import com.pedro.sistemapedido.model.Pedido;
import com.pedro.sistemapedido.model.ResponseJson;
import com.pedro.sistemapedido.repository.ClienteRepository;
import com.pedro.sistemapedido.repository.ItemPedidoRepository;
import com.pedro.sistemapedido.repository.PedidoRepository;
import com.pedro.sistemapedido.repository.ProdutoRepository;

@Controller
@RequestMapping("/pedidos")
public class PedidosController {

	@Autowired
	PedidoRepository pedidoRepo;
	@Autowired
	ClienteRepository clienteRepo;
	@Autowired
	ProdutoRepository produtoRepo;
	@Autowired
	ItemPedidoRepository itemPedidoRepo;
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView abrir(@AuthenticationPrincipal MyUserDetails user) {
		ModelAndView view = new ModelAndView("pedido/pedido");
		
		return view.addObject("user", user);
	}
	
	@GetMapping("/buscar")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView buscar() {
		ModelAndView view = new ModelAndView("pedido/tabela_pedido");
		
		List<Pedido> lista = pedidoRepo.findAll();		
		
		return view.addObject("lista", lista);
	}
	
	@GetMapping(value = {"/modal", "/modal/{pesquisa}"})
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView modal(@PathVariable Optional<String> pesquisa) {
		ModelAndView view = new ModelAndView("pedido/modal_pedido");	
		
		if(pesquisa.isPresent()) {
			
			Pedido pedido = pedidoRepo.findById(Long.parseLong(pesquisa.get())).get();
			
			return view.addObject("pedido", pedido);
		}else {
			return view.addObject("pedido", new Pedido());
		}
	}
	
	@PostMapping(value = "/salvar", consumes = "application/json", headers = "content-type=application/x-www-form-urlencoded")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ResponseJson> salvarJSON(@AuthenticationPrincipal MyUserDetails usuarioSistema,
			@RequestBody Pedido pedido) {
		ResponseJson response = new ResponseJson();
		
		try {		
			Date date= new Date();
			long time = date.getTime();
			Timestamp ts = new Timestamp(time);
			
			for(int i = 0; i < pedido.getItensPedido().size(); i++) {
				pedido.getItensPedido().get(i).setProduto(produtoRepo.findById(pedido.getItensPedido().get(i).getProduto().getId()).get());
				pedido.getItensPedido().get(i).setDataCriacao(ts);
			}
			
			itemPedidoRepo.saveAll(pedido.getItensPedido());
			
			pedido.setCliente(clienteRepo.findById(pedido.getCliente().getId()).get());
			pedido.setDataCriacao(ts);
			
			pedidoRepo.save(pedido);
			
			return new ResponseEntity<>(response,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(response,HttpStatus.NOT_ACCEPTABLE);
		}
	}
}
