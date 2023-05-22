# AvatarCRUD

Тестовый проект по добавлению и сохраниению юзера и картинок.
- RESTful.
- JSON format.
Two html pages. One of them has list of users. Second show form to add new user and file.
Possibilities:
1. send the file to the server (JPG/PNG avatar picture). Save the image in a directory on the server.
2. send the user's personal data to the server (name, email, etc.). Store information in a database.
3. pass a unique user ID to the server. Reading information from the database.
4. send a unique user ID and a new status (Online, Offline) to the server. Change user status

User API.

User GET requests examples:
  /api/users/ - get all users
  /api/users/?status=online - get all users with status = "online". Param "status" is not required.
  /api/users/{id} - get user by id
  
  response: User info(id, name, email, status, pictureKey)
  
User POST requests examples:
  /api/users/
  JSON body:
  {
    "name": "puck",
    "email": "puck@mail.ru",
    "status": "offline"
  }
  
  response: User id
 
 User PUT requests examples:
  /api/users/{id}?status="offline" - changes user status
  JSON response body:
  {
    "data": {
        "id": 25,
        "status": "ONLINE",
        "previousStatus": "ONLINE"
    },
    "message": "status is changed",
    "status": 200
  }
User DELETE requests examples:
  /api/users/{id} - delete User by id
  
Picture API.
  
Picture GET requests examples:
  /api/pictures/ - get all pictures
  
  response: picture info(id, name, pictureKey)
  
Picture POST requests examples:
  /api/pictures/ - load file to storage. 
  Body from-data:
  key: multipartFile
  value: yourFileYouWantLoad.png
  
  response JSON:
  {
    "id": 1,
    "name": "yourFileYouWantLoad.png",
    "pictureKey": "e11e52880e975b7b0a5eecda5de8881d"
  }
 
Picture DELETE requests examples:
  /api/pictures/{id} - delete Picture by id
  /api/pictures/delete-files - delete file from storage
