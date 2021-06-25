$(document).ready(function() {
    buscar(); 
    init_btns();
});

function buscar(){
	var pesquisa;
	$.get('produtos/buscar', pesquisa, function(dados) {
		
		$('#resultado').html(dados);
		init_data_table('tabela_produtos');
		
	});
}

function init_btns(){
	$('#btnModal').click(function(){
		$.get('produtos/modal',function(retorno){
			initForm(retorno,"Cadastro");
		});
	});	
	
}

function initForm(retorno,tipo){
	$('#corpoModal').html(retorno);
	$('#motalTitulo').text(tipo+' - Produto');
	
	$('#modal').modal('show');
}

function alterarItem(id){
	$.get('produtos/modal/'+id,function(retorno){
		initForm(retorno,"Atualizar");
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
			 $.get('produtos/delete/'+id,function(){
				  buscar();
			  }).fail(function(){
				Swal.fire({
					  title: "Não foi possível deletar o Produto!",
					  text: "Necessário retirar todas as dependências do produto antes da deleção!!!",
					  icon: "error",
					  button: "ok!",
					  showCloseButton: true,
				  });
			});
			  }
	  });
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
		init_data_table('tabela_produtos');		
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
				{width: "60%", targets: 0},
				{
			        targets: [1,2],
			        className: 'dt-center'
			    },
			    {
                    render: function (data, type, full, meta) {
                    	if(data == 'true'){
                    		return 'Sim';
                    	}else{
                    		return 'Não';
                    	}
                    },
                    targets: 2
                }
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