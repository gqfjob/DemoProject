# Migration from Swagger Spring MVC to Swagger Springfox 2.0.2

jhipster/generator-jhipster#1508

- no more swagger-ui directory under webapp
- no more swagger dependency in bower.json
- version of the API in application.yml
- static docs generated in HTML and PDF under target/api-docs
- possibility to customize docs by editing src/docs/asciidoc/index.adoc

Static doc generation currently works with maven but could be easily adapted to gradle as explained [here](https://github.com/RobWin/swagger2markup)

