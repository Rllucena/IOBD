# Lista 3 - SQL (Manipulação de Datas)

* Calcular a idade dos funcionários: Selecione o nome dos funcionários e calcule a idade de cada um com base na data de nascimento.
* Eventos futuros: Selecione todos os eventos que ocorrerão no futuro, considerando a data atual.
* Duração dos eventos: Selecione o nome dos eventos e a duração de cada um em horas.
* Ingressos vendidos por mês: Calcule o número de ingressos vendidos por mês.
* Funcionários que trabalharam em eventos no último ano: Selecione o nome dos funcionários que trabalharam em eventos no último ano.
* Listar todos os eventos que ocorrerão no mês de setembro de 2024.
* Encontrar a duração de cada evento em horas.
* Listar todas as atrações que ocorrerão em um determinado evento, incluindo a duração de cada atração.
* Encontrar todos os funcionários que trabalharam em eventos no mês de setembro de 2024.
* Calcular o total de horas trabalhadas por cada funcionário em todos os eventos.
* Listar todos os ingressos vendidos para eventos que ocorrerão após uma data específica.

***

# Lista 2 - SQL

## EXCEPT, UNION, INTERSECT
* Selecione todos os eventos que não têm atrações associadas.
* Selecione todos os funcionários que não trabalharam em nenhum evento.
* Selecione todos os eventos que têm atrações associadas.
* Selecione todos os funcionários que trabalharam em eventos.
* Selecione todos os eventos e todas as atrações.
* Selecione todos os eventos e todas as atrações, incluindo duplicatas.
* Selecione todos os lotes que não têm ingressos associados.
* Selecione todos os ingressos que pertencem a lotes.
* Selecione todos os eventos e todos os funcionários.
* Selecione todos os eventos e todos os funcionários, incluindo duplicatas.

[Gabarito 01/10](https://github.com/IgorAvilaPereira/iobd2024_2sem/blob/main/aula011024.sql)

## Subselect
* Selecione todos os eventos que têm mais de 2 atrações.
* selecione todas as atrações que têm cachê maior que a média.
* Selecione todos os lotes que têm mais de 50 ingressos vendidos.
* Selecione todos os funcionários que trabalharam em mais de um evento.
* Selecione todos os eventos que têm lotes com valor médio maior que 15.
* Selecione todas as atrações que se apresentaram em eventos com mais de 100 ingressos vendidos.
* Selecione todos os eventos que têm funcionários recebendo mais de 100 em cachê.
* Selecione todos os funcionários que trabalharam em eventos com mais de 3 atrações.
* Selecione todos os lotes que têm ingressos com observações específicas.
* Selecione todos os eventos que têm atrações com cachê maior que 2000.

[Gabarito 08/10](https://github.com/IgorAvilaPereira/iobd2024_2sem/blob/main/aula081024.sql)

## GROUP BY e HAVING
* Conte o número total de eventos por data.
* calcule o cachê total recebido por cada atração.
* Calcule a quantidade total de ingressos vendidos por lote.
* Calcule o valor total recebido por cada funcionário.
* Calcule a quantidade total de lotes por evento.
* Calcule a média de cachê recebido por atração.
* Calcule o valor total dos lotes por evento.
* Calcule a quantidade total de ingressos vendidos por evento.
* Calcule o valor total recebido por cada evento.
* Calcule a quantidade total de funcionários por evento.

[Gabarito 15/10](https://github.com/IgorAvilaPereira/iobd2024_2sem/blob/main/aula151024.sql)

***

# Lista 1 - SQL

## Consultas Básicas
1. Selecione todos os eventos.
2. Selecione todas as atrações.
3. Selecione todos os lotes.
4. Selecione todos os ingressos.
5. Selecione todos os funcionários.

## Filtros e Condições
6. Selecione todos os eventos que começam após 2024-09-13.
7. Selecione todas as atrações com cachê maior que 1000.
8. Selecione todos os lotes com quantidade maior que 100.
9. Selecione todos os ingressos com a observação 'Quem comprou: Ronaldo'.
10. Selecione todos os funcionários nascidos após 2000-01-01.

## Ordenação e Limitação
11. Selecione todos os eventos ordenados pelo nome.
12. Selecione as 3 atrações mais caras.
13. Selecione os 5 lotes com menor valor.
14. Selecione os 10 ingressos mais recentes.
15. Selecione todos os funcionários ordenados pela data de nascimento.

## Funções de Agregação
16. Conte o número total de eventos.
17. Conte o número total de atrações.
18. Calcule o valor médio dos lotes.
19. Encontre o evento com a maior quantidade de lotes.
20. Calcule o número total de ingressos vendidos.

## Junções (JOIN)
21. Selecione todas as atrações junto com o nome do evento correspondente.
22. Selecione todos os lotes junto com o nome do evento correspondente.
23. Selecione todos os ingressos junto com o número do lote correspondente.
24. Selecione todos os funcionários junto com o nome do evento em que trabalharam.
25. Selecione todas as atrações de um evento específico.

## Subconsultas
26. Selecione todos os eventos que têm mais de 2 atrações.
27. Selecione todas as atrações que têm cachê maior que a média.
28. Selecione todos os lotes que têm mais de 50 ingressos vendidos.
29. Selecione todos os funcionários que trabalharam em mais de um evento.
30. Selecione todos os eventos que têm lotes com valor médio maior que 15.

## Consultas Avançadas
31. Atualize o cachê da atração 'DJ DAVID' para 50.00.
32. Delete todos os ingressos com a observação 'Quem comprou: Ronaldo'.
33. Adicione uma nova coluna 'email' na tabela 'funcionario'.
34. Crie uma nova tabela 'cliente' com as colunas 'id', 'nome' e 'email'.
35. Insira 3 novos clientes na tabela 'cliente'.

## Consultas de Manipulação de Dados
36. Atualize o valor dos lotes do evento 'HALLOWEEN' para 15.
37. Delete todos os funcionários nascidos antes de 2000.
38. Adicione uma nova atração 'BANDA XYZ' com cachê de 3000.00.
39. Crie uma nova tabela 'venda' com as colunas 'id', 'ingresso_id', 'data_venda' e 'valor_venda'.
40. Insira uma nova venda na tabela 'venda' para um ingresso específico.

## LEFT/RIGHT JOIN
41. Selecione todos os eventos e, se houver, as atrações associadas a cada evento.
42. Selecione todos os lotes e, se houver, os ingressos associados a cada lote.
43. Selecione todos os funcionários e, se houver, os eventos em que trabalharam.
44. Selecione todas as atrações e, se houver, os eventos em que se apresentaram.

[Gabarito](https://github.com/IgorAvilaPereira/iobd2024_2sem/blob/main/aula240924.sql)

* obs: há um vídeo de resolução da lista: https://github.com/IgorAvilaPereira/iobd2024_2sem/wiki#2409---lista-1-joins-inner-left-right-union-except-intersect-subselect