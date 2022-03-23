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
- Deployed on Heroku ~Google cloud~
# Pipeline
![image](https://user-images.githubusercontent.com/8178413/159715920-24b63252-b75e-4376-86c0-8f1629b31469.png)
# Get started
After cloning, use `gradle run` to run the project locally   
The tests are all under `gradle test`  
When commiting, the tests will also run on the cloud and if they pass the code will be deployed on heroku  

# API referance
| Action           | Method | Endpoint         | Arguments                       |
|------------------|--------|------------------|---------------------------------|
| Create message   | POST   | v1/messages      | text={The text of the message}  |
| Retrieve message | GET    | v1/messages/{id} |                                 |
| Update message   | PUT    | v1/messages/{id} | text={New text for the message} |
