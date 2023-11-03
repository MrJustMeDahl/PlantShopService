# PlantShopService

## Task 1

```json
GET http://localhost:7007/api/plants

HTTP/1.1 200 OK
Date: Fri, 03 Nov 2023 17:41:58 GMT
Content-Type: application/json
Content-Length: 377

[
  {
    "type": "Rose",
    "name": "Albertine",
    "maxHeight": 400,
    "price": 199.5,
    "id": 1
  },
  {
    "type": "Bush",
    "name": "Aronia",
    "maxHeight": 200,
    "price": 169.5,
    "id": 2
  },
  {
    "type": "FruitAndBerries",
    "name": "AromaApple",
    "maxHeight": 350,
    "price": 399.5,
    "id": 3
  },
  {
    "type": "Rhododendron",
    "name": "Astrid",
    "maxHeight": 40,
    "price": 269.5,
    "id": 4
  },
  {
    "type": "Rose",
    "name": "The DarkLady",
    "maxHeight": 100,
    "price": 199.5,
    "id": 5
  }
]
```
```json
GET http://localhost:7007/api/plants/1

HTTP/1.1 200 OK
Date: Fri, 03 Nov 2023 17:42:59 GMT
Content-Type: application/json
Content-Length: 71

{
  "type": "Rose",
  "name": "Albertine",
  "maxHeight": 400,
  "price": 199.5,
  "id": 1
}
```
```json
GET http://localhost:7007/api/plants/type/rose

HTTP/1.1 200 OK
Date: Fri, 03 Nov 2023 17:43:21 GMT
Content-Type: application/json
Content-Length: 148

[
  {
    "type": "Rose",
    "name": "Albertine",
    "maxHeight": 400,
    "price": 199.5,
    "id": 1
  },
  {
    "type": "Rose",
    "name": "The DarkLady",
    "maxHeight": 100,
    "price": 199.5,
    "id": 5
  }
]
```
```json
POST http://localhost:7007/api/plants

HTTP/1.1 201 Created
Date: Fri, 03 Nov 2023 17:43:41 GMT
Content-Type: application/json
Content-Length: 73

{
  "type": "Tree",
  "name": "OliveTree",
  "maxHeight": 1000,
  "price": 1499.5,
  "id": 6
}
```

## Task 2

HTTP method | REST Ressource | Exceptions and status(es)                                                                |
|---|---|------------------------------------------------------------------------------------------|
|GET| `/api/plants`| 200 for okay, 500 for server issue                                                       |
|GET|`/api/plants/{id}`| 200 for okay, 400 if id is not a number, 404 if id cannot be found, 500 for server issue |
|GET|`/api/plants/type/{type}` | 200 for okay, 404 type does not exist, 500 for server issue                              |
|POST| `/api/plants` | 201 for okay, 400 for bad json string, 500 for server error                              |

## Task 3

Functional programming

## Task 4

```json
GET http://localhost:7007/api/plants

HTTP/1.1 200 OK
Date: Fri, 03 Nov 2023 17:41:58 GMT
Content-Type: application/json
Content-Length: 377

[
  {
    "type": "Rose",
    "name": "Albertine",
    "maxHeight": 400,
    "price": 199.5,
    "id": 1
  },
  {
    "type": "Bush",
    "name": "Aronia",
    "maxHeight": 200,
    "price": 169.5,
    "id": 2
  },
  {
    "type": "FruitAndBerries",
    "name": "AromaApple",
    "maxHeight": 350,
    "price": 399.5,
    "id": 3
  },
  {
    "type": "Rhododendron",
    "name": "Astrid",
    "maxHeight": 40,
    "price": 269.5,
    "id": 4
  },
  {
    "type": "Rose",
    "name": "The DarkLady",
    "maxHeight": 100,
    "price": 199.5,
    "id": 5
  }
]
```
```json
GET http://localhost:7007/api/plants/1

HTTP/1.1 200 OK
Date: Fri, 03 Nov 2023 17:42:59 GMT
Content-Type: application/json
Content-Length: 71

{
  "type": "Rose",
  "name": "Albertine",
  "maxHeight": 400,
  "price": 199.5,
  "id": 1
}
```
```json
GET http://localhost:7007/api/plants/type/rose

HTTP/1.1 200 OK
Date: Fri, 03 Nov 2023 17:43:21 GMT
Content-Type: application/json
Content-Length: 148

[
  {
    "type": "Rose",
    "name": "Albertine",
    "maxHeight": 400,
    "price": 199.5,
    "id": 1
  },
  {
    "type": "Rose",
    "name": "The DarkLady",
    "maxHeight": 100,
    "price": 199.5,
    "id": 5
  }
]
```
```json
POST http://localhost:7007/api/plants

HTTP/1.1 201 Created
Date: Fri, 03 Nov 2023 17:43:41 GMT
Content-Type: application/json
Content-Length: 73

{
  "type": "Tree",
  "name": "OliveTree",
  "maxHeight": 1000,
  "price": 1499.5,
  "id": 6
}
```

## Task 5

When unit testing we test small individual functions, but when we are testing our dao class - it is an integration test as we are testing that our application is functional when working with an external database. 

## Task 6

We are still integration testing here, but in a larger scale than what we did in task 5. 
We test our server configuration(routing), exception handling aswell as our controller (which could hold validation),
and our persistence layer as the daos are also in play. The downside of this kind of testing is that it can be tough to see where mistakes are made if any exist.
On the positive side we are able to test that every part of our application is integrated correctly. 