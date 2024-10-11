# management-tool

Prerequisites:
- java 17
- maven latest

Product:

The project has the purpose to manage data. So the actions over the products are:
- add product
- find product
- update product

Authorization:

There are some authorization restrictions.
Authorization is added using spring security.
The hierarchy of the type of users is given by the groups, there are 2 types of groups: 
- group_admin
- group_user

The jwt token helps to get the data about the user and all of the endpoints exception the login are restricted to be authorized using a jwt.

Groups:
-  group admin -> the default role is admin -> can do anything
-  group user -> the default role is view -> can just search the product (no edit, no creat other products)

But also can add a role for edit, so having an edit role + group user -> can edit the products but not to create the products



Users:

- Users are managed by Keycloak.

- Credentials are store in keycloak.

Logs:
kibana link: http://localhost:5601/app/kibana#/discover

Swagger link: http://localhost:8080/swagger-ui/index.html