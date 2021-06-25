var tabela_itens;

$(document).ready(function() {
    buscar(); 
    init_btns();
});

function init_btns(){
	$('#btnModal').click(function(){
		$.get('pedidos/modal',function(retorno){
			initForm(retorno,"Cadastro");
		});
	});	
	
}

function validarProduto(){
	 var retorno = true;
	 
	 if($('#idProduto').val() == ''){
		 retorno = false;
		 $('#produto').addClass("is-invalid");
	 }
	 
	 if($('#valorProd').val() == ''){
		 retorno = false;
		 $('#produto').addClass("is-invalid");
	 }
	 
	 if($('#produto').val() == ''){
		 retorno = false;
		 $('#produto').addClass("is-invalid");
	 }
	 
	 if($('#quantidade').val() == ''){
		 retorno = false;
		 $('#quantidade').addClass("is-invalid");
	 }
	 
	 return retorno;
}

function revalidar(id){
	$('#'+id).removeClass("is-invalid");
}

function initForm(retorno,tipo){
	$('#corpoModal').html(retorno);
	$('#motalTitulo').text(tipo+' - Pedidos');
	
	autoCompleteCliente();
	autoCompleteProduto();
	
	init_data_table_itens('tabela_itens_pedido');
	
	$('#addRow').click(function(){
		if(validarProduto()){
			tabela_itens.row.add( [
				$('#idProduto').val(),
	            $('#produto').val(),
	            $('#valorProd').val(),
	            $('#quantidade').val(),
	            Number(Number($('#valorProd').val()) * Number($('#quantidade').val())).toFixed(2),
	            '<i class="ico fa fa-trash" style = "cursor: pointer" title = "Excluir Item"></i>'	            
	        ] ).draw( false );
		}
	})
	
	$('#btnSalvar').click(function(){
		salvar();
	});
	
	$('#quantidade').change(function(){
		revalidar('quantidade');
	});
	
	$('#modal').modal('show');
}

function alterarItem(id){
	$.get('pedidos/modal/'+id,function(retorno){
		initForm(retorno,"Atualizar");
	});
}

function buscar(){
	var pesquisa;
	$.get('pedidos/buscar', pesquisa, function(dados) {
		
		$('#resultado').html(dados);
		init_data_table('tabela_pedido');
		
	});
}

function deletarItem(id){
	Swal.fire({
		title: "Tem certeza que deseja excluir o item? Não terá volta!",
		input: 'checkbox',
		icon: "warning",
		button: "ok!",
		showCloseButton: true,
		showCancelButton: true,
		inputPlaceholder:
		    'Eu concordo em perder permanentemente todos os dados referente à este item a ser excluido.',
		inputValidator: (result) => {
		    return !result && 'Você precisa concordar com o termo!'
		  },
		 preConfirm: () => {
			 $.get('pedidos/delete/'+id,function(){
				  buscar();
			  }).fail(function(){
				Swal.fire({
					  title: "Não foi possível deletar o Pedido!",
					  text: "Necessário retirar todas as dependências do pedido antes da deleção!!!",
					  icon: "error",
					  button: "ok!",
					  showCloseButton: true,
				  });
			});
			  }
	  });
}

function init_data_table_itens(idTabela){
	if($.fn.dataTable.isDataTable('#'+idTabela) ){
		$('#'+idTabela).dataTable().fnDestroy();
		init_data_table('tabela_itens_pedido');		
	}else{
		tabela_itens = $('#'+idTabela).DataTable({
			destroy : true,
	        scrollCollapse: true,
			ordering : true,
			info : true,
			searching : true,
			paging: false,	
			language: {
				url: '//cdn.datatables.net/plug-ins/1.10.24/i18n/Portuguese-Brasil.json'
	        },
	        columnDefs: [		
				{width: "60%", targets: 0},
				{
			        targets: [2,3,4],
			        className: 'dt-center'
			    }
            ],
            "footerCallback": function ( row, data, start, end, display ) {
                var api = this.api(), data;
     
                // Remove the formatting to get integer data for summation
                var intVal = function ( i ) {
                    return typeof i === 'string' ?
                        i.replace(/[\$,]/g, '')*1 :
                        typeof i === 'number' ?
                            i : 0;
                };
     
                // Total over all pages
                total = api
                    .column( 4 )
                    .data()
                    .reduce( function (a, b) {
                        return intVal(a) + intVal(b);
                    }, 0 );
     
                // Total over this page
                pageTotal = api
                    .column( 4, { page: 'current'} )
                    .data()
                    .reduce( function (a, b) {
                        return intVal(a) + intVal(b);
                    }, 0 );
     
                // Update footer
                $( api.column( 4 ).footer() ).html(
                    pageTotal
                );
            }
		});
	}
	
	$('#tabela_itens_pedido tbody').on( 'click', '.fa-trash', function () {
		tabela_itens.row( $(this).parents('tr') ).remove().draw();
	    
	} );
}

function init_data_table(idTabela){
	$('#'+idTabela+' tfoot th').each( function () {
        var title = $(this).text();
        if(title != 'Acao'){
        	$(this).html( '<input type="text" style = "width:100%" placeholder="'+title+'" />' );
        }else{
        	$(this).html('');
        }  
    } );
	
	if($.fn.dataTable.isDataTable('#'+idTabela) ){
		$('#'+idTabela).dataTable().fnDestroy();
		init_data_table('tabela_pedido');		
	}else{
		 $('#'+idTabela).DataTable({
			destroy : true,
	        scrollCollapse: true,
			ordering : true,
			info : true,
			searching : true,
			paging: true,	
			language: {
				url: '//cdn.datatables.net/plug-ins/1.10.24/i18n/Portuguese-Brasil.json'
	        },
	        columnDefs: [			        	
				{width: "40%", targets: 1},
				{
			        targets: [2,3],
			        className: 'dt-center'
			    },
			    {
                    render: function (data, type, full, meta) {
                    	var ts = data.split(' ');
                    	var dt = ts[0].split('-');
                        return dt[2]+'/'+dt[1]+'/'+dt[0]+' '+ts[1];
                    },
                    targets: 2
                },
            ],
			initComplete: function () {
	            // Apply the search
	            this.api().columns().every( function () {
	                var that = this;
	 
	                $( 'input', this.footer() ).on( 'keyup change clear', function () {
	                    if ( that.search() !== this.value ) {
	                        that.search( this.value ).draw();
	                    }
	                } );
	            } );
	        }
		});
	}
}

function autoCompleteCliente() {
	$('#cliente').easyAutocomplete({
		url : function(dado) {
			return 'clientes/filtrar/' + dado;
		},
		getValue : 'nome',
		minCharNumber : 1,
		requestDelay : 100,
		placeholder : "Digite um nome para pesquisar",
		adjustWidth : true,
		// cssClasses: '',
		list : {
			maxNumberOfElements : 10,
			showAnimation : {
				type : "fade", // normal|slide|fade
				time : 200,
				callback : function() {
				}
			},
			hideAnimation : {
				type : "slide", // normal|slide|fade
				time : 100,
				callback : function() {
				}
			},
			match : {
				enabled : true
			},
			onLoadEvent : function() {
				console.log('carregando...');
				
				cliente = null;
				
				$('#idCliente').val('');
			},
			onChooseEvent : function() {
				cliente = $("#cliente").getSelectedItemData();
				$('#idCliente').val(cliente.id);
				
				revalidar('cliente');
			}
		}
	});
}

function autoCompleteProduto() {
	var options = {
			url: function(dado) {
				return 'produtos/filtrar/' + dado;
			},

			getValue: "nome",

			template: {
				type: "custom",
				method: function(value, item){
					var descricao = "cod: "+item.id+", valor: "+item.valor;
					return "<span style = 'font-size:10px'>"+ value +" <span/><br><span style = 'font-size:10px; font-style: italic'>"+descricao+"<span/>";
				}
			},
			minCharNumber : 1,
			requestDelay : 100,
			placeholder : "Digite um nome para pesquisar",
			adjustWidth : true,
			list : {
				maxNumberOfElements : 10,
				showAnimation : {
					type : "fade", // normal|slide|fade
					time : 100,
					callback : function() {
					}
				},
				hideAnimation : {
					type : "slide", // normal|slide|fade
					time : 100,
					callback : function() {
					}
				},
				match : {
					enabled : true
				},
				onLoadEvent : function() {
					console.log('carregando...');
					
					produto = null;
					
					$('#idProduto').val('');
				},
				onChooseEvent : function() {
					produto = $("#produto").getSelectedItemData();
					$('#idProduto').val(produto.id);
					$('#valorProd').val(produto.valor);
					
					revalidar('produto');
				}
			}
		};
	
	$("#produto").easyAutocomplete(options);
}

function salvar(){
	var key = true;
	$('#tabela_itens_pedido tbody tr').each(function(){
		if($(this).children().attr('colspan') == 6){
			key = false;
		}
	})
	
	if(validar() && key){
		var itens = [];
		
		$('#tabela_itens_pedido tbody tr').each(function(){
			var prodId = $(this).children()[0].innerHTML;
			var valor = $(this).children()[4].innerHTML;
			var quantidade = $(this).children()[3].innerHTML;
			
			var item = {
					'id' : '',
					'valor' : valor,
					'quantidade' : quantidade,
					'produto' : {'id' : prodId}
			}
			
			itens.push(item);
		});
			
		var dado = {
			'id' : $('#idPedido').val(),
			'valorTotal' :	$('#tabela_itens_pedido tfoot tr').children()[4].innerHTML,
			'quantidade' : $('#tabela_itens_pedido tbody tr').length,
			'cliente' : {'id': $('#idCliente').val()},
			'itensPedido' : itens
		}
		
		$.ajax({
			url : 'pedidos/salvar',
			type : "POST",
			data : JSON.stringify(dado),
			headers: 
		    {
		        'X-CSRF-TOKEN': $("[name='_csrf']").val()
		    },
			dataType : "json",		 
			contentType : "application/json",
			success : function(msg, status, jqxhr) {
				location.reload();
			},
			error : function(msg, status) {
				Swal.fire({
					  icon: 'error',
					  title: 'Oops...',
					  text: 'Não foi possível cadastrar seu Pedido!'
					})
			}
		});
	}else if(key == false){
		Swal.fire({
			  icon: 'warning',
			  title: 'Oops...',
			  text: 'É necessário adicionar pelo menos um item no seu Pedido!'
			})
	}
	
}