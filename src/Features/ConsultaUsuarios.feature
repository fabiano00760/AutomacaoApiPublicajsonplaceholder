Feature: Consultar Usuários

  Background:
    Given a base URL "https://jsonplaceholder.typicode.com/users"

  Scenario: Consultar todos os usuários
    When realizar uma requisição GET
    Then o código de status deve ser 200

  Scenario: Consultar usuário específico com ID 1
    When realizar uma requisição GET para "1"
    Then o código de status deve ser 200
    And o corpo da resposta deve conter o JSON lido de "src/Data/response.json"

  Scenario: Consultar usuário específico com ID 2 validando o retorno
    When realizar uma requisição GET para "2"
    Then o código de status deve ser 200
    And o corpo da resposta deve conter o JSON lido de "src/Data/response.json"

  Scenario: Consultar usuário com ID 2 com query-string validando campo específico
    When realizar uma requisição GET para "2" com a query-string "example=teste"
    Then o código de status deve ser 200
    And o campo "id" no corpo da resposta deve ser igual a 2

  Scenario: Cadastrar usuário com sucesso
    Given um corpo de requisição lido de "src/Data/payloadFile.Json"
    When realizar uma requisição POST
    Then o código de status deve ser 201
    And o corpo da resposta deve conter o JSON lido de "src/Data/payloadFile.Json"

  Scenario: Atualizar usuário com ID 3
    When realizar uma requisição PUT para "3" com o corpo da requisição "{\"email\": \"dojo.karater@inmetrics.com.br\"}"
    Then o código de status deve ser 200
    And o campo "email" no corpo da resposta deve ser igual a "dojo.karater@inmetrics.com.br"

    And o campo "id" no corpo da resposta deve ser igual a 3
