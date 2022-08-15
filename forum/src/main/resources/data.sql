INSERT INTO USUARIO (
	nome,
	email,
	senha
) VALUES 
(
	'Lucas Moura da Silva',
	'lucassiyahoo.com.br@gmail.com',
	'@Senha123'
),
(
	'Marcus César Sauer',
	'marcus.sauer@liberato.com.br',
	'@Senha123'
);

INSERT INTO CURSO (
	nome,
	categoria
) VALUES 
(
	'Spring Boot',
	'Programação'
),
(
	'JavaScript',
	'Programação'
);

INSERT INTO TOPICO (
	titulo,
	mensagem,
	data_criacao,
	status,
	autor_id,
	curso_id
) VALUES 
(
	'Dúvida',
	'Erro ao criar projeto',
	'2020-11-30 20:50:23',
	'NAO_RESPONDIDO',
	1,
	1
),
(
	'Dúvida',
	'Erro ao rodar projeto',
	'2021-09-30 20:55:23',
	'NAO_RESPONDIDO',
	1,
	2
);


