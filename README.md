Desafio realizado pelo curso de bootcamp da <a href = "https://www.dio.me/">DIO</a>
<h2> Como o projeto foi desenvolvido? </h2>
<h5> Realizei o projeto utilizando as ferramentas e conhecimentos: </h5>
<ul>
<li>Api-Restful</li>
<li>SOLID</li>
<li>DTO</li>
<li>Lombok</li>
<li>JPA</li>
<li>H2 Database - /h2-dio-capgemini - user:greg / password:12345</li>
<li>ModelMapper</li>
<li>Junit5</li>
<li>Mockito</li>
<li>Testes Unitários</li>
<li>Heroku - https://contacts-person-api.herokuapp.com/  api/persons e api/phones</li>
<li>Swagger - http://localhost:8080/swagger-ui/index.html</li>
</ul>

<h2> Bugs e problemas no desenvolvimento: </h2>
<ul>
  <li>Tive problemas no início pois quando iniciei os testes, havia esquecido de colocar todas as exceptions 
e também de criar o handleException para controlar as exceções e exibir para o cliente, assim tive que refatorar todo o projeto. </li>
  </br>
  </br>
  <li> Tive problemas com o ModelMapper e o LocalDate, sempre consegui manipular o LocalDate porém, acredito que
  com o ModelMapper ele não consiga fazer essa diferença por trás, ele até faz mas quando fui testar o listAll
  falhou várias vezes e acabei retirando o Pageable e deixando o listAll sem ser paginado. O LocalDate pega
  a data atual sem a possibilitade de edição, como tem tempo para terminar o bootcamp, deixarei isso para resolver
  outro dia. </li>
  </br>
  </br>
  <li> Tive problemas com o Swagger, não sei porque ainda não corrigiram isso, ele não acompanha a versão do springboot
  tendo assim que fazer o downgrade ou utilizar essa linha de código no properties, spring.mvc.pathmatch.matching-strategy=ant_path_matcher
  </li>


