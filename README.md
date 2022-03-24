<h1 align="center">
    <img src="https://user-images.githubusercontent.com/8178413/159716209-0f91e643-2b80-428a-a5f1-c7e8c7aea5d2.svg" alt="Logo" width="125" height="125">
</h1>
<div align="center">
  A REST API that manages messages and preforms basic checks and operations on them
  <br />
  <br />
  <a href="https://github.com/PandaBoy444/palindromeRestApi/issues/new?assignees=&labels=bug">Report a Bug</a>
  ·
  <a href="https://github.com/PandaBoy444/palindromeRestApi/issues/new?assignees=&labels=enhancement">Request a Feature</a>
  .
  <a href="https://github.com/PandaBoy444/palindromeRestApi/discussions">Ask a Question</a>
</div>

- Built on kotlin and ktor
- [Deployed on Heroku](https://palindrome-rest-api.herokuapp.com/) ~Google cloud~
# Architecture doodle
![image](https://user-images.githubusercontent.com/8178413/159892417-4b00d541-44af-4511-b573-3866605bd89c.png)
# Get started
## Run the server
After cloning, use `gradle run` to run the project locally  
By default the project will use an embedded database when launched, so you don't need to have anything else setup. If you do have a database, please define environment variables `db=true` and `JDBC_DATABASE_URL={your database url}`   
example environment 
```
    db=true;JDBC_DATABASE_URL=jdbc:postgresql:messages?user=postgres&password=palindrome
```  
The tests are all under `gradle test`  
When committing, the tests will also run on the cloud and if they pass the code will be deployed on heroku  
## Use the live server
You can visit and query the API at [palindrome-rest-api.herokuapp.com](https://palindrome-rest-api.herokuapp.com)
## API reference
| Action                             | Method | Endpoint                             | Arguments                       |
|------------------------------------|--------|--------------------------------------|---------------------------------|
| Create message                     | POST   | v1/messages                          | text={The text of the message}  |
| Delete message                     | DELETE | v1/messages/{id}                     |                                 |
| Update message                     | PUT    | v1/messages/{id}                     | text={New text for the message} |
| Retrieve all messages              | GET    | v1/messages                          |                                 |
| Retrieve message                   | GET    | v1/messages/{id}                     |                                 |
| Retrieve field from message        | GET    | v1/messages/{id}/{field}             |                                 |
| Retrieve logic fields from message | GET    | v1/messages/{id}/logicfields         |                                 |
| Retrieve logic field from message  | GET    | v1/messages/{id}/logicfields/{field} |                                 |
