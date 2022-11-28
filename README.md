Desafio realizado pelo curso de bootcamp da <a href = "https://www.dio.me/">DIO</a>
<h2> Como o projeto foi desenvolvido? </h2>
<h5> Realizei o projeto utilizando as ferramentas e conhecimentos: </h5>
<ul>
<li>Api-Restful</li>
<li>Clean-Architecture</li>
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
  <li> Tive problemas com o Swagger, não sei porque ainda não corrigiram isso, ele não acompanha a versão do springboot
  tendo assim que fazer o downgrade ou utilizar essa linha de código no properties, spring.mvc.pathmatch.matching-strategy=ant_path_matcher
  </li>
  </br>
  </br>
  <li>Tive um grande problema com o LocalDate, não conseguia passar ele para String no formato dd/MM/yyyy, só conseguia a hora local com o .now(),
  de alguma forma o ModelMapper não consegue fazer essa conversão automática e tive que salvar na mão sem usar o ModelMapper e deu certo, foi necessário 
  também a anotação @JsonFormat(pattern = "dd/MM/yyyy") na entidade para o postman entender que havia mudança no json do formato  da data. 
  Foi um grande aprendizado, nem sempre podemos contar com as bibliotecas.</li>
  
  


