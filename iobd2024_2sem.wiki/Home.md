# Cronograma

* Atividade Avaliada Presencial - 29/10

* Entrega - Trabalho - 29/10

***

# Guia Rápido

* [PostgreSQL cheatsheet](https://cheatsheets.zip/postgres)

* [Dicas](https://github.com/IgorAvilaPereira/iobd2024_2sem/wiki/Dicas)

***

# Vídeos

* [Aula Remoto 21/09](https://www.youtube.com/watch?v=yDUKRYH-HTU&ab_channel=IgorAvilaPereira)

* [JDBC+VSCODE](https://youtu.be/GdRKYk1DhdE)

* [Padrão DAO](https://youtube.com/playlist?list=PLvT8P1q6jMWfyLT3oRrjwhqLbHnvq1Nzk)
<!--
* [Padrão DAO - ORM n:n](https://youtube.com/playlist?list=PLvT8P1q6jMWfsGSRD3MP8cuR_gnsDD59F)
-->

***

# Aulas

##  05/11 - Início 2º Bim.

**Tópicos Pendentes:**

* Herança de tabelas no Postgresql
* Conectividade usando Java ou Python
* Armazenamento de _bytes_ no PostgreSQL
* Normalização (Formas Normais)
* DCL

***

##  29/10 - Atividade Avaliada Presencial 1º bim.

***

## 22/10 - Lista 3

[Lista 3](https://github.com/IgorAvilaPereira/iobd2024_2sem/wiki/Listas#lista-3---sql-manipula%C3%A7%C3%A3o-de-datas)

**Tópicos pendentes:**
* Manipulação de DATAS => dado na aula
* UUID: uma alternativa às pk's seriais => dado na aula
* Criando meus próprios **_types_** => dado na aula
* Colunas JSON => dado na aula



***

## 15/10 - Lista 2: with, case when, group by, having

[Lista 2](https://github.com/IgorAvilaPereira/iobd2024_2sem/wiki/Listas)

* [aula151024.sql](https://github.com/IgorAvilaPereira/iobd2024_2sem/blob/main/aula151024.sql)

**Matéria nova:**

* [with](https://www.postgresql.org/docs/current/queries-with.html)
```sql
WITH tabela_aux AS (
     select 
        evento_id, 
        evento.nome,
        count(*)*valor as sub_total 
    from evento inner join lote on (evento.id = lote.evento_id) 
        inner join ingresso on (lote.id = ingresso.lote_id) 
    group by evento_id, evento.nome, lote.id
) 
SELECT evento_id, nome, SUM(sub_total) as total from tabela_aux group by evento_id, nome;
```

**Tópicos pendentes:**
* Manipulação de DATAS
* UUID: uma alternativa às pk's seriais
* Criando meus próprios **_types_**
* Colunas JSON
* Herança
* Armazenamento de _bytes_ no PostgreSQL
* Normalização (Formas Normais)
* DCL

***

## 08/10 - Lista 2: schemas, case when, jdbc

[Lista 2](https://github.com/IgorAvilaPereira/iobd2024_2sem/wiki/Listas)


**Tópicos:**
* [schemas](https://github.com/IgorAvilaPereira/iobd2024_2sem/blob/main/bd_presenca.sql)

![image](https://github.com/user-attachments/assets/d9ae8fde-34ee-48cf-9d52-f1c4b8223354)

* Case/When
```sql
select *,   CASE 
         WHEN cast(cache as numeric(8,2)) > 1000 THEN 'caro' 
         WHEN cast(cache as numeric(8,2)) < 10 THEN 'barato'
       ELSE 'n sei' END as perspectiva_david from atracao;
```
* **Conectividade Java/PostgreSQL (JDBC):**
  * [Exemplo JDBC Sem Gerenciador de Pacotes (Projeto Adriano)](https://github.com/IgorAvilaPereira/iobd2024_2sem/blob/main/adriano.zip)
  * [Exemplo JDBC Com Gerenciador de Pacotes (Projeto David)](https://github.com/IgorAvilaPereira/iobd2024_2sem/blob/main/david.zip)


**Dicas:**
* [CASE/WHEN](https://github.com/IgorAvilaPereira/iobd2024_2sem/wiki/Dicas#casewhenelseend)
* [Schemas](https://github.com/IgorAvilaPereira/iobd2024_2sem/wiki/Dicas#schema)

***

## 01/10 - Lista 2: Filtros, Ordenação e Limitação, Funções de Agregação, JOIN's, Subconsultas

[Lista 2](https://github.com/IgorAvilaPereira/iobd2024_2sem/wiki/Listas)

***

## 24/09 - Lista 1: joins, inner, left, right, union, except, intersect, subselect

[Lista 1](https://github.com/IgorAvilaPereira/iobd2024_2sem/wiki/Listas#lista-1---sql)

[Gabarito - Lista 1](https://github.com/IgorAvilaPereira/iobd2024_2sem/blob/main/aula240924.sql)

[balada.sql](https://github.com/IgorAvilaPereira/iobd2024_2sem/blob/main/balada240924.sql)

[Video - Encontro 24/09](https://www.youtube.com/watch?v=CgWosGAb1R4)

***

## 17/09 - Defesas de TCC

***

## 10/09 - Introdução

![image](https://github.com/user-attachments/assets/717a8c47-cb10-4d4c-9b3e-32fc3cd05590)

https://github.com/IgorAvilaPereira/iobd2024_2sem/blob/main/balada.dia

https://github.com/IgorAvilaPereira/iobd2024_2sem/blob/main/balada.sql

https://github.com/IgorAvilaPereira/iobd2024_2sem/blob/main/balada.txt

https://github.com/IgorAvilaPereira/iobd2024_2sem/blob/main/sql1.pdf

https://github.com/IgorAvilaPereira/iobd2024_2sem/blob/main/sql2.pdf