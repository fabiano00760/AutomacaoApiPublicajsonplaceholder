Feature:  Excluir usuário com ID 1 com sucesso

Scenario: Excluir usuário com ID 1 com sucesso
When realizar uma requisição DELETE para "/{id}" com o ID 3
Then o código de status deve ser 200