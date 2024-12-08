import {
  addArticleHandler,
  getAllArticlesHandler,
  getArticleByIdHandler,
  updateArticleHandler,
  deleteArticleByIdHandler,
  addUserHandler,
  getAllUsersHandler,
  getUserByIdHandler,
  updateUserHandler,
  deleteUserHandler,
} from "./handler.js";


const routes = [
  // Artikel Routes
  {
    method: "POST",
    path: "/articles",
    handler: addArticleHandler,
  },
  {
    method: "GET",
    path: "/articles",
    handler: getAllArticlesHandler,
  },
  {
    method: "GET",
    path: "/articles/{articleId}",
    handler: getArticleByIdHandler,
  },
  {
    method: "PUT",
    path: "/articles/{articleId}",
    handler: updateArticleHandler,
  },
  {
    method: "DELETE",
    path: "/articles/{articleId}",
    handler: deleteArticleByIdHandler,
  },
  // User Routes
  { 
    method: "POST", 
    path: "/users", 
    handler: addUserHandler 
  },
  { 
    method: "GET", 
    path: "/users", 
    handler: getAllUsersHandler 
  },
  { 
    method: "GET", 
    path: "/users/{userId}", 
    handler: getUserByIdHandler 
  },
  { 
    method: "PUT", 
    path: "/users/{userId}", 
    handler: updateUserHandler 
  },
  { 
    method: "DELETE", 
    path: "/users/{userId}", 
    handler: deleteUserHandler },
];

export default routes;
  