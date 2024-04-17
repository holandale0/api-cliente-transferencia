Este projeto trata-se de uma solução criada para o desafio proposto pelo Banco Itaú (repositório)

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
DESAFIO:

Desenhar e desenvolver uma solução que permita que os clientes do Itau consigam realizar
Transferência entre contas. Essa solução precisa ser resiliente, ter alta disponibilidade e de fácil
evolução/manutenção.

1 - ENGENHARIA DE SOFTWARE

Desenvolva uma API REST com os seguintes requisitos:

  1.1 Ser desenvolvida em linguagem Java/Spring Boot;
  1.2 Validar se o cliente que vai receber a transferência existe passando o idCliente na API
      de Cadastro;
  1.3 Buscar dados da conta origem passando idConta na API de Contas;
  1.4 Validar se a conta corrente está ativa;
  1.5 Validar se o cliente tem saldo disponível na conta corrente para realizar a transferência;
  1.6 A API de contas retornará o limite diário do cliente, caso o valor seja zero ou menor do
      que o valor da transferência a ser realizada, a transferência não poderá ser realizada;
  1.7 Após a transferência é necessário notificar o BACEN de forma síncrona que a transação
      foi concluída com sucesso. A API do BACEN tem controle de rate limit e pode retornar
      429 em caso de chamadas que excedam o limite;
  1.8 Impedir que falhas momentâneas das dependências da aplicação impactem a
      experiência do cliente;

2 - ARQUITETURA DE SOLUÇÃO

Crie um desenho de solução preferencialmente AWS para a API que foi desenvolvida no
desafio de engenharia de software considerando os requisitos abaixo:

  2.1 Apresentar uma proposta de escalonamento para casos de oscilação de carga;
  2.2 Apresentar uma proposta de observabilidade;
  2.3 Caso utilizado, justificar o uso de caching;
  2.4 A solução precisa minimizar o impacto em caso de falhas das dependências (API
      Cadastro, Conta e BACEN).

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

SOLUÇÃO

1 - ENGENHARIA DE SOFTWARE



      
