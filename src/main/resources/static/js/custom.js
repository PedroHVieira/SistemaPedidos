function validar() {
	var retorno = true;
	//VALIDAÇÃO
	$('.validar').each(function() {
		if (
			$(this).val() == '' ||
			$(this).val() == null ||
			Number($(this).val()) == Number('0')
		) {
			$(this).addClass("is-invalid");
			retorno = false;
		}
	});

	//REVALIDAR
	$('.validar').each(function() {
		$(this).change(function() {
			$(this).removeClass("is-invalid");
		});
	});

	return retorno;

}